package ru.otus.spring.dao;



import org.springframework.stereotype.Component;
import ru.otus.spring.utils.FileManager;

import java.util.*;

@Component
public class QuestionsDaoImpl implements QuestionsDao {

    final private FileManager fileManager;

    final private Map<String,List<String>> questions = new HashMap<>();

    private final static String ASC_NAME_QUESTION = "What's yor name and surname?";

    public QuestionsDaoImpl(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    private Map<String, List<String>> getQuestions() {
        if (questions.isEmpty()) {
            List<String[]> csvArray = fileManager.getCsvArray();
            csvArray.forEach(row-> this.questions.put(row[0], Arrays.asList(Arrays.copyOfRange(row, 1, row.length))));
        }
        return questions;
    }

    @Override
    public List<String> findByQuestions(String questionName) {
        return getQuestions().get(questionName);
    }

    @Override
    public Map<String,List<String>> getAll() {
        return getQuestions();
    }

}
