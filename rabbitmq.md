1.首先安装erlang

```shell
yum -y install esl-erlang_23.0.2-1_centos_7_amd64.rpm
```



2.去官网下载rabbitmq rpm包

http://www.rabbitmq.com/download.html 

```shell
sudo yum -y install rabbitmq-server.noarch 

```

3.按装UI插件

```shell
rabbitmq-plugins enable rabbitmq_management
```



4.允许远程访问（不做了）

```shell
vim etc/rabbitmq/rabbitmq.config
插入[{rabbit, [{loopback_users, []}]}].
```



5.启动rabbitmq服务

``` shell
systemctl  start rabbitmq-server.service

#检查服务是否启动 systemctl status rabbitmq-server.service

http://192.168.10.102:15672/
guest
guest
```

