package FIT.user.Service;

import FIT.user.Entity.User;
import org.junit.Assert;
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
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void findByTel() {
        Assert.assertEquals("Test UserDao.findByTel", "Axe", userService.findByTel("102").getNickName());
    }

    @Test
    public void login() {
        assertEquals("100", userService.login("101", "a"));
        assertEquals("101", userService.login("MakeAmericaGreatAgain", "a"));
        assertEquals("102", userService.login("102", "a"));
        assertEquals("103", userService.login("104", "a"));
    }

    @Test
    public void registerTest() {
        User user = new User();
        user.setNickName("Never use it");
        user.setPassword("Never use it");
        user.setTel("Never use it");
        user.setIsactive(1);
        assertEquals("1", userService.register(user));
        User user1 = userService.findByTel("101");
        assertEquals("0", userService.register(user1));
    }


    @Test
    public void changePwdTest() {
        User user1 = userService.findByTel("101");
        User user = userService.findByTel("Never use it");
        assertEquals("0", userService.changePwd("Never use it", "Change!"));
        assertEquals("1", userService.changePwd("101", "succeed!"));
    }
}