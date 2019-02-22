# dockerfile 基础配置
FROM registry.docker-cn.com/library/java:8-jre
MAINTAINER Yu Zhantao <yuzhantao@qq.com>
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /apps/app.jar
WORKDIR /apps/
EXPOSE 16868
# ENTRYPOINT ["java","-jar","./app.jar"]