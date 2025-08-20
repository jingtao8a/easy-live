1.启动容器
```shell
sudo docker run -d --name easy_live_redis -p 6379:6379 --network easylive-network --network-alias easy_live_redis --notify-keyspace-events Ex redis 
```