package FIT.user.Dao;

import FIT.user.Entity.Record;
import com.alibaba.fastjson.JSONArray;
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
public class RecordDaoTest {
    @Autowired
    private RecordDao recordDao;

    @Test
    public void findByTelAndTime() {
        JSONArray jsonArray = recordDao.findByTelAndTime("22222",
                "2019-08-01 10:51:20","2019-08-01 10:51:27");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertEquals(214,jsonObject.getInteger("id").intValue());
    }

    @Test
    public void saveTest() {
        Record record = new Record();
        record.setCal(123.123);
        record.setFood("test food");
        record.setFat(123.123);
        record.setWeight(123.123);
        record.setProtein(123.123);
        record.setCarbohydrate(123.123);
        record.setIf_is_vegetable(true);
        record.setTel("22222");
        record.setTimeStamp("2019-08-31 10:51:22");
        recordDao.save(record);
        JSONArray jsonArray = recordDao.findByTelAndTime("22222",
                "2019-08-01 10:51:20","2019-08-01 10:51:27");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertEquals(214,jsonObject.getInteger("id").intValue());

    }

}