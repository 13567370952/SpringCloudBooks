#spring-cloud-books
## 使用

1.根目录执行mvn clean install后,将所有war包放在document文件夹下
2.点击run-all.bat批量按顺序执行war包
启动时请等待,直到启动完一个,再按回车继续启动下一个
3.也可以按照自己需求启动每个项目的bat文件

以下按照启动顺序依次介绍各项目

### **配置中心**

访问svn上配置文件所处位置，比如svn上某个地址
svn://xxx.xxx.xxx.xxx/project_name/docs
访问 http://localhost:8044/api/dev/docs
即显示docs目录下api-dev.properties文件中相关配置信息

### **服务发现**

访问http://localhost:8001/discovery/
界面如下：
![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/181013_db44c0d2_43183.jpeg "在这里输入图片标题")

### **服务端**

访问http://localhost:8002/swagger-ui.html
可以查看swagger文档，如下图：
![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/181042_2d280d46_43183.jpeg "在这里输入图片标题")
相应技术为springboot+mysql+mybatis+hikariCP

### **API网关**

访问http://localhost:8005/swagger-ui.html
可查看相关接口，目前这里只是利用java web token做了一个安全验证功能，还有就是路由功能

### **客户端**

访问http://localhost:8004/consumer/1
这里是通过访问api网关，获取token放入请求的header中，然后请求服务端接口获取数据。
目前只做了查询bookID的功能，可自行扩展其它crud操作，如http://localhost:8002/swagger-ui.html中的各接口
这里还包括了负载均衡和熔断器功能，如果服务端访问不了，会访问相关故障信息,如下:
​    
    {
    "code": -99,
    "message": "无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！",
    "data": null
    }


### **聚合服务节点**

访问http://localhost:8031/turbine.stream可下载流文件，稍后等控制台启动后会具体说明此文件用处

### **服务监控控制台**

访问http://localhost:8030/hystrix.steam
可以查看某服务在一个server节点或多个server节点上的实时运行情况
比如在搜索框输入http://localhost:8004/hystrix.stream，并在title输入框取名hystrix-test
（注意在点击monitor stream按钮前，先运行http://localhost:8004/consumer/1），结果如下图
![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/181104_c7e009cc_43183.jpeg "在这里输入图片标题")

也可以在搜索框输入http://localhost:8031/turbine.stream，并在title输入框取名turbine-test，看下列结果
![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/181120_76bbda44_43183.jpeg "在这里输入图片标题")


这里因为服务只在我本机上部署，因此两张图是一样的，如果服务还部署在另外一台或多台server上，第二张图会显示多个server运行服务情况。如果此时有很多访问http://localhost:8004/consumer/1的请求，我们可以看见实时运行情况，如下图
![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/181134_1c099321_43183.jpeg "在这里输入图片标题")
