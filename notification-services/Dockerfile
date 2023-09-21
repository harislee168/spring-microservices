FROM eclipse-temurin:17
WORKDIR /app

COPY target/notification-services-1.0-SNAPSHOT.jar /app/notification-services.jar
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh
CMD ["/app/start.sh"]