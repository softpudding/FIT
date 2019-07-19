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

import javax.persistence.Column;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path = "/Reco")
public class RecongitionController {

    @Autowired
    RecognitionService recognitionService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/test")
    public @ResponseBody boolean test(@RequestBody JSONObject data) {
        int ss = data.size();
        if (ss >=3) {
            System.out.println(">=3");
            System.out.println(data.getString("1"));
            return true;
        }
        else {
            System.out.println("<3");
            System.out.println(data.getString("1"));
            return false;
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/saveReco")
    public @ResponseBody
    boolean saveReco(@RequestBody JSONObject data) {
        Recognition recognition = new Recognition();
        System.out.println("接收中!");
        String tel = data.getString("tel");
        recognition.setTel(tel);
        System.out.println("开始存数据库");


        JSONArray jsonArray = data.getJSONArray("predictions");
        Integer js_size = jsonArray.size();
        System.out.println(js_size);
        JSONObject js0= (JSONObject) jsonArray.get(0);
        recognition.setFoodK1(js0.getString("class"));
        /*
        if (js_size >= 2) {
            JSONObject js1 = (JSONObject) jsonArray.get(1);
            recognition.setFoodK2(js1.getString("class"));
        }
        else if (js_size >= 3) {
            JSONObject js2 = (JSONObject) jsonArray.get(2);
            recognition.setFoodK3(js2.getString("class"));
        }
        else if (js_size >= 4) {
            JSONObject js3 = (JSONObject) jsonArray.get(3);
            recognition.setFoodK4(js3.getString("class"));
        }
        else if (js_size >= 5) {
            JSONObject js4 = (JSONObject) jsonArray.get(4);
            recognition.setFoodK5(js4.getString("class"));
        }

        /*else if(js_size >= 2) {
            recognition.setObjectType(2);
        }


        else {
            recognition.setObjectType(1);
        }
        */


         /*
            获取时间戳
         */
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        String time = sdf.format(date.getTime());
        System.out.println(time);
        recognition.setTimeStamp(time);

        return recognitionService.save(recognition);
    }
}
