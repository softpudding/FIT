package SoftPudding.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*" ,maxAge = 3600)
@Controller
@RequestMapping(path="/usr")
public class UsersController {

    //@Autowired
    //UserRepository userRepository;

    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @GetMapping(path="/login")
    public @ResponseBody boolean exist(@RequestParam String accountname, @RequestParam String passwrod){
        if(accountname.equals("admin")&&passwrod.equals("admin"))
            return true;
        else return false;
    }


}