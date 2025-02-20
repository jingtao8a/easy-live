1.下载镜像
```shell
docker pull docker.io/elasticsearch:7.1.1
```
2.启动es容器
```shell
docker run -d --name easy_live_es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.io/elasticsearch:7.1.1
```
3.进入es容器安装ik分词器
```shell
docker exec -it easy_live_es bash
bin/elasticsearch-plugin install https://get.infini.cloud/elasticsearch/analysis-ik/7.1.1
```
4. 重启es容器
```shell
docker restart easy_live_es
```