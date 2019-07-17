package FIT.user.Service.Implment;

import FIT.user.Dao.RecognitionDao;
import FIT.user.Service.RecognitionService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecognitionServiceImpl implements RecognitionService {

    @Autowired
    RecognitionDao recognitionDao;

    @Override
    public String recognieFood(JSONObject recoData) {

        /**
         *
         * 将请求发送给识别后端,得到识别
         *
         * String result = SHIBIE(  这个看YX想要啥  )
         *
         * if (识别成功)
         *   处理一下Recognition
         *   reDao.save(recognition)
         *
         *
         */
        return "1";
    }
}
