package SoftPudding.Controller;
import SoftPudding.Entity.User;
import SoftPudding.Service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*" ,maxAge = 3600)
@Controller
@RequestMapping(path="/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @PostMapping(path = "/login")
    public @ResponseBody String login(@RequestParam String account, @RequestParam String password) {

        return userService.Login(account,password);
    }

    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @PostMapping(path = "/register")
    public @ResponseBody boolean login(@RequestBody JSONObject data) {
        User user = new User();
        user.setTel(data.getString("tel"));
        user.setPassword(data.getString("password"));
        user.setNickName(data.getString("nickName"));
        //user.setIsactive(true);
        return userService.register(user);
    }
}