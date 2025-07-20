启动所有容器之前需要创建network
```shell
sudo docker network create --driver bridge easylive-network
```

bug: easylive启动时，需要进入docker容器手动启动admin进程