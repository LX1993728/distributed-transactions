#!/usr/bin/env bash
current=$(pwd)
echo $current
cd ../../../../roncoo-pay-service-account/target
ls ./
cp roncoo-pay-service-account.jar $current/