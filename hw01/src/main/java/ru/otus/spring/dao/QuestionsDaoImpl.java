package ru.otus.spring.dao;



import lombok.Getter;
import ru.otus.spring.utils.FileManager;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

@Getter
public class QuestionsDaoImpl implements QuestionsDao {

    final private Map<String,List<String>> questions;

    public QuestionsDaoImpl(String filePath) {
        questions = new HashMap<>();
        var fileManager = new FileManager(filePath);
        List<String[]> csvArray = fileManager.getCsvArray();
        csvArray.forEach(row-> questions.put(row[0], Arrays.asList(Arrays.copyOfRange(row, 1, row.length))));
    }

    @Override
    public List<String> findByQuestions(String questionName) {
        return questions.get(questionName);
    }

    @Override
    public Map<String,List<String>> getAll() {
        return questions;
    }
}
