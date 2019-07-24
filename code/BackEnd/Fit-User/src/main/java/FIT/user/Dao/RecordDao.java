package FIT.user.Dao;


import FIT.user.Entity.Record;
import FIT.user.Repository.RecordRepository;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public class RecordDao {

    @Autowired
    private RecordRepository recordRepository;

    public JSONArray findByTelAndTime(String tel,  String start_time, String end_time) {
        JSONArray jsonArray = new JSONArray();
        Iterable<Record> iterable = recordRepository.findAllByTel(tel);
        Iterator<Record> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            String time = iterator.next().getTimeStamp();
            if (time.compareTo(start_time) < 0 || time.compareTo(end_time) >0 ) { iterator.remove();}
        }
        return (JSONArray)iterator;
    }

    public void save(Record record) {
        recordRepository.saveAndFlush(record);
    }
}
