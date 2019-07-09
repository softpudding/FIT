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
        User user = findByTel(tel);
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

    @Override
    public String register(User user) {
        User test = findByTel(user.getTel());
        // 这里应该有一步验证 用户输入验证码是否正确的值。
        // 如果不正确返回“2”   不过为了效率放在前端还是后端，这个待商榷
        if (test == null) {
            userDao.save(user);
            return "1";         // 注册成功
        }
        else {
            return "0";         // 注册失败
        }
    }

    @Override
    public String changePwd(String tel, String pwd) {
        User test = findByTel(tel);
        if (test != null) {
            test.setPassword(pwd);
            userDao.save(test);
            return "1";
        }
        else {
            return "0";
        }
    }
}
