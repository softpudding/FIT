package FIT.user.Entity;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest

public class NewsTest {
    @Test
    public void newsTest() {
        System.out.println("Test for NewsEntity");
        News news = new News();
        news.setActive(1);
        news.setId(1);
        news.setNews("Test News");
        news.setTime_stamp("2019");
        news.setTittle("Test tittle");
        assertEquals(1,news.getActive().intValue());
        assertEquals(1,news.getId().intValue());
        assertEquals("Test News",news.getNews());
        assertEquals("Test tittle",news.getTittle());
        assertEquals("2019",news.getTime_stamp());
    }

}