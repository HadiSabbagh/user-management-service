FROM openjdk:19
COPY target/user-management-1.0.jar user-management-1.0.jar
ENTRYPOINT ["java","-jar","user-management-1.0.jar"]
#EXPOSE 8080
