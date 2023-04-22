import org.junit.jupiter.api.*;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.dao.QuestionsDaoImpl;
import ru.otus.spring.domain.Results;
import ru.otus.spring.service.TestService;
import ru.otus.spring.service.TestServiceImpl;
import ru.otus.spring.utils.FileManagerImpl;
import ru.otus.spring.utils.IOServiceStreamsImpl;

import java.io.*;

@DisplayName("Тестирование класса-тестировщка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestServiceTest {
    private TestService testService;

    private ByteArrayOutputStream output;

    @BeforeAll
    void beforeAll(){
        output = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream("testUser\nq1_text1\nq1_text1\nq1_text1\n".getBytes()));
        InputStream inputStream = System.in;
        PrintStream outStream = new PrintStream(output);

        QuestionsDao questionsDao = new QuestionsDaoImpl(new FileManagerImpl("questions_test.csv"));

        this.testService = new TestServiceImpl(questionsDao,new IOServiceStreamsImpl(outStream, inputStream));
    }

    @Test
    public void testService() {
        Results results = testService.runTest();
        Assertions.assertEquals("testUser", results.name());
        Assertions.assertEquals(3, results.cntQuestions());
        Assertions.assertEquals(1, results.rightQuestions());

        Assertions.assertTrue(output.toString().contains("Good job testUser"));
        Assertions.assertTrue(output.toString().contains(String.format("You answered %d questions out of %d correctly \n",results.rightQuestions(), results.cntQuestions())));
    }
}
