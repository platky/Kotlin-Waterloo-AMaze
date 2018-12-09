# Kotlin-Waterloo-AMaze
Coding session for the [Kotlin Waterloo P2P Group].

##### Setup Instructions:
1. Ensure that you have the [Java 8 SDK] installed (the JRE is not enough)
2. Install a recent version of [IntelliJ Idea] (the free community edition is fine).
3. Ensure that you have Kotlin 1.3 or later
    1. **File** -> **Settings** -> **Plugins** -> Search **Kotlin**
4. Clone this repo, start IntelliJ and choose **Create New Project**
5. Select **Kotlin** on the left side and choose **Kotlin/JVM** on the right
6. Click **Next** and select the newly created **Kotlin-Waterloo-AMaze** directory for the **Project location**
7. Click **Finish** and ensure that you can successfully build the project (**Build** -> **Build Project**)
8. Navigate to Puzzle1.kt (in package main.kotlin.amaze.puzzles) and run it to see what happens ;)

##### Design Choices:
* Primary goal was to simplify the setup process since we only have 10 minutes to get everyone running
    * Avoid any dependencies to eliminate the potential for setup issues
    * Avoid dependency on JUnit.  We removed unit tests but we strongly encourage unit tests for regular projects
    * Avoid any build management to simplify setup.  We strongly recommend using Gradle for regular projects

   [Kotlin Waterloo P2P Group]: https://www.meetup.com/Kotlin-Waterloo-P2P/
   [Java 8 SDK]: https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
   [IntelliJ Idea]: https://www.jetbrains.com/idea/
   