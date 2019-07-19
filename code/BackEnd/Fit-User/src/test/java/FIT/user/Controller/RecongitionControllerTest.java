package FIT.user.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
@SpringBootTest
public class RecongitionControllerTest {


    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;
    private RecongitionController recongitionController;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }




    @Test
    public void test() throws Exception {
        JSONObject data = new JSONObject();
        data.put("1","a");
        data.put("2","b");
        data.put("3","c");
        data.put("4","d");
        String jsonString = data.toJSONString();
        mvc.perform(MockMvcRequestBuilders.post("/Reco/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test2() throws Exception {
        JSONObject data = new JSONObject();
        data.put("1","a");
        data.put("2","b");
        String jsonString = data.toJSONString();
        mvc.perform(MockMvcRequestBuilders.post("/Reco/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void saveRecoFunctionTest() throws Exception{
        JSONObject data = new JSONObject();
        JSONObject data_food = new JSONObject();
        JSONObject data_food2 = new JSONObject();
        JSONObject data_food3 = new JSONObject();
        JSONObject data_food4 = new JSONObject();
        JSONArray food = new JSONArray();
        data.put("tel","1110");
        data_food.put("class","蒙牛");
        food.add(data_food);
        data_food2.put("class","伊利");
        food.add(data_food2);
        data_food3.put("class","光明");
        food.add(data_food3);
        data_food4.put("class","比利·海灵顿");
        food.add(data_food4);
        data.put("predictions",food);
        String jsonString = data.toJSONString();
        System.out.println(data);
        mvc.perform(MockMvcRequestBuilders.post("/Reco/saveReco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}