package com.burchard36.api.utils;

import com.burchard36.api.BurchAPI;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * A generic package scanner, that will scan a specific {@link Package} for a {@link Class} that
 * extends another {@link Class}. Not really intended for users of this API to use, but feel free to.
 */
public class PackageScanner {

    private Package packageToSearch;
    private Class<?> currentClassToSearch;

    /**
     * While not recommended creating another instance of this class, you still can
     *
     * You can get a running instance of this class after the API gets enabled, see {@link BurchAPI}
     */
    public PackageScanner() {

    }

    public final PackageScanner setQuery(Package aPackage, Class<?> aClass) {
        this.packageToSearch = aPackage;
        this.currentClassToSearch = aClass;
        return this;
    }

    public final <T> List<Class<? extends T>> execute() {
        try {
            return this.getClassesExtendingThis(this.packageToSearch, this.currentClassToSearch);
        } catch (IOException | ClassNotFoundException | URISyntaxException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    protected <T> List<Class<? extends T>> getClassesExtendingThis(Package thePackage, Class<?> that)
            throws IOException, ClassNotFoundException, URISyntaxException {
        List<Class<?>> classes = getClassesForPackage(thePackage.getName());
        Logger.debug("Going to attempt to search for classes extending: " + that.getName() + ". Amount of classes grabbed: " + classes.size());
        List<Class<? extends T>> returnValues = new ArrayList<>();

        for (Class<?> clazz : classes) {
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

        return returnValues;
    }

    protected List<Class<?>> getClassesForPackage(final String pkgName) throws IOException, URISyntaxException {
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
                } catch (final ClassNotFoundException | StringIndexOutOfBoundsException ignored) {
                    ignored.printStackTrace();
                }
            });
        }
        return allClasses;
    }

}
