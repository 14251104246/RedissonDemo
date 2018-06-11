package com.redisson.demo.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

/**
 * Created by kl on 2017/09/26.
 * redisson 客户端配置
 */
@ConfigurationProperties(prefix = "spring.redisson")
@Configuration
public class RedissonConfig {

    private String address = "redis://localhost:6379";
    private int connectionMinimumIdleSize = 10;
    private int idleConnectionTimeout = 10000;
    private int pingTimeout = 1000;
    private int connectTimeout = 10000;
    private int timeout = 3000;
    private int retryAttempts = 3;
    private int retryInterval = 1500;
    private int reconnectionTimeout = 3000;
    private int failedAttempts = 3;
    private String password = null;
    private int subscriptionsPerConnection = 5;
    private String clientName = null;
    private int subscriptionConnectionMinimumIdleSize = 1;
    private int subscriptionConnectionPoolSize = 50;
    private int connectionPoolSize = 64;
    private int database = 0;
    private boolean dnsMonitoring = false;
    private int dnsMonitoringInterval = 5000;

    private int thread; //当前处理核数量 * 2

    private String codec = "org.redisson.codec.JsonJacksonCodec";

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() throws Exception {
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectionPoolSize(connectionPoolSize)
                .setDatabase(database)
                .setDnsMonitoring(dnsMonitoring)
                .setDnsMonitoringInterval(dnsMonitoringInterval)
                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setClientName(clientName)
                .setFailedAttempts(failedAttempts)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setReconnectionTimeout(reconnectionTimeout)
                .setTimeout(timeout)
                .setConnectTimeout(connectTimeout)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setPingTimeout(pingTimeout)
                .setPassword(password);
        Codec codec = (Codec) ClassUtils.forName("org.redisson.codec.JsonJacksonCodec", ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec);
        config.setThreads(thread);
        config.setEventLoopGroup(new NioEventLoopGroup());
        config.setUseLinuxNativeEpoll(false);
        return Redisson.create(config);
    }
}

/*
#redis链接地址
spring.redisson.address: localhost:6379
#当前处理核数量 * 2
spring.redisson.thread: 4
#指定编解码
spring.redisson.codec: org.redisson.codec.JsonJacksonCodec;
#最小空闲连接数,默认值:10,最小保持连接数（长连接）
spring.redisson.connectionMinimumIdleSize: 12
#连接空闲超时，单位：毫秒 默认10000;当前连接池里的连接数量超过了最小空闲连接数，
#而连接空闲时间超过了该数值，这些连接将会自动被关闭，并从连接池里去掉
spring.redisson.idleConnectionTimeout: 10000
#ping节点超时,单位：毫秒,默认1000
spring.redisson.pingTimeout: 1000
#连接等待超时,单位：毫秒,默认10000
spring.redisson.connectTimeout: 10000
#命令等待超时,单位：毫秒,默认3000；等待节点回复命令的时间。该时间从命令发送成功时开始计时
spring.redisson.timeout: 3000
#命令失败重试次数，默认值:3
spring.redisson.retryAttempts: 2
#命令重试发送时间间隔，单位：毫秒,默认值:1500
spring.redisson.retryInterval: 1500
#重新连接时间间隔，单位：毫秒,默认值：3000;连接断开时，等待与其重新建立连接的时间间隔
spring.redisson.reconnectionTimeout: 3000
#执行失败最大次数, 默认值：3；失败后直到 reconnectionTimeout超时以后再次尝试。
spring.redisson.failedAttempts: 2
#身份验证密码
#spring.redisson.password:
#单个连接最大订阅数量，默认值：5
spring.redisson.subscriptionsPerConnection: 5
#客户端名称
#spring.redisson.clientName:
#发布和订阅连接的最小空闲连接数，默认值：1；Redisson内部经常通过发布和订阅来实现许多功能。
#长期保持一定数量的发布订阅连接是必须的
spring.redisson.subscriptionConnectionMinimumIdleSize: 1
#发布和订阅连接池大小，默认值：50
spring.redisson.subscriptionConnectionPoolSize: 50
#连接池最大容量。默认值：64；连接池的连接数量自动弹性伸缩
spring.redisson.connectionPoolSize: 64
#数据库编号，默认值：0
spring.redisson.database: 0
#是否启用DNS监测，默认值：false
spring.redisson.dnsMonitoring: false
#DNS监测时间间隔，单位：毫秒，默认值：5000
spring.redisson.dnsMonitoringInterval: 5000
 */