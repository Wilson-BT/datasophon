#!/bin/sh


export JAVA_HOME=/usr/local/jdk1.8.0_333
CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export JAVA_HOME CLASSPATH
# 需要獲取安裝目錄


export KYUUBI_HOME=$INSTALL_PATH/kyuubi
export SPARK_HOME=$INSTALL_PATH/spark3
export PYSPARK_ALLOW_INSECURE_GATEWAY=1
export HIVE_HOME=$INSTALL_PATH/hive
export KAFKA_HOME=$INSTALL_PATH/kafka
export STARROCKS_HOME=$INSTALL_PATH/starrocks
export HBASE_HOME=$INSTALL_PATH/hbase
export HBASE_PID_PATH_MK=$INSTALL_PATH/hbase/pid
export FLINK_HOME=$INSTALL_PATH/flink
export HADOOP_HOME=$INSTALL_PATH/hadoop
export HADOOP_CONF_DIR=$INSTALL_PATH/hadoop/etc/hadoop
export PATH=$PATH:$JAVA_HOME/bin:$SPARK_HOME/bin:$HADOOP_HOME/bin:$HIVE_HOME/bin:$FLINK_HOME/bin:$KAFKA_HOME/bin:$HBASE_HOME/bin
if [ -d $HADOOP_HOME ]; then
    export HADOOP_CLASSPATH=`hadoop classpath`
fi


