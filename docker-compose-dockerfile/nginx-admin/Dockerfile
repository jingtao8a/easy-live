# 基于官方 Nginx 镜像
FROM nginx

# 删除默认配置
RUN rm -rf /etc/nginx/conf.d/default.conf

# 复制自定义配置文件
COPY conf/nginx.conf /etc/nginx/nginx.conf
COPY conf.d/ /etc/nginx/conf.d/

# 复制静态文件到 Nginx 的 HTML 目录
COPY html/ /usr/share/nginx/html/

# 可选：创建日志目录（如果需要持久化日志）
RUN mkdir -p /var/log/nginx

COPY admin_dist /home/admin_dist

EXPOSE 3000

# 启动 Nginx（使用官方镜像的默认命令）
CMD ["nginx", "-g", "daemon off;"]