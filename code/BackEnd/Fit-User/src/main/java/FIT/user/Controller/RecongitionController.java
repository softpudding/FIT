package FIT.user.Controller;


import FIT.user.Annotation.UserLoginToken;
import FIT.user.Entity.Recognition;
import FIT.user.Service.RecognitionService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path = "/Reco")
public class RecongitionController {

    @Autowired
    RecognitionService recognitionService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/saveReco", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void saveReco(@RequestBody JSONObject data) {
        Recognition recognition = new Recognition();
        System.out.println("接收中!");
        System.out.println(data.getString("tel"));
        System.out.println("下一个接收数组");
        System.out.println(data.getString("predictions"));
        System.out.println("下一个接收数组中的单个");
        JSONArray jsonArray = data.getJSONArray("predictions");
        System.out.println(jsonArray.get(0));
        System.out.println(jsonArray.get(1));
        System.out.println(jsonArray.get(2));
        System.out.println(jsonArray.get(3));
        System.out.println("4个已经够了，看看会不会报错");
        System.out.println("看来多了的都会变成NULL");
        //String js = jsonArray.get(0)
    }
}
