package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.spring.utils.FileManager;

@Component
@PropertySource("classpath:application.properties")
public class ServicesConfig {
    @Bean
    public FileManager fileManager(@Value("${filename.questions}") String filename) {
        return new FileManager(filename);
    }
}
