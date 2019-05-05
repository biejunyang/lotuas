2.1、Load Balance负载均衡
负载均衡是分布式架构的重点，负载均衡机制决定整个服务器集群的性能和稳定。目前主流的LB方案主要有两种：
1、集中式LB，在服务的提供方和消费方之间使用单独的LB设施（可以是硬件如F5,也可以是软件如ngix），服务消费方统一访问LB设备，由LB负责使用负载均衡策略将请求转发到具体的服务提供方。
2、进程内LB，将LB逻辑集成到服务提供方，消费方从服务注册中心获取到可用的服务列表，然后选择合适的服务提供方调用。Ribbon属于后这，是一个类库，可集成到服务消费方，实现负载均衡。

2.2、Ribbon介绍
Ribbon是Netflix项目在负载均衡子项目，为集群中的客户端之间的通信提供了支持。

Ribbon特性：
a、负载均衡器，提供可插拔的负载均衡规则
b、支持多种通信协议，如HTTP,TCP,UDP
c、在客户端集成负载均衡

Ribbon项目主要字模块：
a、ribbon-core：Ribbon项目的核心，主要包括负载均衡器接口定义，客户端接口定义，内置的负载均衡器实现等API。
b、ribbon-eureka：为Eureka客户端提供的负载均衡实现类，主要用来集成到Eureka客户端中提供负载均衡。
c、ribbon-client：对apache的HttpClient进行了封装，提供了含有负载均衡的Rest服务调用客户端。

2.3、Ribbon负载均衡使用，在客户端集成负载均衡功能
2.3.1、pom.xml文件添加ribbon，ribbon-core，ribbon-httpclient，ribbon-loadbalancer，common-configration，archaius-core，guava依赖


2.3.2、集成到客户端调用：
a、获取Netflix配置管理实例，设置相关属性，如负载均衡时需设置好实际服务列表
ConfigurationManager.getConfigInstance().setProperty("my-client.ribbon.listOfServers",
                "localhost:8788,localhost:8789");

也可以通过配置文件加载属性，如：
ConfigurationManager.loadPropertiesFromResources("ribbon.properties");


b、获取具有负载均衡功能的Rest客户端（ribbon-client提供支持）：
RestClient client=(RestClient) ClientFactory.getNamedClient("my-client");

c、创建请求实例，设置请求URI
HttpRequest request=HttpRequest.newBuilder().uri("/findPerson/1").build();

d、执行请求并且使用负载均衡功能
HttpResponse response=client.executeWithLoadBalancer(request);
String result = response.getEntity(String.class);
System.out.println(result);

2.3.3、常见Ribbon配置：
配置Ribbon时使用netflix的通用配置管理ConfigutationManager来设置属性，可以在程序中手动设置，获取配置实例后getConfigInstance()后设置属性setProperty(name,value)；也可以从配置文件加载属性配置loadPropertiesFromResources()。
属性配置格式如下：
<client>.<namespace>.<property>=<value>
说明：
client：客户端的名称，声明该配置属于哪一个客户端，在使用ClientFactory.getNamedClient()时要传入客户端的名称，即可返回客户端实例。
namespace：配置的命名空间，一般就默认为ribbon
property：属性名称
value：属性值
注意：如果想配置对所有的客户端生效，则可以将客户端的名称去掉。

常见配置：
配置服务器列表：
ribbon.listOfServers=localhost:8088,localhost:8089
Ribbon的配置同样可以在Spring Cloud的配置文件application.yml中。
2.4、Ribbon负载均衡机制
2.4.1、负载均衡的核心机制就是选择合适的服务器，并将请求转发给他处理，Ribbon负载均衡实现机制主要包含三块：
a、Rule规则：负载规则，选择服务器的逻辑组件，这些逻辑决定充服务器列表中返回哪个服务器实例
b、Ping机制：确保服务器的可用状态，主要使用定时器定时访问服务器。
c、ServerList：服务器列表，可以通过静态的配置确定负载的服务器，也可以动态指定，若是动态指定服务器列表，则会有后台的线程来属性该列表。

2.4.2、Ribbon负载均衡器
ILoadBalancer接口：Ribbon负载均衡器接口，主要定义的服务器的操作，如添加服务器，选择合适的服务器。Ribbon默认实现类为负载均衡器。
BaseLoadBalancer类：ILoadBalancer的实现类，Ribbon的默认负载均衡器。
Ribbon负载均衡器的主要操作：
添加服务器：addServer()
选择服务器：chooseServer()
设置负载规则：setRule()
设置Ping机制：setPing()
添加服务器列表监听器：

2.4.3、负载均衡规则
Ribbon负载均衡器从服务器列表中选择合适的服务器实例时，会根据负载规则来选择。Rule规则就是选择服务器的逻辑。由IRule接口的chooseServer方法来决定。
Rule负载规则使用：
代码中设置负载规则：
BaseLoadBalancer lb=new BaseLoadBalancer();
lb.setRule(new MyRule(lb));

设置Rule负载配置：
配置属性名为：<client>.ribbon.NFLoadBalancerRuleClassName，如：
ConfigurationManager.getConfigInstance().setProperty("myClient.ribbon.NFLoadBalancerRuleClassName", MyRule.class.getName());

Ribbon内置的负载规则：
RoundRobinRule：ribbon默认的负载规则，通过简单的服务器列表轮询来选择服务器。
AvailabilityFilteringRule：可用过滤负载规则，该规则会忽略一下服务器：
无法连接的服务器：默认情况下，如果连接三个失败，该服务器则会被置为“短路”状态，该状态持续30秒；如果再次连接失败，则“短路”状态的持续时间将会以几何级数添加。设置联机失败的次数属性：niws.loadbalancer.<clientName>.connection-failureCountThreshold
并发数过高的服务器：若是连接到该服务器的并发数过高，则也忽略。设置最高并发数属性：<clientName>.ribbon.ActiveConnectionsLimit
WeightResponseTimeRule：为每个服务器赋予一个权重值，优先选择权重值大的服务器。服务器相应时会动态设置权重的值，服务器的响应时间越长，权重值越小。
ZoneAvoidanceRule：该规则一区域，可用服务器为基础进行服务器选择。使用Zone对服务器进行分类，可以理解为机架或者机房。
BestAvailableRule：忽略短路的服务器，并选择并发数最低的服务器
RandomRule：随机选择服务器
RetryRule：含有充实的逻辑，如果使用RoundRobinRule选择的服务器无法连接，则将会重新选择服务器。

2.4.4、Ping机制
在负载均衡器中提供了Ping机制，通过定时器每隔一段时间会去Ping服务器，判断服务器是否存活。注意单独使用Ribbon时，默认情况下不会开启Ping机制，需要手动激活。
IPing接口：Ping机制的接口定义
DummyPing：IPing的默认实现类。

设置Ping机制属性：
<client-name>.ribbon.NFLoadBalancerPingClassName：配置IPing的实现类
<cleint-name>.ribbon.NFLoadBalancerPingInterval：配置Ping操作的时间间隔


2.4.5、其他配置：
NFLoadBalancerClassName：自定义负载均衡器实现类
NIWSServerListClassName：服务器列表处理类，用来维护服务器列表，Ribbon已经实现动态服务器列表
NIWSSerlerListFilterClassName：用于处理服务器列表拦截


