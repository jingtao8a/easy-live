```shell
sudo docker build -t nginx_web ./docker-compose-dockerfile/nginx-web
```

```shell
sudo docker run -p 3001:3001 --name easy_live_nginx_web --network easylive-network --network-alias easy_live_nginx_web -d nginx_web
```
