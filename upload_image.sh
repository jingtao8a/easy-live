# 将镜像保存为tar文件
sudo docker save -o nginx_admin.tar nginx_admin:latest
sudo docker save -o nginx_web.tar nginx_web:latest
sudo docker save -o easylive.tar easylive:latest
sudo docker save -o elasticsearch.tar elasticsearch:latest
sudo docker save -o mysql.tar mysql:latest
sudo docker save -o redis.tar redis:latest

# 修改文件权限
sudo chmod 777 *.tar

# 替换为你的服务器IP和路径  或 使用FileZilla上传
sudo scp nginx_admin.tar jingtao8a@8.148.77.99:/home/jingtao8a/tmp/
sudo scp nginx_web.tar jingtao8a@8.148.77.99:/home/jingtao8a/tmp
sudo scp easylive.tar jingtao8a@8.148.77.99:/home/jingtao8a/tmp/
sudo scp elasticsearch.tar jingtao8a@8.148.77.99:/home/jingtao8a/tmp/
sudo scp mysql.tar jingtao8a@8.148.77.99:/home/jingtao8a/tmp/
sudo scp redis.tar jingtao8a@8.148.77.99:/home/jingtao8a/tmp/
