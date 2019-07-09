package SoftPudding.Dao;

import SoftPudding.Entity.User;
import SoftPudding.Repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByTel() {
        assertEquals("Axe",userDao.findByTel("102").getNickName());
    }

    @Test
    public void findByTel2() {
        assertEquals("Axe",userRepository.findByTel("102").getNickName());
    }

    @Test
    public void findByTel3() {
        User user = userDao.findByTel("103");
        assertNotNull(user);
        User user1 = userDao.findByTel("10101");
        assertNull(user1);
    }

}