package FIT.user.Interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GloablExceptionHandlerTest {

    @Autowired
    private GloablExceptionHandler gloablExceptionHandler;

    @Test
    public void handleException() {
        Exception e = new Exception("yes");
        Object ss = gloablExceptionHandler.handleException(e);
        JSONObject js = (JSONObject) JSON.toJSON(ss);
        assertEquals("yes",js.getString("message"));

        Exception f = new Exception();
        Object dd = gloablExceptionHandler.handleException(f);
        JSONObject jugg = (JSONObject) JSON.toJSON(dd);
        assertEquals("服务器出错",jugg.getString("message"));
    }
}