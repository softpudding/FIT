package FIT.user.Service.Implment;

import FIT.user.Dao.RecordDao;
import FIT.user.Dao.UserDao;
import FIT.user.Entity.Record;
import FIT.user.Entity.User;
import FIT.user.Service.RecordService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

@Service
public class RecordServiceImpl implements RecordService {


    /*************
     *
     *     5条报告
     *     所有报告
     *     标准热量计算
     */

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private UserDao userDao;

    @Override
    public String getForm(String tel, String start_time,String end_time) {
        return "test";
    }

    @Override
    public void save(JSONArray data) {
        System.out.println(data.size());

        for(int i=0;i<data.size();i++) {
            Record record = new Record();
            JSONObject jsonObject = data.getJSONObject(i);
            System.out.println(jsonObject);
            /*
            获取时间戳
            */
            SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
            Date date = new Date();// 获取当前时间
            String time = sdf.format(date.getTime());
            System.out.println(time);
            record.setTimeStamp(time);

            record.setTel(jsonObject.getString("tel"));
            record.setCarbohydrate(jsonObject.getDouble("carbohydrate"));
            record.setFat(jsonObject.getDouble("fat"));
            record.setFood(jsonObject.getString("food"));
            record.setProtein(jsonObject.getDouble("protein"));
            record.setWeight(jsonObject.getDouble("weight"));
            record.setCal(jsonObject.getDouble("cal"));
            recordDao.save(record);
        }
    }

    @Override
    public boolean if_vegetable(String food) {
        HashSet<String> vegetableSet = new HashSet<>();
        {
            vegetableSet.add("土豆丝");
            vegetableSet.add("凉拌黄瓜");
            vegetableSet.add("番茄炒蛋");
            vegetableSet.add("炒玉米");
            vegetableSet.add("炒青菜");
            vegetableSet.add("炒芹菜");
            vegetableSet.add("炒豇豆");
            vegetableSet.add("炒花菜");
            vegetableSet.add("炒海带");
            vegetableSet.add("炒冬瓜");
        }
        return false;
    }

    @Override
    public String getAllRecord(String tel) {

        JSONArray jsonArray = recordDao.findByTelAndTime
                (tel,"2019-06-26 14:55:46","3000-09-26 14:55:46 下午");
        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }
        else return "[]";
    }


    /**
     *
     *  并不是获取5条记录
     *  而是获取今天的记录
     */
    @Override
    public String getFiveRecord(String tel) {

        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();// 获取当前时间
        String time = sdf.format(date.getTime());
        System.out.println(time);

        StringBuilder ss = new StringBuilder(time);
        String str = "00:00:00";
        ss.replace(11,19,str);
        System.out.println("begin time: " + ss.toString());
        System.out.println("Compare:  " + ss.toString().compareTo("2019-07-28 14:26:46 PM"));

        JSONArray jsonArray = recordDao.findByTelAndTime(tel,ss.toString(),"3000-09-26");

        if (jsonArray.size()>0) {
            return jsonArray.toJSONString();
        }

        else return "[]";
    }

    public JSONObject sumCal(String tel) {

        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();// 获取当前时间
        String time = sdf.format(date.getTime());
        System.out.println(time);

        StringBuilder ss = new StringBuilder(time);
        String str = "00:00:00";
        ss.replace(11,19,str);
        System.out.println("begin time: " + ss.toString());
        System.out.println("Compare:  " + ss.toString().compareTo("2019-07-28 14:26:46 PM"));

        JSONArray jsonArray = recordDao.findByTelAndTime(tel,ss.toString(),"3000-09-26");
        Integer size = jsonArray.size();


        /*
          如果size = 0，得特殊处理 (并不是这样，而是因为生日为null，就搞不定了)

        if (size == 0) {
            JSONObject js = new JSONObject();

            User u = userDao.findByTel(tel);

            String birthday = u.getBirthday();
            StringBuilder sb = new StringBuilder(birthday);
            String ageString = sb.substring(0,4);
            System.out.println("birth_year test for  " + ageString);
            Integer age = 2019 - Integer.parseInt(ageString);//valueOf(ageString).intValue();
            Double standard = getStandard(age,u.getHeight(),u.getWeight(),u.getGender());

            js.put("standard",standard);
            js.put("result",0.0);
            return js;
        }

         */

        System.out.println("the size : " + size);
        Double sumCal = 0.0;
        for (int i=0; i<size; i++){
            sumCal = sumCal + jsonArray.getJSONObject(i).getDouble("cal");
        }

        JSONObject js = new JSONObject();
        js.put("result",sumCal);


        User u = userDao.findByTel(tel);

        String birthday = u.getBirthday();
        StringBuilder sb = new StringBuilder(birthday);
        String ageString = sb.substring(0,4);
        System.out.println("birth_year test for  " + ageString);
        Integer age = 2019 - Integer.parseInt(ageString);//valueOf(ageString).intValue();
        Double standard = getStandard(age,u.getHeight(),u.getWeight(),u.getGender());

        js.put("standard",standard);
        return js;

    }

    @Override
    public JSONObject sumail(String tel, String beginDate, String endDate) throws Exception{

        /* 返回值 */
        JSONObject result = new JSONObject();
        /* 自己吃了啥 */
        String b1 = beginDate + " 00:00:00";
        String e1 = endDate + " 24:00:00";
        JSONArray jsonArray = recordDao.findByTelAndTime(tel, b1, e1);

        System.out.println("Dao 找完了");
        System.out.println(jsonArray.size());

        double eat_cal = 0.0;
        double eat_protein = 0.0;
        double eat_carbohydrate = 0.0;
        double eat_fat = 0.0;

        System.out.println("开始一个一个的加起来");

        for (Integer ss = 0; ss < jsonArray.size() ; ss++) {
            JSONObject jo = jsonArray.getJSONObject(ss);
            eat_cal = eat_cal + jo.getDouble("cal");
            eat_protein = eat_protein + jo.getDouble("protein");
            eat_carbohydrate = eat_carbohydrate + jo.getDouble("carbohydrate");
            eat_fat = eat_fat + jo.getDouble("fat");
        }


        result.put("eat_cal",eat_cal);
        result.put("eat_protein",eat_protein);
        result.put("eat_carbohydrate",eat_carbohydrate);
        result.put("eat_fat",eat_fat);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date begin_date = df.parse(beginDate);
            Date end_date = df.parse(endDate);
            System.out.println("看看起始时间：  "+ begin_date.toString());
        int nDay = (int) ((end_date.getTime() - begin_date.getTime()) / (24 * 60 * 60 * 1000) + 1 );
        System.out.println("相差天数：  " + nDay);


        /* 首先get个人信息 */
        Double pro = null;
        User u = userDao.findByTel(tel);
        if (u.getGender() == 0)  { pro = 51.0; }
        if (u.getGender() == 1)  { pro = 56.0; }
        if (u.getGender() == 2)  { pro = 46.0; }

        JSONObject jsonObject = sumCal(tel);

        Double cal = jsonObject.getDouble("standard");

        /* 计算正常所需的量 */

        result.put("standard_cal", cal * nDay);
        result.put("standard_protein",pro * nDay);
        result.put("standard_carbohydrate",50 * nDay);
        result.put("standard_fat",67 * nDay);

        return result;
    }

    @Override
    public Double getStandard(Integer age, Integer height, Double weight, Integer gender) {


        Double female = 655 + 9.6 * weight + 1.7 * height - 4.7 * age;
        Double male = 66 + 13.7 * weight + 5 * height - 6.8 * age;
        if( gender == 1) {
            return male;
        }
        if (gender == 2) {
            return female;
        }
        else {
            return 0.5*male + 0.5*female;
        }
    }

}
