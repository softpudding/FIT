package FIT.user.Entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Test
    public void testUserEntity() {
        System.out.println("Test for UserEntity");
        User user = new User();
        user.setId(10086);
        user.setTel("10086");
        user.setPassword("10086");
        user.setNickName("LianTong");
        user.setIsactive(true);
        assertEquals(10086, user.getId().intValue());
        assertEquals("10086", user.getTel());
        assertEquals("10086", user.getPassword());
        assertEquals("LianTong", user.getNickName());
        assertEquals(true, user.getIsactive());


    }

    /*
    @Test
    public void getTel() {
    }

    @Test
    public void setTel() {
    }

    @Test
    public void getPassword() {
    }

    @Test
    public void setPassword() {
    }

    @Test
    public void getNickName() {
    }

    @Test
    public void setNickName() {
    }

    @Test
    public void getIsactive() {
    }

    @Test
    public void setIsactive() {
    }
     */
}