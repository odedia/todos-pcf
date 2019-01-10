Todo on PCF
-----------

This is a Todo demo application that uses the following components:
- VueJS application as for frontend.
- Spring Boot with MySQL for backend.
- Runs on Pivotal Cloud Foundry.

The application shows the following abilities of PCF:
1. The VueJS application is packed as a docker image since it needs a version of NPM that is newer that what's available at the current default buildpack. PCF can run cloud-native docker images since it's OCI-compliant.
2. The Spring Boot application uses the Java buildpack. There is no need to create a Dockerfile for the backend.
3. The Spring Boot application binds itself to a MySQL Service provided from the PCF marketplace. 
4. Both frontend and backend share the same base domain URL. PCF handles the routing tier for us. As a result - there is no need to deal with port bindings or to add CORS support to our backend. VueJS maps to the / path and Spring Boot maps to the /api path.

Based on work published by Andrew Hughes at the Okta developer blog: https://developer.okta.com/blog/2018/11/20/build-crud-spring-and-vue
