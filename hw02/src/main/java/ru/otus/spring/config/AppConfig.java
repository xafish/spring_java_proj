package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.utils.IOServiceStreams;
import ru.otus.spring.utils.IOServiceStreamsImpl;

@Configuration
public class AppConfig {
    @Bean
    public IOServiceStreams ioServiceStreams(){
        return new IOServiceStreamsImpl(System.out,System.in);
    }
}
