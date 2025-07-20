set -e  # 命令执行失败时立即退出

# 定义回滚函数
rollback() {
    echo "Error: Command failed! Rolling back..."
    # 执行回滚操作，例如删除临时文件、撤销创建的资源等
    [ -f /tmp/temp_file ] && rm /tmp/temp_file
    echo "Rollback complete."
}

NETWORK_NAME="easylive-network"
# 设置错误捕获陷阱
trap rollback ERR

# create easylive-network
# 检查网络是否存在
if ! sudo docker network inspect "$NETWORK_NAME" &>/dev/null; then
    echo "Creating network '$NETWORK_NAME'..."
    sudo docker network create "$NETWORK_NAME"
else
    echo "Network '$NETWORK_NAME' already exists."
fi

# build elasticsearch
sudo docker build -t elasticsearch ./docker-compose-dockerfile/elasticsearch
# build mysql
sudo docker build -t mysql ./docker-compose-dockerfile/mysql
# build nginx_web
sudo docker build -t nginx_web ./docker-compose-dockerfile/nginx-web
# build nginx_admin
sudo docker build -t nginx_admin ./docker-compose-dockerfile/nginx-admin
# build easylive
sudo docker build -t easylive ./docker-compose-dockerfile/easy-live

# run
sudo docker run -d --name easy_live_es -p 9200:9200 -p 9300:9300 --network easylive-network --network-alias easy_live_es elasticsearch
sudo docker run -d --name easy_live_mysql -p 3306:3306 --network easylive-network --network-alias easy_live_mysql mysql
sudo docker run -d --name easy_live_redis -p 6379:6379 --network easylive-network --network-alias easy_live_redis redis
sudo docker run -p 3001:3001 --name easy_live_nginx_web --network easylive-network --network-alias easy_live_nginx_web -d nginx_web
sudo docker run -p 3000:3000 --name easy_live_nginx_admin --network easylive-network --network-alias easy_live_nginx_admin -d nginx_admin
echo "deploy easylive after 5 seconds."
sleep 5
sudo docker run -it --name easylive  -p 7070:7070 -p 7071:7071 -d --network easylive-network --network-alias easylive easylive
