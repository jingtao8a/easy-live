# 将镜像保存为tar文件
sudo docker save -o nginx_admin.tar nginx_admin:latest
# 替换为你的服务器IP和路径
sudo scp nginx_admin.tar jingtao8a@8.148.77.99:/tmp/

# 将镜像保存为tar文件
sudo docker save -o nginx_web.tar nginx_web:latest
# 替换为你的服务器IP和路径
sudo scp nginx_web.tar jingtao8a@8.148.77.99:/tmp/

# 将镜像保存为tar文件
sudo docker save -o easylive.tar easylive:latest
# 替换为你的服务器IP和路径
sudo scp easylive.tar jingtao8a@8.148.77.99:/tmp/

# 将镜像保存为tar文件
sudo docker save -o elasticsearch.tar elasticsearch:latest
# 替换为你的服务器IP和路径
sudo scp elasticsearch.tar jingtao8a@8.148.77.99:/tmp/

# 将镜像保存为tar文件
sudo docker save -o mysql.tar mysql:latest
# 替换为你的服务器IP和路径
sudo scp mysql.tar jingtao8a@8.148.77.99:/tmp/

# 将镜像保存为tar文件
sudo docker save -o redis.tar redis:latest
# 替换为你的服务器IP和路径
sudo scp redis.tar jingtao8a@8.148.77.99:/tmp/
