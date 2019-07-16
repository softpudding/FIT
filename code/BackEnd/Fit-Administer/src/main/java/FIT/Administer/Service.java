package FIT.Administer;


import FIT.user.Entity.User;
import FIT.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private UserService userService;

    @Autowired
    private Repo repo;

    public void ban(User user) {
        user.setIsactive(false);
        userService.save(user);
    }

    public void relieve(User user) {
        user.setIsactive(true);
        userService.save(user);
    }

    public String login( Integer id, String pwd) {
        Administer administer = repo.getOne(id);
        if (administer == null) {
            return "101";       // 账号不存在
        }
        else if (!administer.getPassword().equals(pwd)) {
            return "102";       // 密码不正确
        }
        return "100";           // 登录正常
    }
}
