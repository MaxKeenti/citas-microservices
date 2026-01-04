#!/bin/sh

# Check if the password file environment variable is set and the file exists
if [ -n "$SPRING_DATASOURCE_PASSWORD_FILE" ] && [ -f "$SPRING_DATASOURCE_PASSWORD_FILE" ]; then
    # Read the password from the file and export it as the standard Spring Boot variable
    export SPRING_DATASOURCE_PASSWORD=$(cat "$SPRING_DATASOURCE_PASSWORD_FILE")
fi

# Execute the passed command (the java application)
exec "$@"
