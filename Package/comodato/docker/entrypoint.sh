#!/bin/sh

echo "The application will start in ${START_SLEEP:-0}s..." && sleep ${START_SLEEP:-0}
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "${HOME}/app.jar" "$@"
