package FIT.user.Controller;

import FIT.user.Annotation.UserLoginToken;
import FIT.user.Service.RecordService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path = "/record")
public class RecordController {

    @Autowired
    private RecordService recordService;


    /**
     *
     * 获取最近五条识别记录
     *
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/getFiveRecord")
    @UserLoginToken
    public @ResponseBody
    String getFFiveRecord(@RequestParam String tel) {

        return recordService.getFiveRecord(tel);
    }

    /**
     *
     * 获取今日识别记录
     *
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/getFOneDayRecord")
    @UserLoginToken
    public @ResponseBody
    String getFOneDayRecord(@RequestParam String tel) {

        return "All";
    }

    /**
     *
     * 获取所有本账号识别记录
     *
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/getAllRecord")
    @UserLoginToken
    public @ResponseBody
    String getAllRecord(@RequestParam String tel) {

        return recordService.getAllRecord(tel);
    }

    /**
     *
     * 获取今天的热量
     *
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/sumCal")
    @UserLoginToken
    public @ResponseBody
    JSONObject getCal(@RequestParam String tel) {

        return recordService.sumCal(tel);
    }

    /**
     *
     * 获取健康报表
     *
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/getForm")
    @UserLoginToken
    public @ResponseBody
    JSONObject getForm(@RequestParam String tel,@RequestParam String beginDate,@RequestParam String endDate) throws Exception{

        try {
            return recordService.sumail(tel, beginDate, endDate);
        }
        catch (Exception e ) {
            JSONObject js = new JSONObject();
            js.put("error","error");
            return js;
        }
    }

}
