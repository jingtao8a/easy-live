FROM openjdk:8-jdk-alpine

WORKDIR /easy-live-workdir

ENV db.host=easy_live_mysql \
    db.port=3306 \
    db.username=root \
    db.password=root \
    redis.host=easy_live_redis \
    redis.port=6379 \
    project.folder=/easy-live-workdir \
    es.host.port=easy_live_es:9200 \
    admin.log.path=/easy-live-workdir/admin-log \
    web.log.path=/easy-live-workdir/web-log

# 安装 FFmpeg
RUN apk add --no-cache ffmpeg

# 验证安装
RUN ffmpeg -version

# 复制应用 JAR 文件和启动脚本
COPY easylive-web-1.0-SNAPSHOT.jar .
COPY easylive-admin-1.0-SNAPSHOT.jar .

# 安装字体配置工具和字体
RUN apk add --update ttf-dejavu fontconfig && rm -rf /var/cache/apk/*

EXPOSE 7070 7071
COPY start.sh .

ENTRYPOINT ["sh", "start.sh"]
