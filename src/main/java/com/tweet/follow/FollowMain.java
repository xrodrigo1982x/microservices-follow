package com.tweet.follow;

import com.google.common.collect.Sets;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.thetransactioncompany.cors.CORSFilter;
import com.tweet.follow.controller.FollowController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableResourceServer
@EnableSwagger2
public class FollowMain {

    @Value("${hazelcast.endpoint:localhost:5701}")
    private String hazelcastEndpoint;


    public static void main(String[] args) {
        SpringApplication.run(FollowMain.class, args);
    }

    @Bean
    public CacheManager cacheManager(){
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress(hazelcastEndpoint);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
        return new HazelcastCacheManager(client);
    }

    @Bean
    public Docket configSpringfoxDocketForAll() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Sets.newHashSet(MediaType.APPLICATION_JSON.toString()))
                .select().apis((input) -> input.getHandlerMethod().getBeanType().equals(FollowController.class))
                .build();
    }

    @Bean
    public CORSFilter corsFilter(){
        return new CORSFilter();
    }

}
