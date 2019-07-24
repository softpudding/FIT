package FIT.user.Service.Implment;

import FIT.user.Dao.RecordDao;
import FIT.user.Service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;

public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordDao recordDao;

    @Override
    public String getForm(String tel, String start_time,String end_time) {
        return "test";
    }
}
