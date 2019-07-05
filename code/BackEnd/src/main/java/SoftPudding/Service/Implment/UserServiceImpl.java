package SoftPudding.Service.Implment;

import SoftPudding.Dao.UserDao;
import SoftPudding.Entity.User;
import SoftPudding.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByTel(String tel) {
        return userDao.findByTel(tel);
    }

}
