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
    public boolean save(Recognition recognition) {
        return recognitionDao.save(recognition);
    }

    @Override
    public Iterable<Recognition> findAllByTel(String tel) {
        return recognitionDao.findAllByTel(tel);
    }
}
