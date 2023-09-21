FROM eclipse-temurin:17

WORKDIR /app
COPY target/api-gateway-1.0-SNAPSHOT.jar /app/api-gateway.jar
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh
CMD ["/app/start.sh"]


