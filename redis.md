1.安装redis需要的依赖

```shell
yum -y install automake autoconf
yum install gcc
```



2.新建目录，下载解压redis

```shell
mkdir /usr/local/redis
cd /usr/local/redis
wget http://download.redis.io/releases/redis-6.0.5.tar.gz
```

3.解压redis

```shell
tar -zxvf redis-6.0.5.tar.gz
```



4.进入解压后的文件夹，并执行make（可以看一下README.md）

```shell
cd /usr/local/redis/
cd redis-6.0.5/
make
```

5.迁出可执行程序：prefix是你想迁到的文件夹　　　　

```shell
make install PREFIX=/opt/redis6
```

这一步执行完以后，在/opt/redis6目录下就会出现一个bin目录，里面是redis的一些可执行程序，主要是与源码分开



6.创建一个目录  /etc/redis，放置redis的配置文件　　　　

```shell
mkdir /etc/redis
cp /usr/local/redis/redis-6.0.5/redis.conf /etc/redis/6379.conf

# 修改一些配置：
logfile "/var/log/redis/6379.log"　　　　　　# 配置日志文件存放的地方
dir 　　/var/lib/redis/6379　　　　　　　　　　# 配置数据存放的路径
daemonize   yes　　　　　　　　　　　　　　　　 # 以守护进程启动
```

注意事项

```shell
redis 解压目录 /usr/local/redis/redis-6.0.5/
redis 安装目录 /opt/redis6
redis 配置文件目录 /etc/redis/6379.conf
redis 原始配置文件目录 /usr/local/redis/redis-6.0.5/redis.conf
```



配置完成后的启动

```shell
/opt/redis6/bin/redis-server /etc/redis/6379.conf
```

本地客户端

```shell
/opt/redis6/bin/redis-cli
```

