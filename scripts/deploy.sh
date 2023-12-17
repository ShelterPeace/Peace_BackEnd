#!/bin/bash

BUILD_WAR=$(ls /home/ec2-user/xmpi_deploy/target/*.war)
WAR_NAME=$(basename $BUILD_WAR)
echo "> build 파일명: $WAR_NAME" >> /home/ec2-user/xmpi_deploy.log
echo "> debugging: $BUILD_WAR" >> /home/ec2-user/xmpi_deploy.log

echo "> build 파일 복사" >> /home/ec2-user/xmpi_deploy.log
DEPLOY_PATH=/home/ec2-user/tomcat/apache-tomcat-9.0.83/webapps/xmpi.war
cp $BUILD_WAR $DEPLOY_PATH

CURRENT_PID=$(pgrep -f $WAR_NAME)
echo "## 현재 실행중인 애플리케이션 pid: $CURRENT_PID" >> /home/ec2-user/xmpi_deploy.log

if [ -z $CURRENT_PID ]
then
  echo "## 현재 구동중인 애플리케이션 없음" >> /home/ec2-user/xmpi_deploy.log
else
  echo "## kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> XMPI scripts/deploy.sh 종료" >> /home/ec2-user/xmpi_deploy.log