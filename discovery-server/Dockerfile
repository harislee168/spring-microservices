FROM eclipse-temurin:17

# Install dockerize
ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

WORKDIR /app
COPY target/discovery-server-1.0-SNAPSHOT.jar /app/discovery-server.jar
CMD ["dockerize", "-wait", "http://zipkin:9411", "-timeout", "60s", "java", "-jar", "discovery-server.jar"]