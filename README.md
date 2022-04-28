# THIS NO LONGER WORKS, THE DATASET THAT THE PROGRAM ACCESSED ONLINE IS NO LONGER AVAILABLE. 
# ALSO IT IS OLD AND BAD, BUT I'LL LEAVE IT UP FOR THE MEMORIES AND AS PROOF I HAVE ALWAYS BEEN A NERD :) 



# Ontario Covid-19 DAE

This is a Java project me and my friend are working on a little bit for school, but mostly for fun (this is actually just extreme overkill for a Grade 12 Computer Science Project... we're nerds). The goal is to create an environment in Java built around [JTablesaw](https://github.com/jtablesaw/tablesaw) for accessing and visualizing the most recent Ontario COVID-19 data.   


# Project Status 
I mean... It's been handed in for school, but this is just the beginning....   
There are known issues, bad enough to be noticable, not bad enough for me to have the energy to write about them, but noticable enough that I won't have to!
things don't work some times and are slow, ok?


## How to run  

### Note: You need to run it in update or setup mode before it will work normally, I think... See step "Run the project"

After installing the project, you must first install the project using Maven.   
Since you're on GitHub already, I'll assume at the minimum your JAVA_HOME is set properly, but just in case check that your <code>usr/libexec/java_home</code> or <code>$JAVA_HOME</code> point to the right directory....

### Make sure the path to your Maven installation is set:  
<code>export PATH=your/path/to/maven/bin:$PATH</code>    (on my Mac, I have this in my /Users/me/.zshrc)

### Go to the directory where the project is downloaded:
<code>cd /directory/where/you/installed/Ontario-Covid-19-DAE-App</code>

### Build the project with dependencies:  
<code>mvn clean install</code>

### Run the project: 
<code>mvn exec:java -Dexec.mainClass=jginfosci.covid19.dae.Environment -Dexec.args=basic</code>  
Argument Options:  
1. basic - loads normally with local datasets, you can still update while running
2. update - updates and then loads, very slow.
3. setup - updates datasets but doesn't launch the program

any time you do an update the terminal will say some stuff that makes it look like it's not working.. it is.

For me this looks like: 
1. <code>(base) superuser@Apostoloss-MacBook-Pro ~ % /usr/libexec/java_home</code>  
/Library/Java/JavaVirtualMachines/jdk-16.0.1.jdk/Contents/Home

2. <code>(base) superuser@Apostoloss-MacBook-Pro ~ % export PATH=/Users/superuser/apache-maven-3.8.1/bin:$PATH</code>

OR

2.5 <code>(base) superuser@Apostoloss-MacBook-Pro ~ % vim ~/.zshrc</code>  
and add to file:  
<code>export PATH=/Users/superuser/apache-maven-3.8.1/bin:$PATH</code>  

3. <code>(base) superuser@Apostoloss-MacBook-Pro ~ %cd /Users/superuser/NetBeansProjects/Ontario-Covid-19-DAE-App</code>  
4. <code>(base) superuser@Apostoloss-MacBook-Pro Ontario-Covid-19-DAE-App % mvn clean install</code>  
  ....a bunch of output from maven  
5. <code>(base) superuser@Apostoloss-MacBook-Pro Ontario-Covid-19-DAE-App % mvn exec:java -Dexec.mainClass=jginfosci.covid19.dae.Environment -Dexec.args=basic</code>   

a bunch more output and then the program runs







You could also run it from your IDE if you aren't cool.


### Authors
Apostolos Paul Geyer \
Nathan Johnson 


### Acknowledgements
Everyone who has contributed to the library [JTablesaw](https://github.com/jtablesaw/tablesaw), which is the backbone of this project. 

### Data sources
[Ontario Data Catalogue](https://data.ontario.ca/) \
[IntelliHealth Ontario](https://intellihealth.moh.gov.on.ca/) \
[Statistics Canada](https://www.statcan.gc.ca/eng/start)
