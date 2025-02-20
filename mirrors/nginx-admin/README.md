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
mkdir -p D:/easy-live/mirrors/nginx-admin/conf.d 
mkdir -p D:/easy-live/mirrors/nginx-admin/html
mkdir -p D:/easy-live/mirrors/nginx-admin/logs
mkdir -p D:/easy-live/mirrors/nginx-admin/conf
```
3.2拷贝nginx容器对应的文件默认配置<br>
```shell
docker cp nginx:/etc/nginx/conf.d D:/easy-live/mirrors/nginx-admin
docker cp nginx:/usr/share/nginx/html D:/easy-live/mirrors/nginx-admin
docker cp nginx:/etc/nginx/nginx.conf D:/easy-live/mirrors/nginx-admin/conf/nginx.conf
```
3.3停止并删除容器<br>
```shell
docker stop nginx 
docker rm nginx
```
4.重新启动nginx_admin容器
```shell
docker run -p 3000:3000 --name nginx_admin -v D:/easy-live/mirrors/nginx-admin/conf.d:/etc/nginx/conf.d  -v D:/easy-live/mirrors/nginx-admin/html:/usr/share/nginx/html -v D:/easy-live/mirrors/nginx-admin/logs:/var/log/nginx -v D:/easy-live/mirrors/nginx-admin/conf/nginx.conf:/etc/nginx/nginx.conf -d nginx
```

5.拷贝admin_dist到nginx_admin容器中
```shell
docker cp D:/easy-live/mirrors/nginx-admin/admin_dist nginx_admin:/home
```
