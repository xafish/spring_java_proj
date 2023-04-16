package ru.otus.spring.dao;



import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.spring.utils.FileManager;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class QuestionsDaoImpl implements QuestionsDao {

    final private Map<String,List<String>> questions;

    private final String ASC_NAME_QUESTION = "What's yor name and surname?";

    public QuestionsDaoImpl(FileManager fileManager) {
        questions = new HashMap<>();

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

    @Override
    public void runTest(){
        Scanner in = new Scanner(System.in);

        String username = ascName(in);

        AtomicInteger sNum = new AtomicInteger();
        questions.forEach((q,a)->{

                    System.out.print(q+"?");
                    String userAnswer = in.nextLine();

                    if (a.contains(userAnswer)) {
                        sNum.getAndIncrement();
                    }
                }
        );
        in.close();
        System.out.printf((sNum.get()==questions.size()?"Well done ":"Good job ")+username+"!\n"
                +"You answered %d questions out of %d correctly \n", sNum.get(),questions.size()
        );
    }

    private String ascName(Scanner in){
        System.out.print(ASC_NAME_QUESTION);
        return in.nextLine();
    }
}
