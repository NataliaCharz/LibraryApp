#!/bin/sh

export SPRING_DATASOURCE_PASSWORD=$(cat /run/secrets/db_password)

exec java -jar app.jar