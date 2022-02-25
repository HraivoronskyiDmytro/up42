#up42 Coding challenge

JAVA 8 is required for running the project.

add to /src/main/java/com/up42/qa/Config.java your projectID and apiKey values

to run tests please clone the project & execute in the command promt from project root:"./gradlew test" for Linux\OSX  or "./graldlew.bat test" for Windows
run report - /build/reports/tests/test/index.html

Possible improvements:
- add javadoc documentation
- more strict JSON schema validation
- store credentials and entry URL in a more "safe" place (encrypted and pass as env variable)
- add a possibility to run tests independently with passing all necessary parameters via env variables
- add console reporting for  manual checks