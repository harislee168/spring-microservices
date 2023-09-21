#!/bin/bash
while ! curl -s http://config-server:8888/actuator/health >/dev/null; do
  echo "Waiting for config-server to become available..."
  sleep 10
done
java -jar product-services.jar