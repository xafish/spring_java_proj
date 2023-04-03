package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.dao.QuestionsDao;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        var questionsDao = applicationContext.getBean(QuestionsDao.class);

        Scanner in = new Scanner(System.in);
        Map<String, List<String>> questions = questionsDao.getAll();
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
        // список ответов на конкретный вопрос
        System.out.printf(questionsDao.findByQuestions("who killed kennedy").toString());

    }
}
