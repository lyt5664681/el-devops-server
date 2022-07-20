##############安装环境
FROM openjdk:8-jdk-alpine
MAINTAINER Yuntao.Li lyt5664681@163.com
ARG PROJECT_NAME=el-devops
ARG PROJECT_VERSION=1.0.0
ENV WORK_FILE /apps/${PROJECT_NAME}
#创建目录与调整时区
RUN mkdir -p ${WORK_FILE} && ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" >/etc/timezone
EXPOSE 8899
WORKDIR ${WORK_FILE}
ADD target/${PROJECT_NAME}-${PROJECT_VERSION}.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar