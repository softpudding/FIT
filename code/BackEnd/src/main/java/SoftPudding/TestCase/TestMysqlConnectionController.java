package SoftPudding.TestCase;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/***
 * 测试是否访问到数据库服务器，项目结束时删除
 *
 ***/


@CrossOrigin(origins = "*" ,maxAge = 3600)
@Controller
@RequestMapping(path="/administer")
public class TestMysqlConnectionController {

    @Autowired
    TestAdministerRepo testAdministerRepo;

    @CrossOrigin(origins = "*" ,maxAge = 3600)
    @GetMapping(path="/login")
    public @ResponseBody
    String getAll(){
        return JSON.toJSONString(testAdministerRepo.findAll()) ;
    }
}
