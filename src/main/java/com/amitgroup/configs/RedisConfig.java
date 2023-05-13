package com.amitgroup.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.redisson.config.TransportMode;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@PropertySource("classpath:application.properties")
@EnableRedisRepositories(basePackages = "com.amit.sample.redis.repositories")
public class RedisConfig {
    private static final Logger logger = LogManager.getLogger(RedisConfig.class);

    @Autowired
    Environment env;

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redissonConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }


    @Bean(destroyMethod = "shutdown")
    public RedissonClient createdRedissonClient() {
//        Config config = Config.fromJSON(new File(jsonConfig));
        Config config = new Config();
        config.setThreads(env.getProperty("com.nip.redis.threads", Integer.class, 16));
        config.setNettyThreads(env.getProperty("com.nip.redis.nettyThreads", Integer.class, 32));
        config.setTransportMode(TransportMode.valueOf(env.getProperty("com.nip.redis.transportMode", "NIO")));
        // config.setCodec(new FstCodec());
        String mode = env.getProperty("com.nip.redis.mode", "single");

        if (mode.equalsIgnoreCase("single")) {
            config.useSingleServer()

                    .setIdleConnectionTimeout(env.getProperty("com.nip.redis.ideConnectionTimeOut", Integer.class, 1000))
                    .setConnectTimeout(env.getProperty("com.nip.redis.connectTimeout", Integer.class, 1000))
                    .setTimeout(env.getProperty("com.nip.redis.timeout", Integer.class, 1000))
                    .setRetryAttempts(env.getProperty("com.nip.redis.retryAttempts", Integer.class, 3))
                    .setRetryInterval(env.getProperty("com.nip.redis.retryInterval", Integer.class, 1500))
                    .setSubscriptionsPerConnection(env.getProperty("com.nip.redis.subscriptionsPerConnection", Integer.class, 20))
                    .setSubscriptionConnectionMinimumIdleSize(env.getProperty("com.nip.redis.subscriptionConnectionMinimumIdleSize", Integer.class, 30))
                    .setSubscriptionConnectionPoolSize(env.getProperty("com.nip.redis.subscriptionConnectionPoolSize", Integer.class, 50))
                    .setDatabase(env.getProperty("com.nip.redis.database", Integer.class, 0))
                    .setPassword(env.getProperty("com.nip.redis.password", String.class, null))
                    .setClientName(env.getProperty("com.nip.redis.clientName", String.class, null))
                    // rieng
                    .setAddress(env.getProperty("com.nip.redis.address", "127.0.0.1:6379"))
                    .setConnectionPoolSize(env.getProperty("com.nip.redis.connectionPoolSize", Integer.class, 100))
                    .setConnectionMinimumIdleSize(env.getProperty("com.nip.redis.connectionMinimumIdleSize", Integer.class, 50))
                    .setDnsMonitoringInterval(env.getProperty("com.nip.redis.dnsMonitoringInterval", Integer.class, 1000));
        } else if (mode.equalsIgnoreCase("sentinel")) {
            config.useSentinelServers()
                    .setIdleConnectionTimeout(env.getProperty("com.nip.redis.ideConnectionTimeOut", Integer.class, 1000))
                    .setConnectTimeout(env.getProperty("com.nip.redis.connectTimeout", Integer.class, 1000))
                    .setTimeout(env.getProperty("com.nip.redis.timeout", Integer.class, 1000))
                    .setRetryAttempts(env.getProperty("com.nip.redis.retryAttempts", Integer.class, 3))
                    .setRetryInterval(env.getProperty("com.nip.redis.retryInterval", Integer.class, 1500))
                    .setSubscriptionsPerConnection(env.getProperty("com.nip.redis.subscriptionsPerConnection", Integer.class, 20))
                    .setSubscriptionConnectionMinimumIdleSize(env.getProperty("com.nip.redis.subscriptionConnectionMinimumIdleSize", Integer.class, 30))
                    .setSubscriptionConnectionPoolSize(env.getProperty("com.nip.redis.subscriptionConnectionPoolSize", Integer.class, 50))
                    .setDatabase(env.getProperty("com.nip.redis.database", Integer.class, 0))
                    .setPassword(env.getProperty("com.nip.redis.password", String.class, null))
                    .setClientName(env.getProperty("com.nip.redis.clientName", String.class, null))
                    //
                    .addSentinelAddress(env.getProperty("com.nip.redis.nodes", String[].class, new String[]{"127.0.0.1:26379", "127.0.0.1:26389"}))
                    .setSlaveConnectionMinimumIdleSize(env.getProperty("com.nip.redis.slaveConnectionMinimumIdleSize", Integer.class, 24))
                    .setSlaveConnectionPoolSize(env.getProperty("com.nip.redis.slaveConnectionPoolSize", Integer.class, 64))
                    .setMasterConnectionMinimumIdleSize(env.getProperty("com.nip.redis.masterConnectionMinimumIdleSize", Integer.class, 24))
                    .setMasterConnectionPoolSize(env.getProperty("com.nip.redis.masterConnectionPoolSize", Integer.class, 64))
                    .setFailedSlaveCheckInterval(env.getProperty("com.nip.redis.failedSlaveCheckInterval", Integer.class, 60000))
                    .setFailedSlaveReconnectionInterval(env.getProperty("com.nip.redis.failedSlaveReconnectionInterval", Integer.class, 3000))
                    .setReadMode(ReadMode.valueOf(env.getProperty("com.nip.redis.readMode", "SLAVE")))
                    .setSubscriptionMode(SubscriptionMode.valueOf(env.getProperty("com.nip.redis.subscriptionMode", "SLAVE")))
                    .setMasterName(env.getProperty("com.nip.redis.masterName", "master"));
        } else if (mode.equalsIgnoreCase("master")) {

        }

        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory() {
        return new RedissonConnectionFactory(createdRedissonClient());
    }

//    @Bean
//    RedisMessageListenerContainer keyExpirationListenerContainer(RedisConnectionFactory connectionFactory) {
//
//        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
//        listenerContainer.setConnectionFactory(connectionFactory);
//
//        listenerContainer.addMessageListener((message, pattern) -> {
//
//            // event handling comes here
//
//        }, new PatternTopic("__keyevent@*__:expired"));
//        return listenerContainer;
//    }
}
