package FIT.user.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;


@Rollback(value = true)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodTypeDaoTest {

    @Autowired
    private FoodTypeDao foodTypeDao;

    @Test
    public void TestFoodTypeDao() {
        assertEquals(1,foodTypeDao.findByName("烤鸭").getId().intValue());
    }

}