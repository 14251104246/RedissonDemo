package com.redisson;

import com.redisson.demo.controller.DemoController;
import io.netty.channel.nio.NioEventLoopGroup;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ClassUtils;

import java.util.concurrent.TimeUnit;

public class RedissonTest {
    private static Logger logger = LoggerFactory.getLogger(RedissonTest.class);

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



    @Test
    public void testGetLock() throws Exception {
        RedissonClient redissonClient = redisson();

        RLock lock = redissonClient.getLock("TEST");
        try {
            lock.lock();
            logger.info("Request Thread - " + Thread.currentThread().getName() + " locked and begun...");
            Thread.sleep(5000); // 5 sec
            logger.info("Request Thread - " + Thread.currentThread().getName() + " ended successfully...");
        } catch (Exception ex) {
            logger.error("Error occurred");
        } finally {
            lock.unlock();
            logger.info("Request Thread - " + Thread.currentThread().getName() + " unlocked...");
        }
    }

    @Test
    public void testTryLock() throws Exception {
        RedissonClient redissonClient = redisson();

        RLock lock = redissonClient.getLock("TEST");
        try {
            lock.tryLock(1000*1,1000*10, TimeUnit.MICROSECONDS);
            logger.info("Request Thread - " + Thread.currentThread().getName() + " locked and begun...");
            Thread.sleep(5000); // 5 sec
            logger.info("Request Thread - " + Thread.currentThread().getName() + " ended successfully...");
        } catch (Exception ex) {
            logger.error("Error occurred");
        } finally {
            lock.unlock();
            logger.info("Request Thread - " + Thread.currentThread().getName() + " unlocked...");
        }
    }

    @Test
    public void testGetLockAllTime() throws Exception {
        RedissonClient redissonClient = redisson();
        RLock lock = redissonClient.getLock("TEST");
        try {
            lock.lock();
            logger.info("Request Thread - " + Thread.currentThread().getName() + " locked and begun...");
            Thread.sleep(5000); // 5 sec
            logger.info("Request Thread - " + Thread.currentThread().getName() + " ended successfully...");
        } catch (Exception ex) {
            logger.error("Error occurred");
        }
    }
}
