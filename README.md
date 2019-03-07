# Overview



## Prerequisites

Docker or Java 8+.

## Docker (preferred)
### Build
- `./build.sh`
    * Creates container image.
     
### Run
- `./run.sh`
    * Start a container named game.
    
## Java
### Build
- `./gradlew clean build` 
    * compiles the sources.
    * runs the tests.
    * runs the static analysis tools: **pmd** and **findbugs**.
    * runs the coverage tool **jacoco**.
    * generates the test, analysis and coverage reports under `build/reports`
     folder.
         

### Run
- `./gradlew run` launches the app.
    For example you can customize all of them with:
    
