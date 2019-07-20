package FIT.user.Dao;

import FIT.user.Entity.Recognition;
import FIT.user.Repository.RecognitionRepository;
import com.mysql.cj.jdbc.IterateBlock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Iterator;

import static org.junit.Assert.*;


@Rollback(value = true)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecognitionDaoTest {

    @Autowired
    private RecognitionDao recognitionDao;

    @Autowired
    private RecognitionRepository recognitionRepository;

    @Test
    public void saveTest() {
        Recognition recognition = new Recognition();
        recognition.setTel("test");
        recognitionDao.save(recognition);
        Iterable<Recognition> re1 = recognitionDao.findAllByTel("test");
        assertNotNull(re1);
    }

    @Test
    public void findByTelTest() {
        Iterable<Recognition> iterable = recognitionDao.findAllByTel("101");
        Iterator<Recognition> ss = iterable.iterator();
        assertEquals("米饭",ss.next().getFoodK1());


    }
}