wget https://github.com/downloads/libevent/libevent/libevent-2.0.21-stable.tar.gz
tar zxvf libevent-2.0.21-stable.tar.gz
cd libevent-2.0.21-stable && ./configure
make && make install

git clone https://github.com/coturn/coturn
cd coturn 
./configure 
make 
make install

which turnserver

cd /usr/local/etc/
cp turnserver.conf.default turnserver.conf

ifconfig

yum install openssl

openssl req -x509 -newkey rsa:2048 -keyout /etc/turn_server_pkey.pem -out /etc/turn_server_cert.pem -days 99999 -nodes

cd /etc
ls -l turn_server_*

vi /usr/local/etc/turnserver.conf

#配置
relay-device=eth1   #与前ifconfig查到的网卡名称一致
listening-ip=43.241.156.188    #内网IP
listening-port=3478
tls-listening-port=5349
relay-ip=43.241.156.188
external-ip=43.241.156.188    #公网IP
relay-threads=50
lt-cred-mech
cert=/etc/turn_server_cert.pem
pkey=/etc/turn_server_pkey.pem
pidfile=”/var/run/turnserver.pid”
min-port=49152
max-port=65535
user=zjf:123456    #用户名密码，创建IceServer时用


turnserver -o -a -f -user=zjf:123456 -r Guangdong