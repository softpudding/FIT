package FIT.user.Service;


import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@Rollback(value = true)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordServiceTest {

    // 写到这里了~
    @Autowired
    private RecordService recordService;

    @Test
    public void testStanrdard() {
        assertEquals(1495.8999999999999,recordService.getStandard(21,180,66.0,2),0.0001);
        assertEquals(1727.3999999999999,recordService.getStandard(21,180,66.0,1),0.0001);
        //        Double female = 655 + 9.6 * weight + 1.7 * height - 4.7 * age;   2
        //        Double male = 66 + 13.7 * weight + 5 * height - 6.8 * age;       1
        //        getStandard(Integer age, Integer height, Double weight, Integer gender)
    }

    @Test
    public void testSumail() throws Exception {
        JSONObject jsonObject = recordService.sumail("11111","2019-07-22","2019-07-26");
        assertEquals(250,jsonObject.getInteger("standard_carbohydrate").intValue());
    }

}
