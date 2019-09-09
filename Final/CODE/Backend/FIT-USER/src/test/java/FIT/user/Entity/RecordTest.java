package FIT.user.Entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest

public class RecordTest {
    @Test
    public void testRecord() {
        System.out.println("Test for RecordEntity");
        Record record = new Record();
        record.setId(1);
        record.setTel("123");
        record.setTimeStamp("2019");
        record.setCal(123.123);
        record.setFood("test food");
        record.setFat(123.123);
        record.setWeight(123.123);
        record.setProtein(123.123);
        record.setCarbohydrate(123.123);
        record.setIf_is_vegetable(true);
        assertEquals(1,record.getId().intValue());
        assertEquals("123",record.getTel());
        assertEquals("2019",record.getTimeStamp());
        assertEquals("test food",record.getFood());
        assertEquals(123.123,record.getProtein(),0.001);
        assertEquals(123.123,record.getWeight(),0.001);
        assertEquals(123.123,record.getCarbohydrate(),0.001);
        assertEquals(123.123,record.getCal(),0.001);
        assertEquals(123.123,record.getFat(),0.001);
        assertTrue(record.isIf_is_vegetable());
    }

}