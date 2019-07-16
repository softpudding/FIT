package FIT.user.Service;

import FIT.user.Entity.User;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    User findByTel(String tel);

    void save(User user);

    String login(String tel, String pwd);

    String register(User user);

    String changePwd(String tel, String pwd);

    boolean logout(HttpServletRequest request, HttpServletResponse response) throws Exception;

    Integer sendMessage(String tel);

    boolean changeUserInfo(JSONObject data);
}
