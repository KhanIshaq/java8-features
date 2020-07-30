# java8-features

# Javadoc Command 1

$ javadoc -d /Users/nsa/Documents/eclipseWorkspace_2018_09/java8/html2 -sourcepath /Users/nsa/Documents/eclipseWorkspace_2018_09/java8/src/main/java -subpackages features

# Javadoc Command 2

$javadoc -d /Users/nsa/Documents/eclipseWorkspace_2018_09/java8/build   -sourcepath /Users/nsa/Documents/eclipseWorkspace_2018_09/java8/src/main/java -subpackages features -use -splitIndex  -windowtitle 'Java Platform, Standard Edition 8 API Specification'  -doctitle 'Java Platform, Standard Edition 8 API Specification'  -header '<b>Java™ SE 8</b>'  -bottom '<font size="-1"><a href="http://bugreport.sun.com/bugreport/">Submit a bug or feature</a><br/>Copyright &copy; 1993, 2014, Oracle and/or its affiliates. All rights reserved.<br/>Oracle is a registered trademark of Oracle Corporation and/or its affiliates.Other names may be trademarks of their respective owners.</font>'   -group "Core Packages" "java.*:com.sun.java.*:org.omg.*"   -group "Extension Packages" "javax.*"  -J-Xmx180m

# Create a file named “options” containing
-d /Users/nsa/Documents/eclipseWorkspace_2018_09/java8/html2 -sourcepath /Users/nsa/Documents/eclipseWorkspace_2018_09/java8/src/main/java -subpackages features

# Create a file named "packages" containing:

     com.mypackage1
     com.mypackage2
     com.mypackage3

- You would then run javadoc with:
$ javadoc @options @packages

# Cross-Compilation Example
The following example uses javac to compile code that will run on a 1.6 VM.

$javac -source 1.6 -target 1.6 -bootclasspath /jdk1.6.0/lib/rt.jar -extdirs "" OldCode.java
