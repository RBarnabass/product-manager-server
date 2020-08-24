FROM adoptopenjdk/openjdk11:jre-11.0.5_10-alpine
ADD /target/product-manager-server.jar product-manager-server.jar
EXPOSE 8080
CMD java -jar product-manager-server.jar