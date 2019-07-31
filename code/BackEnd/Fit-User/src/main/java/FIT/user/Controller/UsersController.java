package FIT.user.Controller;

import FIT.user.Annotation.UserLoginToken;
import FIT.user.Entity.User;
import FIT.user.Service.RecordService;
import FIT.user.Service.TokenService;
import FIT.user.Service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path = "/user")
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RecordService recordService;


    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/login")               //  这里的自动登录交给了前端
    public @ResponseBody
    String login(@RequestParam String tel, @RequestParam String password) {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        user.setTel(tel);
        user.setPassword(password);
        String ss = userService.login(tel, password);
        if (ss.equals("100")) {
                String token = tokenService.getToken(user);
                jsonObject.put("result",ss);   // 登录结果result
                User tmp = userService.findByTel(tel);
                jsonObject.put("user", tmp);
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

    /**
     * 发送验证短信
     *
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/sendMessage")
    public @ResponseBody
    String sendMessage(@RequestParam String tel) {
        return userService.sendMessage(tel).toString();
    }

    /**
     *  用户注册
     *
     */
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
        user.setHeight(0);
        user.setWeight(0.0);
        user.setGender(0);
        user.setBirthday("1926-08-17");
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

    /**
     * 修改密码（后面是不是需要前端验证下他这个短信）
     *
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/changePassword")    // 记得改回来 这是测试用的  改了已经
    public @ResponseBody
    String changPwd(@RequestParam String tel, @RequestParam String password) {
        try {return userService.changePwd(tel, password);}
        catch (Exception e){return "报错了";}
    }

    /**
     *
     * 修改用户信息，还没搞好（未跟前端调通）
     *
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/changeUserInfo")
    public @ResponseBody
    boolean changeUserInfo(@RequestBody JSONObject data) {
        try { return userService.changeUserInfo(data); }
        catch (Exception e){return false;}
    }


    /**
     *
     * 识别用户token，来验证用户合法性
     *
     */
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

    /*
       接受前端传入的识别信息
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @UserLoginToken
    @PostMapping(path = "/saveRecord")
    public @ResponseBody
    String saveRecord(@RequestBody JSONArray data) {
        //JSONArray jsonArray = data.getJSONArray("test");
        recordService.save(data);
        return "1";
    }


    /**
     * get news
     *
     */

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/news")
    public @ResponseBody JSONArray getNews() throws Exception{

        String st = userService.getNews();
        JSONArray jsonArray = JSONArray.parseArray(st);
        // 我给我自己发请求？ 嗯 就是这个亚子
        return jsonArray;
    }
}