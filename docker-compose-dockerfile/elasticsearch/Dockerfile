# 基于官方 Elasticsearch 7.1.1 镜像
FROM docker.io/elasticsearch:7.17.9

# 设置环境变量，使用单节点发现模式
ENV discovery.type=single-node

# 安装 IK 分词器
RUN elasticsearch-plugin install -b https://get.infini.cloud/elasticsearch/analysis-ik/7.17.9

# 可选：如果需要自定义 IK 分词器配置，可以添加配置文件
# COPY config/ik/ /usr/share/elasticsearch/config/ik/

# 暴露 Elasticsearch 端口
EXPOSE 9200 9300

