启动所有容器之前需要创建network
```shell
sudo docker network create --driver bridge easylive-network
```
## 存在的bug
> 1. easylive启动时，需要进入docker容器手动启动admin进程
> 2. nginx-admin和nginx-web需要手动启动