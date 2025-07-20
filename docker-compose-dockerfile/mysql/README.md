```shell
sudo docker build -t mysql ./docker-compose-dockerfile/mysql
```

```shell
sudo docker run -d --name easy_live_mysql -p 3306:3306 --network easylive-network --network-alias easy_live_mysql mysql
```