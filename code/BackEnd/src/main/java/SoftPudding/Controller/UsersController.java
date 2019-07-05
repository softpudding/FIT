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
@RequestMapping(path="/usr")
public class UsersController {

    @Autowired
    private UserService userService;


    //@Autowired
    //UserRepository userRepository;

    /*
    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @GetMapping(path="/login")
    public @ResponseBody boolean exist(@RequestParam String accountname, @RequestParam String passwrod){
        if(accountname.equals("admin")&&passwrod.equals("admin"))
            return true;
        else return false;
    }
     */

    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @PostMapping(path = "/login")
    public @ResponseBody String login(@RequestBody JSONObject data) {
        String tel = data.getString("account");
        String pwd = data.getString("password");

        User user = userService.findByTel(tel);
        if (user == null) {
            return "101";       // 账号不存在
        } else {
            if (user.getIsactive()) {
                return "103";                   // 账号被禁用
            } else {
                if (user.getPassword().equals(pwd)) {
                    return "100";
                } else {
                    return "102";
                }
            }

        }

    }
}