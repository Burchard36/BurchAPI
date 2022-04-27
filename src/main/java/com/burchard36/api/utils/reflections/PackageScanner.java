package com.burchard36.api.utils.reflections;

import com.burchard36.api.BurchAPI;
import com.burchard36.api.data.annotations.PlayerDataFile;
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
 * extends another {@link Class}. Not really intended for users of this API to use, but feel free to.
 *
 * @author Dalton Burchard
 * @since 2.1.8
 */
public class PackageScanner<T> {

    protected Package packageToSearch;
    protected Class<?> currentClassToSearch;
    protected List<Class<?>> classesForPackage;
    protected List<Class<? extends T>> result;


    /**
     * While <bold>not recommended</bold>n creating another instance of this class, you still can
     * <br>
     * You can get a running instance of this class after the API gets enabled, see {@link BurchAPI#getPackageScanner()}
     */
    public PackageScanner() {
        this.classesForPackage = new ArrayList<>();
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

    public final InvocationResult<T, InvocationErrorStatus> invokeClass(Class<? extends T> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<? extends T> constructor = null;
        Object toProvide = null;
        try {
            for (Constructor<?> aConstructor : constructors) {
                if (aConstructor.getParameterTypes().length == 0 && constructor == null)
                    constructor = clazz.getDeclaredConstructor();

                if (aConstructor.getParameterTypes().length == 1 && aConstructor.getParameterTypes()[0] == BurchAPI.INSTANCE.getClass()) {
                    toProvide = BurchAPI.INSTANCE;
                    constructor = clazz.getDeclaredConstructor(BurchAPI.INSTANCE.getClass());
                }
            }
        } catch (NoSuchMethodException ex) {
            return new InvocationResult<>(null, InvocationErrorStatus.ERR_INVALID_CONSTRUCTOR);
        }

        if (constructor == null)
            return new InvocationResult<>(null, InvocationErrorStatus.ERR_INVALID_CONSTRUCTOR);

        T object;
        try {
            try {
                object = constructor.newInstance(toProvide);
            } catch (NullPointerException ignored) {
                object = constructor.newInstance();
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            return new InvocationResult<>(null, InvocationErrorStatus.ERR_INVOKATION);
        }

        return new InvocationResult<>(object, InvocationErrorStatus.NULL);
    }

    @SafeVarargs
    public final HashMap<Class<? extends Annotation>, Class<? extends T>> findWithClassConstructorAnnotations(Class<? extends Annotation>... annotations) {
        final HashMap<Class<? extends Annotation>, Class<? extends T>> results = new HashMap<>();
        if (this.result == null) throw new PackageScannerException("Attempting to call findWithClassAnnotations without executing a query!");
        for (Class<? extends Annotation> annotation : annotations) {
            this.result.forEach((clazz) -> {
                if (clazz.getAnnotation(PlayerDataFile.class) != null) {
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
    public final PackageScanner<T> subclassSearchQuery(@Nonnull Package aPackage, @Nonnull Class<?> aClass) {
        this.packageToSearch = aPackage;
        this.currentClassToSearch = aClass;

        try {
            this.classesForPackage = this.getClassesForPackage(this.packageToSearch.getName());
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
            throw new PackageScannerException(("When executing a PackageScanner query, a exception occured, this is likely an issue with your jar file. Please recompile your project, " +
                    "and if this doesnt work open an issue on our GitHub (BurchAPI)"));
        }
    }


    /* https://stackoverflow.com/questions/1810614/getting-all-classes-from-a-package */
    protected final List<Class<? extends T>> getClassesExtendingThis(Package thePackage, Class<?> that)
            throws IOException, ClassNotFoundException, URISyntaxException {
        Logger.debug("Going to attempt to search for classes extending: " + that.getName() + ". Amount of classes grabbed: " + this.classesForPackage.size());
        List<Class<? extends T>> returnValues = new ArrayList<>();

        for (Class<?> clazz : this.classesForPackage) {
            if (that.isAssignableFrom(clazz)) {
                Logger.debug("Found a class that matched! Class found: " + clazz.getName() + ". Class needed");
                try {
                    Class<? extends T> input = (Class<? extends T>) clazz.asSubclass(that);
                    returnValues.add(input);
                } catch (ClassCastException ignored) {
                    Logger.error("Un-expected class when parsing: " + clazz.getName());
                    Logger.error("This is likely an issue with the way you are implementing/extending a class from our API! Please review your code and documentation.");
                }
            }
        }
        this.classesForPackage.clear();
        return returnValues;
    }

    protected final List<Class<?>> getClassesForPackage(String pkgName) throws IOException, URISyntaxException {
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
        } else {
            root = Paths.get(pkg);
        }

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
        return allClasses;
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
     * Returned as a Entry value when using {@link PackageScanner#invokeClass}
     */
    public enum InvocationErrorStatus {
        NULL,
        ERR_INVALID_CONSTRUCTOR,
        ERR_INVOKATION
    }

    public static class InvocationResult<T, InvocationErrorStatus> extends AbstractMap.SimpleEntry<Object, InvocationErrorStatus> {

        public InvocationResult(T key, InvocationErrorStatus value) {
            super(key, value);
        }

        /**
         * Checks if the Invocation of a class was successfully
         * @return A {@link Boolean} true if invocation was successfull
         */
        public final boolean wasSuccessfullyInvoked() {
            return this.getValue() != PackageScanner.InvocationErrorStatus.NULL;
        }
    }
}
