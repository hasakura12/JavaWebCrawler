# Get started
## Build
`./gradlew build`


Package multiple modules (i.e. app is dependent on core module) into one fat jar:

`gradle shadowJar`

## Run
`java -jar app/build/libs/app-all.jar`  

## Run tests
`./gradew test`