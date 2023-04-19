import org.junit.jupiter.api.*;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.dao.QuestionsDaoImpl;
import ru.otus.spring.utils.FileManagerImpl;

import java.io.*;

@DisplayName("Тестирование класса-хранилища вопросов")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionDaoTest {
    private QuestionsDao questionsDao;

    @BeforeAll
    void beforeAll(){
        this.questionsDao = new QuestionsDaoImpl(new FileManagerImpl("questions_test.csv"));
    }

    @Test
    public void testDao() throws IOException {
        Assertions.assertTrue(questionsDao.findByQuestions("question1").contains("q1_text1"));
        Assertions.assertTrue(questionsDao.findByQuestions("question2").contains("q2_text1"));
        Assertions.assertTrue(questionsDao.findByQuestions("question2").contains("q2_text2"));
        Assertions.assertEquals(questionsDao.getAll().size(),3);
    }

}
