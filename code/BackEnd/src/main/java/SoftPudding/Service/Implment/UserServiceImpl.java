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

    @Override
    public String Login(String tel, String pwd) {
        User user = userDao.findByTel(tel);
        if (user == null) {
            return "101";       // 账号不存在
        }
        else if (!user.getIsactive()) {
            return "103";       // 账号被禁用
        }
        else if (!user.getPassword().equals(pwd)) {
            return "102";       // 密码不正确
        }
        return "100";           // 登录正常
    }

}
