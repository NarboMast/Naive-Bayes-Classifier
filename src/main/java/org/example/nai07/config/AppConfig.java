package org.example.nai07.config;

import org.example.nai07.model.DataType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    @Qualifier("yesMap")
    public Map<DataType, Integer> probabilitiesYes() {
        return new HashMap<>();
    }

    @Bean
    @Qualifier("noMap")
    public Map<DataType, Integer> probabilitiesNo() {
        return new HashMap<>();
    }
}
