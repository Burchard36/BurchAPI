Steps for contributing:

1. The javadocs
    * When adding new methods that are publicly accessible, they should preferably have a JavaDoc attached to them. 
    
    * The only time you should not provide JavaDocs, is when a method is privately accessible, or your remove the package from
    the javadoc compile task in the pome.xml

2. How to format your PR's
    
    * When you are testing your fork, you should compile using `mvn clean package -Dmaven.javadoc.skip=true`
    This skips the javadoc generation, and allows you to create a tag on your own branch, or depend on the built jar itself. 
    When committing your code, do not use `mvn clean package`, use the flag specifically above so the javadoc code doesn't generate on each commit

    * When you are done testing your fork, and ready to submit a PR, your LAST commit should strictly only be a javadoc build commit,
    to build the javadocs, simply run `mvn clean package`, this will auto generate all docs in the /docs folder, and you can commit off of them
 
    * You can find an Example PR [here](https://github.com/Burchard36/BurchAPI/pull/3)