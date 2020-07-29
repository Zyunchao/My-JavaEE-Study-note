# 常用命令



``` shell
su root # 切换到root账户下

sudo useradd aaa # 暂时提升权限来执行sudo 后面的命令

ps -ef | grep tomcat # 查看搜索tomcat对应的的进程PID

kill -9 PID # 强制杀死进程

cd [相对或绝对路径]# 进入目录
	cd  # 进入家目录  home目录 如果是root 进入的/root 其他的进入的是 /home/user(对应的用户)
	cd - # 进入上一次所在的目录
	cd .. # 回退到上一级目录 cd ../../../../

ls -al # 展示出当前目录所有的文件 包括隐藏的  ls /root 列出 root目录下有哪些
ll # ls -l 的简写
pwd   # 看看当前自己在哪个目录下

mkdir # 创建目录  mkdir -p /aaa/bbb 创建多级目录
rm  -rf # 删除文件或者目录 -rf 删除目录并且直接删除无需确认   这个命令危险程度很高  rm -rf / 自杀命令 

cp -r 原路径 目的地 # 复制
mv 源路径 目的地 # 移动

# chown -R 属主名：属组名 文件名   # 将文件名对应的属主 属组进行更改 -R表示包含其子目录
sudo chmod -R 777 文件 # 给一个目录或者文件 最高权限 (如果是测试服务器快速解决权限问题可以这样，但是正式服务器还是要精细的操作)  chmod u+x 文件  （给当前用户某个文件添加可执行权限） 4 2 1


# touch a # 创建一个新文件 a    nginx.conf  touch Student.java

vim a.txt  #  按i 进入编辑模式 insert 插入 按下esc进入命令模式  按下: 进入末行模式 wq    # 打开一个文件， 如果文件名不存在 则新建文件  :wq 可以被简写成 :x
vim a  i  :wq

cat a.txt # 直接将所有的文件内容显示到终端上 读取小文件 如果大文件 还是用vim就行了

tail -100f /usr/local/tomcat/logs/catalina.out # 动态实时查看tomcat日志

# 解压命令
tar zxvf nginx.tar.gz   # -v 可以省略的

# 安装软件 yum方式
yum install tree
```

