package FIT.user.Service.Implment;

import FIT.user.Dao.RecognitionDao;
import FIT.user.Entity.Recognition;
import FIT.user.Service.RecognitionService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecognitionServiceImpl implements RecognitionService {

    @Autowired
    RecognitionDao recognitionDao;


    @Override
    public void save(Recognition recognition) {
        recognitionDao.save(recognition);
    }
}
