```shell
sudo docker build -t nginx_admin ./docker-compose-dockerfile/nginx-admin
```

```shell
sudo docker run -p 3000:3000 --name easy_live_nginx_admin --network easylive-network --network-alias easy_live_nginx_admin -d nginx_admin
```
