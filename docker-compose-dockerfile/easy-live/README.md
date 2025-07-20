```shell
sudo docker build -t easylive ./docker-compose-dockerfile/easy-live
```

```shell
sudo docker run -it --name easylive  -p 7070:7070 -p 7071:7071 -d easylive
```