package ru.otus.spring.dao;



import lombok.Getter;
import ru.otus.spring.utils.FileManager;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class QuestionsDaoImpl implements QuestionsDao {

    final private Map<String,List<String>> questions;

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
        System.out.printf((sNum.get()==questions.size()?"Well done!\n":"")
                +"You answered %d questions out of %d correctly \n", sNum.get(),questions.size()
        );
    }
}
