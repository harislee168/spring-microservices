FROM eclipse-temurin:17

# Install dockerize
ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

WORKDIR /app
COPY target/config-server-1.0-SNAPSHOT.jar /app/config-server.jar
CMD ["dockerize", "-wait", "http://discovery-server:8761", "-timeout", "180s", "java", "-jar", "config-server.jar"]