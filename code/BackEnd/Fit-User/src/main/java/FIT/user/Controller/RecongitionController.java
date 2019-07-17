package FIT.user.Controller;


import FIT.user.Annotation.UserLoginToken;
import FIT.user.Service.RecognitionService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path = "/Reco")
public class RecongitionController {

    @Autowired
    RecognitionService recognitionService;

    /**
     * This function is used to recieve request from front end
     * and it will verify the token in http's header. If token
     * is valid, post request to python backend and get the
     * result. At last, return the result and save it in data-
     * base.
     */

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/sendPic")
    @UserLoginToken
    public @ResponseBody
    String picTest (@RequestParam String tel, @RequestParam String pic,      // 最好用JSONObject的，回头要跟前端说要改
                    @RequestParam Integer photoType, @RequestParam Timestamp timestamp) {
        /**
         * 跟前后端调通，识别请求接受，发送，处理（存数据库）
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key","value");
        recognitionService.recognieFood(jsonObject);
        return null;
    }
}
