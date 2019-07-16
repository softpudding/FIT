package FIT.Administer;

import FIT.user.Entity.User;
import FIT.user.Service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@org.springframework.stereotype.Controller
@RequestMapping(path = "/admin")
public class Controller {

    @Autowired
    private Service service;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/test")
    public @ResponseBody String test() {
        return "test success";
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/login")
    public @ResponseBody String login(@RequestBody JSONObject data) {
        return service.login(data.getInteger("id"),
                data.getString("pwd"));
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/ban")
    public @ResponseBody boolean banUser(@RequestBody String tel) {
        User user = userService.findByTel(tel);
        if (user == null) { return false; }
        else {
            service.ban(user);
            return true;
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/relieve")
    public @ResponseBody boolean relieveUser(@RequestBody String tel) {
        User user = userService.findByTel(tel);
        if (user == null) { return false; }
        else {
            service.relieve(user);
            return true;
        }
    }

}
