package FIT.user.Controller;

import FIT.user.Annotation.UserLoginToken;
import FIT.user.Entity.User;
import FIT.user.Service.TokenService;
import FIT.user.Service.UserService;
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
    String changPwd(@RequestParam String tel, @RequestParam String password, HttpServletRequest request,
                    HttpServletResponse response) {
        HttpSession session = request.getSession();
        System.out.println("session 里面的USERID是：  " + session.getAttribute("USERID"));
        //if (tel == )
        return userService.changePwd(tel, password);
    }



    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/changeUserInfo")
    public @ResponseBody
    boolean changeUserInfo(@RequestBody JSONObject data) {
        return userService.changeUserInfo(data);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/picTest")
    public @ResponseBody
    String picTest (@RequestParam String tel, @RequestParam String pic,      // 最好用JSONObject的，回头要跟前端说要改
                    @RequestParam Integer photoType, @RequestParam Timestamp timestamp) {
        String ss = "Hello";
        String sss = (ss + tel + pic + photoType + timestamp);
        return  sss;
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