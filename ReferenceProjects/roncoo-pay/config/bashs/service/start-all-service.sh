#!/bin/sh

## java env
#export JAVA_HOME=/usr/local/java/jdk1.7.0_45
#export JRE_HOME=$JAVA_HOME/jre

../service/message/service-message.sh start
sleep 5
../service/account/service-account.sh start
sleep 5
../service/accounting/service-accounting.sh start
sleep 5
../service/notify/service-notify.sh start
sleep 5
../service/trade/service-trade.sh start
sleep 5
../service/user/service-user.sh start
