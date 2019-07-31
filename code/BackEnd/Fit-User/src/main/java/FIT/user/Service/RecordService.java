package FIT.user.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface RecordService {

    String getForm(String tel,String start_time,String end_time);

    void save(JSONArray data);

    boolean if_vegetable(String food);

    String getFiveRecord(String tel);


    String getAllRecord(String tel);

    JSONObject sumCal(String tel);

    JSONObject sumail(String tel, String beginDate, String endDate) throws Exception;

    Double getStandard(Integer age, Integer height, Double weight, Integer gender);
}
