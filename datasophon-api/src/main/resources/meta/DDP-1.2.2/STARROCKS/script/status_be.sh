#!/bin/bash
#
#  Licensed to the Apache Software Foundation (ASF) under one or more
#  contributor license agreements.  See the NOTICE file distributed with
#  this work for additional information regarding copyright ownership.
#  The ASF licenses this file to You under the Apache License, Version 2.0
#  (the "License"); you may not use this file except in compliance with
#  the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

usage="Usage: start_be.sh (status) be "

# if no args specified, show usage
if [ $# -le 1 ]; then
  echo $usage
  exit 1
fi
startStop=$1
shift
command=$1
SH_DIR=`dirname $0`

curdir=`dirname "$0"`
curdir=`cd "$curdir"; pwd`
PID_DIR=`cd "$curdir"; pwd`
pid=$PID_DIR/be.pid

function get_json(){
  echo "${1//\"/}" | sed "s/.*$2:\([^,}]*\).*/\1/"
}
status(){
   if [ -f $pid ]; then
      ARGET_PID=`cat $pid`
      echo "pid is $ARGET_PID"
      kill -0 $ARGET_PID
      if [ $? -eq 0 ]; then
        echo "$command is running "
      else
        NEW_ARGET_PID=`ps -ef | grep starrocks_be | grep -v grep | awk -F ' ' '{print $2}'`
        if [ -n "$NEW_ARGET_PID" ]; then
          echo "$command  is running"
        else
          echo "$command  is not running"
        fi
      fi
    else
      echo "$command  pid file is not exists"
      exit 1
  	fi
}
case $startStop in
  (status)
	  status
	;;
  (*)
    echo $usage
    exit 1
    ;;
esac


echo "End $startStop $command."
