package FIT.user.Service;

import FIT.user.Entity.User;
import com.alibaba.fastjson.JSONObject;

public interface UserService {

    User findByTel(String tel);

    String save(User user);

    String login(String tel, String pwd);

    String register(User user);

    String changePwd(String tel, String pwd) throws Exception;

    Integer sendMessage(String tel);

    boolean changeUserInfo(JSONObject data) throws Exception;

    Iterable<User> findAll();

    String  getNews() throws Exception;
}
