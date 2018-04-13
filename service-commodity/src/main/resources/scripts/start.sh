#TITLE=eureka-server

BASE_HOME=$(cd $(dirname $0) && pwd)
export BASE_HOME
echo "BASE_HOME is "$BASE_HOME
cd $BASE_HOME

#JAVA_OPTIONS="-server -XX:PermSize=64m -XX:MaxPermSize=128m -Xmx512m -Xms256m"

JAVA_OPTIONS="-server -XX:PermSize=64m -XX:MaxPermSize=512m -Xmx512m -Xms256m"
export JAVA_OPTIONS

DEBUG_PORT=19000
export DEBUG_PORT

JAVA_DEBUG="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=${DEBUG_PORT},server=y,suspend=n"
export JAVA_DEBUG

nohup ${JAVA_HOME}/bin/java ${JAVA_OPTIONS} ${JAVA_DEBUG} -Dsprint.config.location=${BASE_HOME}/application.properties -jar Commodity.jar > Commodity.log 2>&1 &
