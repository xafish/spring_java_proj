import org.junit.jupiter.api.*;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.dao.QuestionsDaoImpl;
import ru.otus.spring.utils.FileManager;

@DisplayName("Тестирование класса-хранилища вопросов")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionDaoTest {
    private QuestionsDao questionsDao;
    @BeforeAll
    void beforeAll(){
        this.questionsDao = new QuestionsDaoImpl(new FileManager("questions_test.csv"));
    }

    @Test
    public void test(){
        Assertions.assertTrue(questionsDao.findByQuestions("question1").contains("q1_text1"));
        Assertions.assertTrue(questionsDao.findByQuestions("question2").contains("q2_text1"));
        Assertions.assertTrue(questionsDao.findByQuestions("question2").contains("q2_text2"));
        Assertions.assertEquals(questionsDao.getAll().size(),3);
    }
}
