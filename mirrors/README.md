容器之间相互访问需要使用docker network命令
```shell
docker network create -d bridge easy-live-net
```
这里为了简便，easy-live不创建docker容器，其它中间件还是创建docker容器