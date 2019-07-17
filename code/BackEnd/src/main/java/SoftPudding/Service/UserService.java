package SoftPudding.Service;

import SoftPudding.Entity.User;

public interface UserService{

    User findByTel(String tel);

    String Login(String tel, String pwd);

    String register(User user);

    String changePwd(String tel, String pwd);
}
