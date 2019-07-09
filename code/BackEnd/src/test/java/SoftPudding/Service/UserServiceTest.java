package SoftPudding.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void findByTel() {
        assertEquals("Test UserDao.findByTel","Axe",userService.findByTel("102").getNickName());
    }

    @Test
    public void login() {
        assertEquals("100",userService.Login("101","a"));
        assertEquals("101",userService.Login("MakeAmericaGreatAgain","a"));
        assertEquals("102",userService.Login("102","a"));
        assertEquals("103",userService.Login("104","a"));
    }
}