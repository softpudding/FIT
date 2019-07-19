package FIT.user.Entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecognitionTest {

    @Test
    public void RecoTestEntity() {
        Recognition recognition = new Recognition();
        recognition.setId(10086);
        recognition.setTel("10086");
        recognition.setTimeStamp("1926-8-17");
        recognition.setObjectType(1);
        recognition.setFoodK1("gou");
        recognition.setFoodK2("li");
        recognition.setFoodK3("guo");
        recognition.setFoodK4("jia");
        recognition.setFoodK5("sheng");

        assertEquals(10086,recognition.getId().intValue());
        assertEquals("10086",recognition.getTel());
        assertEquals("1926-8-17",recognition.getTimeStamp());
        assertEquals("1",recognition.getObjectType().toString());
        assertEquals("gou",recognition.getFoodK1());
        assertEquals("li",recognition.getFoodK2());
        assertEquals("guo",recognition.getFoodK3());
        assertEquals("jia",recognition.getFoodK4());
        assertEquals("sheng",recognition.getFoodK5());
    }
}