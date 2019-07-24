package FIT.user.Controller;

import FIT.user.Annotation.UserLoginToken;
import FIT.user.Entity.User;
import FIT.user.Service.TokenService;
import FIT.user.Service.UserService;
import FIT.user.Service.UserService2;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;

import java.sql.Timestamp;



@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path = "/user")
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    private UserService2 userService2;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/login")               //  这里的自动登录还没有搞好额...
    public @ResponseBody
    String login(@RequestParam String tel, @RequestParam String password) {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        user.setTel(tel);
        user.setPassword(password);
        String ss = userService.login(tel, password);
        if (ss.equals("100")) {
            //Integer userId = userService.findByTel(tel).getId();
                String token = tokenService.getToken(user);
                jsonObject.put("result",ss);   // 登录结果result
                jsonObject.put("user", user);
                jsonObject.put("token",token);
                return jsonObject.toJSONString();
        }
        else {
            jsonObject.put("result", ss);
            jsonObject.put("user", user);
            jsonObject.put("token", "No token!");
            return jsonObject.toJSONString();
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/sendMessage")
    public @ResponseBody
    String sendMessage(@RequestParam String tel) {
        return userService.sendMessage(tel).toString();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/register")
    public @ResponseBody
    String register(@RequestParam String tel, @RequestParam String password,
                    @RequestParam String nickName) {

        User user = new User();
        user.setTel(tel);
        user.setPassword(password);
        user.setNickName(nickName);
        user.setIsactive(1);
        String result = userService.register(user);
        JSONObject jsonObject = new JSONObject();
        if (result.equals("1")) {

            String token = tokenService.getToken(user);
            jsonObject.put("result", result);   // 登录结果result
            jsonObject.put("user", user);
            jsonObject.put("token", token);
            return jsonObject.toJSONString();
        }
        else {
            jsonObject.put("result", result);   // 登录结果result
            jsonObject.put("user", user);
            jsonObject.put("token","No token!");
            return jsonObject.toJSONString();
        }

    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/changePassword")    // 记得改回来 这是测试用的  改了已经
    public @ResponseBody
    String changPwd(@RequestParam String tel, @RequestParam String password) {
        try {return userService.changePwd(tel, password);}
        catch (Exception e){return "报错了";}
    }

    /**
     * 用来测试怎么连接那个公司的API的
     * @param tel
     * @param password
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/apitest")    // 记得改回来 这是测试用的  改了已经
    public @ResponseBody
    String apitest(@RequestParam String tel, @RequestParam String password) {
        try {return userService2.changePwd(tel, password);}
        catch (Exception e){return "Controller 报错了兄dei";}
    }



    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/changeUserInfo")
    public @ResponseBody
    boolean changeUserInfo(@RequestBody JSONObject data) {
        return userService.changeUserInfo(data);
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @UserLoginToken
    @PostMapping(path = "/recoTest")
    public @ResponseBody
    String recoTest () {
        System.out.println("/*");
        System.out.println("*");
        System.out.println("Token identified successfully!");
        System.out.println("*");
        System.out.println("*/");
        return "get!";
    }


}