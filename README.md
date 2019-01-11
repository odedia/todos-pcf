Todo on PCF
-----------

This is a Todo demo application that uses the following components:
- VueJS application for the frontend.
- Spring Boot with MySQL for backend.
- Runs on Pivotal Cloud Foundry.
- Includes separate concourse pipelines for the frontend and the backend.

The application shows the following abilities of PCF:
1. The VueJS application is packaged as a docker image to illustrate the ability of PCF to run cloud-native docker images.
2. The Spring Boot application uses the Java buildpack. There is no need to create a Dockerfile for the backend.
3. The Spring Boot application binds itself to a MySQL Service provided from the PCF marketplace. 
4. Both frontend and backend share the same base domain URL. PCF handles the routing tier for us. As a result - there is no need to deal with port bindings or to add CORS support to our backend. VueJS maps to the / path and Spring Boot maps to the /api path.

Based on work published by Andrew Hughes at the Okta developer blog: https://developer.okta.com/blog/2018/11/20/build-crud-spring-and-vue

# Running

You can push the code either via the commandline or through Concourse.

Via command line
----------------

Create a mysql service called mysql. The name of the service varies by PCF foundation, so check your plans via `cf marketplace` command. Here's an example:

`cf create-service p.mysql db-small mysql`

Inside the VueJSClient folder, run a docker build and push to dockerhub:

```
docker build -t <your-dockerhub-username>/todos-ui .
docker push odedia/todos-ui
```

Edit manifest.yml to replace `<changeme>` with the route you plan to use and <docker-username> with your docker username.

Push the frontend app:

`cf push`

Inside the `SpringBootVueApplication` folder, edit the `manifest.yml`, replace the route with the route you plan to use in your PCF foundation. Make sure you keep the `/api` in the end.

Push the backend app:
`cf push`


