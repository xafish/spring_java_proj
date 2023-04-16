package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.dao.QuestionsDaoImpl;
import ru.otus.spring.utils.FileManager;

@Configuration
public class AppConfig {
    @Bean
    public QuestionsDao questionsDao(FileManager fileManager){
        return new QuestionsDaoImpl(fileManager);
    }
}
