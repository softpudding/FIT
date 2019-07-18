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
    @PostMapping(path = "/saveReco", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void saveReco(@RequestBody JSONObject data) {
        Recognition recognition = new Recognition();
        System.out.println("接收中!");
        String tel = data.getString("tel");
        JSONArray jsonArray = data.getJSONArray("predictions");
        Integer js_size = (jsonArray.size());
        // 这里要把food 1-5 依次存到数据库了，目前是手写food 1-5 ，不知道怎么用才能用for循环在里面
        System.out.println("开始存数据库");

        /*Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.CHINA);
        String time = dateFormat.format(date.getTime());
         */

        String webUrl = "http://www.taobao.com";
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 输出北京时间
            String time = sdf.format(date.getTime());
            System.out.println(time);
            recognition.setTimeStamp(time);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recognition.setTel(tel);
        JSONObject js0= (JSONObject) jsonArray.get(0);
        recognition.setFoodK1(js0.getString("class"));
        if (js_size >= 2) {
            JSONObject js1 = (JSONObject) jsonArray.get(1);
            recognition.setFoodK2(js1.getString("class"));
        }
        if (js_size >= 3) {
            JSONObject js2 = (JSONObject) jsonArray.get(2);
            recognition.setFoodK3(js2.getString("class"));
        }
        if (js_size >= 4) {
            JSONObject js3 = (JSONObject) jsonArray.get(3);
            recognition.setFoodK4(js3.getString("class"));
        }

        if (js_size >= 5) {
            JSONObject js4 = (JSONObject) jsonArray.get(4);
            recognition.setFoodK5(js4.getString("class"));
        }

        if(js_size >= 2) {
            recognition.setObjectType(2);
        }
        else {
            recognition.setObjectType(1);
        }

        recognitionService.save(recognition);
    }
}
