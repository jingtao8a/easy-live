1.下载nginx镜像
```shell
docker pull nginx
```
2.启动nginx容器
```shell
docker run --name nginx -p 80:80 -d nginx
```
3.从nginx容器中映射核心文件<br>
3.1本地创建文件目录
```shell
mkdir -p D:/easy-live/mirrors/nginx-web/conf.d 
mkdir -p D:/easy-live/mirrors/nginx-web/html
mkdir -p D:/easy-live/mirrors/nginx-web/logs
mkdir -p D:/easy-live/mirrors/nginx-web/conf
```
3.2拷贝nginx容器对应的文件默认配置<br>
```shell
docker cp nginx:/etc/nginx/conf.d D:/easy-live/mirrors/nginx-web
docker cp nginx:/usr/share/nginx/html D:/easy-live/mirrors/nginx-web
docker cp nginx:/etc/nginx/nginx.conf D:/easy-live/mirrors/nginx-web/conf/nginx.conf
```
3.3停止并删除容器<br>
```shell
docker stop nginx 
docker rm nginx
```
4.重新启动nginx_web容器
```shell
docker run -p 3001:3001 --name nginx_web -v D:/easy-live/mirrors/nginx-web/conf.d:/etc/nginx/conf.d  -v D:/easy-live/mirrors/nginx-web/html:/usr/share/nginx/html -v D:/easy-live/mirrors/nginx-web/logs:/var/log/nginx -v D:/easy-live/mirrors/nginx-web/conf/nginx.conf:/etc/nginx/nginx.conf -d nginx
```

5.拷贝web_dist到nginx_web容器中
```shell
docker cp D:/easy-live/mirrors/nginx-web/web_dist nginx_web:/home
```
