package com.burchard36.api.utils.reflections;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.utils.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * A generic package scanner, that will scan a specific {@link Package} for a {@link Class} that
 * extends another {@link Class}.
 *
 * @author Dalton Burchard
 * @since 2.1.8
 */
public class PackageScanner<T> {

    /* Dev note, im really proud of this class */
    protected Package packageToSearch;
    protected Class<? extends T> currentClassToSearch;
    protected List<Class<?>> classesForPackage;
    protected List<Class<? extends T>> result;


    /**
     * Initializes the class, make sure to specify the generic class you are looking to use for this class
     *<br>
     *
     * EG: {@code PackScanner<MyInterfaceImLookingFor> scanner = new PackageScanner<>();}
     * Where MyInterfaceImLookingFor is the subclass you want to use when searching through your Packages
     */
    public PackageScanner() {
        this.classesForPackage = new ArrayList<>();
        this.result = new ArrayList<>();
    }

    /**
     * Use this when you are still using the same package previous set by using {@link PackageScanner#subclassSearchQuery}
     * <br>
     * If you want to use a different Package, then use {@link PackageScanner#subclassSearchQuery}
     * @param clazz Target class you want to check, typically a YourClass.class argument.
     * @return instance of this class
     * @since 2.1.8
     */
    public PackageScanner<T> updateTargetClass(Class<? extends T> clazz) {
        this.currentClassToSearch = clazz;
        return this;
    }

    /**
     * Tries to invoke a {@link Class} extending another {@link Class}, and providing dependency
     * injection for your main class {@link BurchAPI}
     * @param clazz The {@link Class} to invoke
     * @return A {@link InvocationResult},
     */
    public final InvocationResult<T, InvocationErrorStatus> invokeClass(Class<? extends T> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<? extends T> constructor = null;
        Object toProvide = null;
        try {
            for (Constructor<?> aConstructor : constructors) {
                if (aConstructor.getParameterTypes().length == 0 && constructor == null)
                    constructor = clazz.getDeclaredConstructor();


                if (aConstructor.getParameterTypes().length == 1 &&
                        aConstructor.getParameterTypes()[0] == BurchAPI.class) {
                    toProvide = BurchAPI.INSTANCE;
                    constructor = clazz.getDeclaredConstructor(BurchAPI.class);
                }

                if (aConstructor.getParameterTypes().length == 1 &&
                    aConstructor.getParameterTypes()[0] == BurchAPI.INSTANCE.getClass()) {
                    toProvide = BurchAPI.INSTANCE;
                    constructor = clazz.getDeclaredConstructor(BurchAPI.INSTANCE.getClass());
                }
            }
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            return new InvocationResult<>(null, InvocationErrorStatus.ERR_INVALID_CONSTRUCTOR);
        }

        if (constructor == null)
            return new InvocationResult<>(null, InvocationErrorStatus.ERR_INVALID_CONSTRUCTOR);

        T generic;
        try {
            try {
                generic = constructor.newInstance(toProvide);
            } catch (NullPointerException ignored) {
                generic = constructor.newInstance();
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            return new InvocationResult<>(null, InvocationErrorStatus.ERR_INVOCATION);
        }

        return new InvocationResult<>(generic, InvocationErrorStatus.NULL);
    }

    /**
     * Finds all classes that have a class annotated
     * with a specific {@link Annotation} in the results set from {@link PackageScanner#execute} (Must call execute() before calling this method)
     * @param annotations A varargs of {@link Annotation} you want to search for, do note these are not METHOD annot
     * @return An {@link HashMap} of {@link Annotation} classes as values, and classes extending your generic provided.
     */
    @SafeVarargs
    public final HashMap<Class<? extends Annotation>, Class<? extends T>> findWithClassConstructorAnnotations(Class<? extends Annotation>... annotations) {
        final HashMap<Class<? extends Annotation>, Class<? extends T>> results = new HashMap<>();
        for (Class<? extends Annotation> annotation : annotations) {
            this.result.forEach((clazz) -> {
                if (clazz.getAnnotation(annotation) != null) {
                    results.putIfAbsent(annotation, clazz);
                }
            });
        }
        return results;
    }

    /**
     * Sets the package to scan, and type to scan for.
     * <br>
     * This will also cache all the classes for the specific package.
     *
     * @param aPackage A {@link Package} the root package to start scanning at
     * @param aClass The class you want the return value of this
     * @return Instance of this class
     * @since 2.1.8
     */
    public final PackageScanner<T> subclassSearchQuery(@Nonnull Package aPackage, @Nonnull Class<? extends T> aClass) {
        this.packageToSearch = aPackage;
        this.currentClassToSearch = aClass;

        try {
            this.getClassesForPackage(this.packageToSearch.getName());
        } catch (IOException | URISyntaxException ignored) {
            throw new PackageScannerException("When using package: " + this.packageToSearch.getName() + " a malformed class" +
                    " file was detected please review your code");
        }
        return this;
    }

    /**
     * Executes the query you set. Make sure you call {@link PackageScanner#subclassSearchQuery} first or this will generate {@link PackageScannerException}
     * @return {@link List} of {@link Class}'s that extend a class you specified in {@link PackageScanner#subclassSearchQuery }
     * @since 2.1.8
     */
    public final @Nonnull List<Class<? extends T>> execute() {
        if (this.packageToSearch == null || this.currentClassToSearch == null)
            throw new PackageScannerException("Package to search, or current class to search was invalid! This is an error with your plugin integration, contact a developer");
        else if (this.classesForPackage.isEmpty()) throw new PackageScannerException("You did not call PackageScanner#subclassSearchQuery! Are you okay? Did you read the documentation, or using this class for your own purposes? " +
                "if you are not initializing this class yourself, please open an issue on our GitHub");
        try {
            this.result = this.getClassesExtendingThis(this.packageToSearch, this.currentClassToSearch);
            return this.result;
        } catch (IOException | ClassNotFoundException | URISyntaxException ex) {
            throw new PackageScannerException(("When executing a PackageScanner query, a exception occurred, this is likely an issue with your jar file. Please recompile your project, " +
                    "and if this doesn't work open an issue on our GitHub (BurchAPI)"));
        }
    }


    protected final List<Class<? extends T>> getClassesExtendingThis(Package thePackage, Class<? extends T> that)
            throws IOException, ClassNotFoundException, URISyntaxException {
        Logger.debug("Going to attempt to search for classes extending: " + that.getName() + ". Amount of classes grabbed: " + this.classesForPackage.size());
        List<Class<? extends T>> returnValues = new ArrayList<>();

        for (Class<?> clazz : this.classesForPackage) {
            if (that.isAssignableFrom(clazz)) {
                Logger.debug("Found a class that matched! Class found: " + clazz.getName() + ". Class needed");
                try {
                    Class<? extends T> input = clazz.asSubclass(that);
                    returnValues.add(input);
                } catch (ClassCastException ignored) {
                    Logger.error("Un-expected class when parsing: " + clazz.getName());
                    Logger.error("This is likely an issue with the way you are implementing/extending a class from our API! Please review your code and documentation.");
                    Logger.error("No exception can be caught for this, this is your own fault and you need to fix it");
                }
            }
        }
        Logger.debug("Found: " + returnValues.size() + " that extended searching class: " + this.currentClassToSearch.getName());
        return returnValues;
    }

    /* https://stackoverflow.com/questions/1810614/getting-all-classes-from-a-package */
    protected final void getClassesForPackage(String pkgName) throws IOException, URISyntaxException {
        final String pkgPath = pkgName.replace('.', '/');
        final URI pkg = Objects.requireNonNull(BurchAPI.INSTANCE.getClass().getClassLoader().getResource(pkgPath)).toURI();
        final ArrayList<Class<?>> allClasses = new ArrayList<>();

        Path root;
        if (pkg.toString().startsWith("jar:")) {
            try {
                root = FileSystems.getFileSystem(pkg).getPath(pkgPath);
            } catch (final FileSystemNotFoundException e) {
                root = FileSystems.newFileSystem(pkg, Collections.emptyMap()).getPath(pkgPath);
            }
        } else root = Paths.get(pkg);

        this.classesForPackage.clear();
        final String extension = ".class";
        try (final Stream<Path> allPaths = Files.walk(root)) {
            allPaths.filter(Files::isRegularFile).forEach(file -> {
                try {
                    final String path = file.toString().replace('/', '.');
                    final String name = path.substring(path.indexOf(pkgName), path.length() - extension.length());
                    allClasses.add(Class.forName(name));
                } catch (final ClassNotFoundException | StringIndexOutOfBoundsException ex) {
                    /* Ignore for now */
                }
            });
        }
        this.classesForPackage.addAll(allClasses);
    }

    /**
     * The exception thrown when there is any generic error with this class.
     */
    public static class PackageScannerException extends RuntimeException {
        protected PackageScannerException(String msg) {
            super(msg);
        }
    }

    /**
     * Used to return err status code in {@link PackageScanner#invokeClass}
     */
    public enum InvocationErrorStatus {
        /**
         * Used when there is no error.
         */
        NULL,
        /**
         * Used when a default class constructor could not be found
         */
        ERR_INVALID_CONSTRUCTOR,
        /**
         * Used when an exception is generated while invoking {@link Constructor#newInstance}
         */
        ERR_INVOCATION
    }

    /**
     * Used as a Results set when calling {@link PackageScanner#invokeClass}
     * @param <T>
     * @param <InvocationErrorStatus>
     */
    public static class InvocationResult<T, InvocationErrorStatus> extends AbstractMap.SimpleEntry<Object, InvocationErrorStatus> {

        protected final T key;
        protected final InvocationErrorStatus value;

        protected InvocationResult(T key, InvocationErrorStatus value) {
            super(key, value);
            this.key = key;
            this.value = value;
        }

        /**
         * Gets the generic value of this class
         * @return T is provided by you when initializing the generic
         */
        @Override
        public T getKey() {
            return this.key;
        }

        /**
         * A {@link InvocationErrorStatus} returned from this result
         * @return An enum of {@link InvocationErrorStatus}, typically {@link InvocationErrorStatus} of NULL (enum value) when successfully executed
         */
        @Override
        public InvocationErrorStatus getValue() {
            return this.value;
        }

        /**
         * Checks if the Invocation of a class was successfully
         * @return A {@link Boolean} true if invocation was successfully
         * @since 2.1.8
         */
        public final boolean wasSuccessfullyInvoked() {
            return this.getValue() == PackageScanner.InvocationErrorStatus.NULL;
        }
    }
}
