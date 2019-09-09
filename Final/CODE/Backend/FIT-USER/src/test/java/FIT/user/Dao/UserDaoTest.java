package FIT.user.Dao;

import FIT.user.Entity.User;
import FIT.user.Repository.UserRepository;
import org.junit.Assert;
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

public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByTel() {
        Assert.assertEquals("队友呢", userDao.findByTel("6827").getNickName());
    }

    @Test
    public void findByTel2() {
        Assert.assertEquals("队友呢", userRepository.findByTel("6827").getNickName());
    }

    @Test
    public void findByTel3() {
        User user = userDao.findByTel("6827");
        assertNotNull(user);
        User user1 = userDao.findByTel("never use this string as tel");
        assertNull(user1);
    }

    @Test
    public void saveTest() {
        User user = userDao.findByTel("6827");
        user.setNickName("看看你到底变没变");
        userDao.save(user);
        User user1 = userDao.findByTel("6827");
        String nowName;
        nowName = user1.getNickName();
        assertEquals("看看你到底变没变", nowName);
    }

    @Test
    public void findAllTest() {
        Iterator<User> ss = userDao.findAll().iterator();
        assertEquals("a",ss.next().getNickName());
    }

}