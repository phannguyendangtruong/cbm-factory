#!/bin/bash

SERVER_ADDRESS="103.170.123.231"
SERVER_USERNAME="kytran"
SERVER_PASSWORD="Amit@123"
SERVER_DIRECTORY="/home/kytran/factory"
PASSWORD_LOCAL="8802"

JAR_FILE="/home/dangtruong/Documents/GitHub/maintenance-portal-be/target/factory-0.0.1-SNAPSHOT.jar"
JAR_NAME="factory-0.0.1-SNAPSHOT.jar"

echo "delete old jar"
sshpass -p $PASSWORD_LOCAL sudo rm $JAR_NAME

echo "Uploading $JAR_NAME to $SERVER_ADDRESS"
sshpass -p $SERVER_PASSWORD scp -o StrictHostKeyChecking=no $JAR_FILE $SERVER_USERNAME@$SERVER_ADDRESS:$SERVER_DIRECTORY/$JAR_NAME

echo "remove nohup"
sshpass -p $PASSWORD_LOCAL sudo rm nohup.out

echo "kill port"
npx kill-port 8802

echo "Starting application..."
sshpass -p $SERVER_PASSWORD ssh -o StrictHostKeyChecking=no $SERVER_USERNAME@$SERVER_ADDRESS "nohup java -jar $SERVER_DIRECTORY/$JAR_NAME &> $SERVER_DIRECTORY/app.log 2>&1 &"
echo "Application started."

