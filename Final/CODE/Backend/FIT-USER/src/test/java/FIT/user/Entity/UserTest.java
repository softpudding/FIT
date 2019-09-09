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
        user.setBirthday("1999-9-9");
        user.setAvatar("test-Avatar");
        user.setGender(1);
        user.setHeight(183);
        user.setWeight(65.5);
        user.setIsactive(1);
        assertEquals(10086, user.getId().intValue());
        assertEquals("10086", user.getTel());
        assertEquals("10086", user.getPassword());
        assertEquals("LianTong", user.getNickName());
        assertEquals("1", user.getIsactive().toString());
        assertEquals("65.5",user.getWeight().toString());
        assertEquals("183",user.getHeight().toString());
        assertEquals("1999-9-9",user.getBirthday());
        assertEquals("test-Avatar",user.getAvatar());
        assertEquals("1",user.getGender().toString());



    }

}