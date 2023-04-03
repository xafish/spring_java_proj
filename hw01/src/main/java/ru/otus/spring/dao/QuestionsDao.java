package ru.otus.spring.dao;

import java.util.List;
import java.util.Map;

public interface QuestionsDao
{
    Map<String,List<String>> getAll();
    List<String> findByQuestions(String questionName);
}
