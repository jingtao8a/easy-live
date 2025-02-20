1.启动容器
```shell
docker run -d --name easy_live_mysql -e MYSQL_ROOT_PASSWORD=2001yuxintao -p 3306:3306 mysql
```
2.将本地sql文件导入容器
```shell
docker cp D:/easy-live/doc/init.sql easy_live_mysql:/home/
```
3.登入容器内mysql应用程序并执行sql
```shell
docker exec -it easy_live_mysql /bin/bash
mysql -uroot -p2001yuxintao
source /home/init.sql
```
