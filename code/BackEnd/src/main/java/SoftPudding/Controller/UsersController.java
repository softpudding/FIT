package SoftPudding.Controller;

import SoftPudding.Entity.User;
import SoftPudding.Service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import static org.springframework.http.HttpHeaders.ORIGIN;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path = "/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/login")               //  这里的自动登录还没有搞好额...
    public @ResponseBody
    String login(@RequestParam String tel, @RequestParam String password, HttpServletRequest request,
                 HttpServletResponse response) {

        HttpSession session = request.getSession();
        String ss = userService.login(tel, password);
        if (ss.equals("100")) {
            Integer userId = userService.findByTel(tel).getId();
            if (!request.isRequestedSessionIdValid() || session.getAttribute("USERID") == null) {
                System.out.println("No session existed. Make a new session!");
                session.setAttribute("USERID", userId);
                System.out.println("NEW sessionId:  " + session.getId());
                System.out.println("NEW session的USERID:  " + session.getAttribute("USERID"));
                response.setHeader("Access-Control-Allow-Origin", ORIGIN);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.addCookie(new Cookie("USERID", Long.toString(userId)));
                response.addCookie(new Cookie("USERNAME", tel));
                return (ss);
            }
        }
        return ss;             // 记得改回来 这是测试用的
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/sengMessage")
    public @ResponseBody
    void sendMessage(@RequestParam String tel, HttpServletRequest request,
                     HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("USER_TEL", tel);
        String mobile_code = userService.sendMessage(tel).toString();
        session.setAttribute("MOBILE_CODE", mobile_code);
        // 没完成
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/register")
    public @ResponseBody
    String register(@RequestParam String tel, @RequestParam String password,
                    @RequestParam String nickName, @RequestParam String mobile_code) {


        User user = new User();
        user.setTel(tel);
        user.setPassword(password);
        user.setNickName(nickName);
        user.setIsactive(true);
        return null;
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
    @PostMapping(path = "/logout")
    public @ResponseBody
    String logOut(HttpServletRequest request,
                  HttpServletResponse response) {
        try {
            userService.logout(request, response);
            return "登出成功啦";
        } catch (Exception e) {
            return "没有Session啦啦 \n 登出失败啦啦";
        }
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/changeUserInfo")
    public @ResponseBody
    boolean changeUserInfo(@RequestBody JSONObject data) {
        return userService.changeUserInfo(data);
    }

}