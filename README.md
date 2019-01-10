Todo on PCF

This is a todo demo applciation that uses the following:
- VueJS application as for frontend.
- Spring Boot with MySQL for backend.
- Runs on Pivotal Cloud Foundry.

The application shows the following ability of PCF:
1. The VueJS application is packed as a docker image since it needs a version of NPM that is newer that what's available at the currentl buildpack. PCF can run cloud-native docker images since it's containers are OCI-compliant.
2. The Spring Boot application uses the Java buildpack. There is no need to create a Dockerfile for the backend.
3. The Spring Boot application binds itself to a MySQL Service provided from the PCF marketplace. 
4. Both frontend and backend share the same base domain URL. PCF handles the routing tier for us. As a result - there is no need to deal with port bindings or to add CORS support to our backend. VueJS maps to the / path and Spring Boot maps to the /api path.
