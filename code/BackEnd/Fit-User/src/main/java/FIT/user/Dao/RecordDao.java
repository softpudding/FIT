package FIT.user.Dao;


import FIT.user.Entity.Record;
import FIT.user.Repository.RecordRepository;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecordDao {

    @Autowired
    private RecordRepository recordRepository;

    public JSONArray findByTelAndTime(String tel,  String start_time, String end_time) {
        JSONArray jsonArray = new JSONArray();
        Iterable<Record> iterable = recordRepository.findAllByTel(tel);
        for(Record item: iterable) {
            String time = item.getTimeStamp();
            System.out.println(time);
            if (time.compareTo(start_time) > 0 && time.compareTo(end_time) < 0 &&
                    !item.getFood().equals("苑齐超未知") && !item.getFood().equals( "苑齐超不知道")) {
                jsonArray.add(item);
            }
        }
        return jsonArray;
    }

    public void save(Record record) {
        recordRepository.saveAndFlush(record);
    }
}
