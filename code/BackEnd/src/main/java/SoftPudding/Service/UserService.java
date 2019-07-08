package SoftPudding.Service;

import SoftPudding.Entity.User;

public interface UserService{

    User findByTel(String tel);

    String Login(String tel, String pwd);
}
