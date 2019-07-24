package FIT.user.Service;

import FIT.user.Entity.Recognition;
import com.alibaba.fastjson.JSONObject;

public interface RecognitionService {

    boolean save(Recognition recognition);

    Iterable<Recognition> findAllByTel(String tel);
}
