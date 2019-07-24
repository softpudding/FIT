package FIT.user.Service.Implment;

import FIT.user.Dao.UserDao;
import FIT.user.Entity.User;
import FIT.user.Service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * findByTel
     * @return
     */
    @Override
    public User findByTel(String tel) {
        return userDao.findByTel(tel);
    }

    /**
     * save
     * @param
     * @return String
     */
    @Override
    public String save(User user) { userDao.save(user);
        return null;
    }


    @Override
    public Iterable<User> findAll() { return userDao.findAll(); }

    /**
     * 登录

     * @return String
     */
    @Override
    public String login(String tel, String pwd) {
        User user = findByTel(tel);
        if (user == null) {
            return "101";       // 账号不存在
        } else if (user.getIsactive() == 0) {
            return "103";       // 账号被禁用
        } else if (!user.getPassword().equals(pwd)) {
            return "102";       // 密码不正确
        }
        return "100";           // 登录正常
    }

    /**
     * 注册服务

     * @return String
     */
    @Override
    public String register(User user) {
        User test = findByTel(user.getTel());
        if (test == null) {
            userDao.save(user);
            return "1"; // 注册成功
        } else {
            return "0"; // 注册失败
        }
    }

    /**
     * 修改密码

     * @return String
     *
     */
    @Override
    public String changePwd(String tel, String pwd) throws Exception{
        User test = findByTel(tel);
        if (test != null) {
            test.setPassword(pwd);
            userDao.save(test);
            /**
             *
             *   调用别人的API，实现更改密码
            */
            String url = "https://api.im.jpush.cn/v1/users/"+ tel + "/password";
            org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
            HttpPut put = new HttpPut(url);

            String payload = "{" +
                    "\"new_password\": \"" + pwd + "\"" +
                    "}";
            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_JSON);

            System.out.println("\nentity : " + entity);
            put.setEntity(entity);

            // 设置头部
            put.setHeader("Content-Type", "application/json");
            put.setHeader("Authorization","Basic ZmY0YTZiZTQ3MWEyNDhiMjMyNTY5OGQzOjZmNjE1NmUzNDI1ODEyYmJlM2IzMDMzOA==");

            HttpResponse response = client.execute(put);
            System.out.println("\nSending 'PUT' request to URL : " + url);
            System.out.println("Post parameters : " + put.getEntity().getContent());
            System.out.println("TEST payload : " + payload);
            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 发送验证短信
     * @param tel
     * @return
     */
    @Override
    public Integer sendMessage(String tel) {        // tel 就应该是给用户的tel
        String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
        //System.out.println("发短信了啊！！！！");
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        PostMethod method = new PostMethod(Url);
        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = { // 提交短信
                new NameValuePair("account", "C35851249"), // 查看用户名是登录用户中心->验证码短信->产品总览->APIID
                new NameValuePair("password", "14227d803ba627befcad8fbbfb269a3d"), // 查看密码请登录用户中心->验证码短信->产品总览->APIKEY
                // new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", tel), new NameValuePair("content", content),};
        method.setRequestBody(data);
        try {
            client.executeMethod(method);
            String SubmitResult = method.getResponseBodyAsString();

            /* System.out.println(SubmitResult); */
            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();
            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");
            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);
            if ("2".equals(code)) {
                System.out.println("短信提交成功");
                return mobile_code;
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return null;
    }


    /**
     * 修改用户信息
     * @param data
     * @return
     */
    @Override
    public boolean changeUserInfo(JSONObject data) throws Exception{
        User user = userDao.findByTel(data.getString("tel"));
        /**
         * 调用别家的API，更改那边的信息
         */
        String url = "https://api.im.jpush.cn/v1/users/" + data.getString("tel");
        org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(url);
        String payload = "{" +
                //"\"avatar\": \"" + data.getString("avatar") + "\"" +
                "\"nickname\": \"" + data.getString("nickName") + "\"," +
                "\"birthday\": \"" + data.getString("birthday") + "\"," +
                "\"gender\": \"" + data.getInteger("gender") + "\"" +
                "}";
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        System.out.println("\nentity : " + entity);
        put.setEntity(entity);

        // 设置头部
        put.setHeader("Content-Type", "application/json");
        put.setHeader("Authorization","Basic ZmY0YTZiZTQ3MWEyNDhiMjMyNTY5OGQzOjZmNjE1NmUzNDI1ODEyYmJlM2IzMDMzOA==");

        HttpResponse response = client.execute(put);
        System.out.println("\nSending Change User Info'PUT' request to URL : " + url);
        System.out.println("Post parameters : " + put.getEntity().getContent());
        System.out.println("TEST payload : " + payload);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());


        if (user == null) {
            return false;
        }
        else {
            user.setAvatar(data.getString("avatar"));
            user.setNickName(data.getString("nickName"));
            user.setBirthday(data.getString("birthday"));       // 前端直接传yyyy-mm-dd类型的？ 前端设置好输入类型
            user.setGender(data.getInteger("gender"));
            user.setHeight(data.getInteger("height"));
            user.setWeight(data.getDouble("weight"));
            userDao.save(user);
            return true;
        }
    }

}
