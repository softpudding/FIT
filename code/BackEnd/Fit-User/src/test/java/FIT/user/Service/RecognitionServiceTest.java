package FIT.user.Service;

import FIT.user.Entity.Recognition;
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
public class RecognitionServiceTest {

    @Autowired
    RecognitionService recognitionService;

    @Test
    public void findAllByTel() {
        Iterable<Recognition> it = recognitionService.findAllByTel("101");
        Iterator<Recognition> io = it.iterator();
        assertEquals("米饭",io.next().getFoodK1());
    }

    @Test
    public void save() {

        Recognition recognition = new Recognition();
        recognition.setTel("test");
        recognitionService.save(recognition);
        Iterable<Recognition> re1 = recognitionService.findAllByTel("test");
        assertNotNull(re1);

    }
}