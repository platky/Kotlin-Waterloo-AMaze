# Kotlin-Waterloo-AMaze
Coding session for the [Kotlin Waterloo P2P Group].

![picture](/src/resources/images/Capture.PNG)

### Setup Instructions:
1. Ensure that you have the [Java 8 SDK] installed (the JRE is not enough)
2. Install a recent version of [IntelliJ Idea] (the free community edition is fine).
    1. If you already have IntelliJ installed, ensure that you have Kotlin plugin 1.3 or newer
4. Clone this repo, start IntelliJ and choose **Create New Project**
5. Select **Kotlin** on the left side and choose **Kotlin/JVM** on the right
6. Click **Next** and select the newly created **Kotlin-Waterloo-AMaze** directory for the **Project location**
7. Click **Finish** and build & run Puzzle1.kt (in package main.kotlin.amaze.puzzles) 
8. Fix the **getNextMove()** method to make the llama go forward and move on to the next puzzle

### Design Choices:
* Primary goal was to simplify the setup process since we only have 10 minutes to get everyone running
    * Avoid any dependencies to eliminate the potential for setup issues
    * Avoid dependency on JUnit.  We removed unit tests but we strongly encourage unit tests for regular projects
    * Avoid build management tools to simplify setup.  We strongly recommend using Gradle for regular projects

   [Kotlin Waterloo P2P Group]: https://www.meetup.com/Kotlin-Waterloo-P2P/
   [Java 8 SDK]: https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
   [IntelliJ Idea]: https://www.jetbrains.com/idea/
   