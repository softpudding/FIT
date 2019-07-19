package FIT.user.Controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.junit.Assert.*;


@Rollback(value = true)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }

    @Test
    public void loginTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/login")
                .param("tel", "101")
                .param("password", "a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void loginTest2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/login")
                .param("tel", "100")
                .param("password", "a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void registerTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                .param("tel", "101")
                .param("password", "a")
                .param("nickName", "a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void registerTest2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                .param("tel", "Never Used ")
                .param("password", "a")
                .param("nickName", "a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void changePwdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/changePassword")
                .param("tel", "101")
                .param("password", "a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void TokenForDjangoTest() throws Exception {
        String jsonString = "123";
        mvc.perform(MockMvcRequestBuilders.post("/user/recoTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDIifQ.jkd7YsRgACX1WN97Vd3lBTZtv_mZAQKPwpUDRYRPWgM"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void changeUserInfo() throws Exception {
        JSONObject data_t = new JSONObject();
        data_t.put("tel","101");
        data_t.put("avatar","New AVATAR");
        data_t.put("nickName","nn");
        data_t.put("gender",1);
        data_t.put("birthday","1895-7-5");
        data_t.put("weight",55.5);
        data_t.put("height",178);
        String data = data_t.toJSONString();
        mvc.perform(MockMvcRequestBuilders.post("/user/changeUserInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void sendMessageTest() throws Exception {
        /**
        String YxTel = "18621105309";
        mvc.perform(MockMvcRequestBuilders.post("/user/sendMessage")
                .param("tel", YxTel)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

         * 发短信的测试，已经100%了。随后注释掉就ok
         */
    }
}