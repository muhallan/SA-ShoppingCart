package com.example.ShoppingCartCommandService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic checkOutTopic(){
        return TopicBuilder.name("ordercreated").build();
    }
    public NewTopic productAddedTopic(){
        return TopicBuilder.name("productAdded").build();
    }
    public NewTopic productDeletedTopic(){
        return TopicBuilder.name("productDeleted").build();
    }

}
