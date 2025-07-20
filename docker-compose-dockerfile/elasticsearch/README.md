```shell
sudo docker build -t elasticsearch ./docker-compose-dockerfile/elasticsearch
```

```shell
sudo docker run -d --name easy_live_es -p 9200:9200 -p 9300:9300 elasticsearch
```