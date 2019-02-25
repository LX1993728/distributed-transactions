#!/usr/bin/env bash
APP_NAME=account
SERVICE_DIR=$BASE_DIR/config/bashs/service/$APP_NAME/
SERVICE_NAME=roncoo-pay-service-$APP_NAME
JAR_NAME=$SERVICE_NAME\.jar
echo $JAVA_HOME

#source $BASE_DIR/config/bashs/common.sh
#cp -r $BASE_DIR/$SERVICE_NAME/target/$JAR_NAME $BASE_DIR/$SERVICE_NAME/target/lib/ $SERVICE_DIR/

#sleep 5
#./service-account.sh stop
echo  $BASE_DIR