## Burch API
*A library for Spigot/Paper Plugins!*

##Importing

There is a few things you need to know before importing this library:

* Versioning
* What version to use?

So firstly, the API versioning will typically look like this:

 - 01-AA-00

The 01 means a major version rewrite, this typically 
gets upped when something major happens, eg a brand-new module implementation,
major minecraft version change, major refactorings, and even full rewrites. It
is highly recommended when using this API, that you update to the latest API version,
as major version updates likely will not have bugs.

The AA means this is still a major version update, but it will never be:
* Total Rewrite
* Total Refactor

This can still be bumped by Major Module Implementations, however it really just depends on what it is.
These version typically are stable releases that introduce something new, as in:
* Minor API refactoring
* Minor API Addition (New methods)
* Whole list of bug fixes (For individual fixes, skip to next section)
* Minor API rewriting (Typically one or 2 classes, maybe a module as well)

It is recommended you update when this version gets bumped.

And finally, the 00, means a minor version change.  Its is **NOT**!!
Recommended that you update to these version unless you are a developer added to this project, and
are able to add Releases. Or you can be testing something I add for you on here. Do not use any version
higher than 00 when using one of our versions, as you may experience bugs when using these versions!

## Maven importing

I use Maven for importing my project, if you have a gradle version for importing make a commit 
to this file and I will add a gradle sections

Firstly you need the JitPack repository added to your project:

```xml
<repositories>
    <!-- JitPack for GitHub projects -->
    <repository>
        <id>jitpack.io</id>
        <!-- This has "www" because: https://stackoverflow.com/questions/54928706/error-in-library-built-with-jitpack-unable-to-resolve-dependency-for-appdebu -->
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```

And then you need to add the dependency to your project 

**NOTE:** *The version listed in this example, is the* **CORRECT** *version to use!*

```xml
<dependencies>
   <!-- My own plugin API -->
    <dependency>
        <groupId>com.github.Burchard36</groupId>
        <artifactId>BurchAPI</artifactId>
        <version>1.2.3</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

