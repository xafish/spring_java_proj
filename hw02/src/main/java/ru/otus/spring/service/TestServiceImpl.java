package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Results;
import ru.otus.spring.utils.IOServiceStreams;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final QuestionsDao questionsDao;

    private final IOServiceStreams ioServiceStreams;

    private final static String ASC_NAME_QUESTION = "What's yor name and surname?";

    @Override
    public Results runTest(){
        PrintStream out = ioServiceStreams.out();
        InputStream in = ioServiceStreams.in();

        Scanner inScanner = new Scanner(in);

        String username = ascName(inScanner);

        Map<String, List<String>> questions = questionsDao.getAll();

        AtomicInteger sNum = new AtomicInteger();
        questions.forEach((q,a)->{
            out.print(q+"?");
                    String userAnswer = inScanner.nextLine();
                    if (a.contains(userAnswer)) {
                        sNum.getAndIncrement();
                    }
                }
        );
        inScanner.close();
        out.printf((sNum.get()==questions.size()?"Well done ":"Good job ")+username+"!\n"
                +"You answered %d questions out of %d correctly \n", sNum.get(),questions.size()
        );
        return new Results(username, questions.size(), sNum.get());
    }





    private String ascName(Scanner in){
        ioServiceStreams.out().print(ASC_NAME_QUESTION);
        return in.nextLine();
    }
}
