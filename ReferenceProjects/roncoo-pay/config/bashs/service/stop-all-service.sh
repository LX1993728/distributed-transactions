#!/bin/sh

## java env
#export JAVA_HOME=/usr/local/java/jdk1.7.0_45
#export JRE_HOME=$JAVA_HOME/jre

../service/account/service-account.sh stop
../service/accounting/service-accounting.sh stop
../service/notify/service-notify.sh stop
../service/trade/service-trade.sh stop
../service/user/service-user.sh stop
../service/message/service-message.sh stop
