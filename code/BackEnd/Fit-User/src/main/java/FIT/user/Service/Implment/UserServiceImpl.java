package FIT.user.Service.Implment;

import FIT.user.Dao.UserDao;
import FIT.user.Entity.User;
import FIT.user.Service.UserService;
import FIT.user.Service.UserService2;
import com.alibaba.fastjson.JSONObject;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService2 userService2;

    @Override
    public User findByTel(String tel) {
        return userDao.findByTel(tel);
    }

    @Override
    public String save(User user) { userDao.save(user);
        return null;
    }

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

    @Override
    public String register(User user) {
        User test = findByTel(user.getTel());
        if (test == null) {
            userDao.save(user);
            return "1";         // 注册成功
        } else {
            return "0";         // 注册失败
        }
    }

    @Override
    public String changePwd(String tel, String pwd) throws Exception{
        User test = findByTel(tel);
        if (test != null) {
            test.setPassword(pwd);
            userDao.save(test);
            /*
                调用别人的API，实现更改密码
             */
/**
            try {
                String url = "https://api.im.jpush.cn" + " /v1/users/" + tel + "/password";

                System.out.println(1);
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost(url);
                System.out.println(2);
                List<BasicNameValuePair> pairs = new ArrayList<>();
                pairs.add(new BasicNameValuePair("new_password", tel));

                request.setEntity(new UrlEncodedFormEntity(pairs));
                System.out.println(3);
                request.setHeader("Content-Type", "application/json");
                request.setHeader("Authorization", "Basic ZmY0YTZiZTQ3MWEyNDhiMjMyNTY5OGQzOjZmNjE1NmUzNDI1ODEyYmJlM2IzMDMzOA==");
                System.out.println(4);
                client.execute(request);
                System.out.println(5);

            }
            catch (Exception e ) {
                System.out.println("Wrong");
            }


            /*
            //String basicUrl = "https://api.im.jpush.cn";

            String url = "https://api.im.jpush.cn" + " /v1/users/" + tel + "/password";
            String params = tel;

            byte[] requestBytes = params.getBytes("utf-8"); // 将参数转为二进制流

            HttpClient httpClient = new HttpClient();// 客户端实例化
            try {
            //  经判断 这里有问题哦！
            //PutMethod putMethod = new PutMethod(url);
            HttpPut putMethod = new HttpPut(url);


            InputStream inputStream = new ByteArrayInputStream(requestBytes, 0,
                    requestBytes.length);
            RequestEntity requestEntity = new InputStreamRequestEntity(inputStream,
                    requestBytes.length, "application/json; charset=utf-8"); // 请求体


            // 设置请求头 Content-Type
                putMethod.addHeader("Content-Type", "application/json");
                putMethod.addHeader("Authorization", "Basic ZmY0YTZiZTQ3MWEyNDhiMjMyNTY5OGQzOjZmNjE1NmUzNDI1ODEyYmJlM2IzMDMzOA==");

                httpClient.executeMethod((HttpMethod) putMethod);
            }
            catch (Exception e) {
                System.out.println("Here is a problem!");
            }

*/


            /**

            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder();
            URI uri = null;
            try {
                uri = builder.setScheme("http")
                        .setHost("https://api.im.jpush.cn")
                        .setPath(" /v1/users/" + tel + "/password")
                        .build();
                System.out.println("walk here!");
                HttpPut put = new HttpPut(uri);
                //设置请求头
                put.setHeader("Content-Type", "application/json");
                put.setHeader("Authorization","Basic ZmY0YTZiZTQ3MWEyNDhiMjMyNTY5OGQzOjZmNjE1NmUzNDI1ODEyYmJlM2IzMDMzOA==");
                String body = "{\"new_password\": \"111111\"}";
                //设置请求体
                System.out.println(body);

                put.setEntity(new StringEntity(body));
                //获取返回信息
                System.out.println("I am going to explore!!");
                client.execute(put);
                System.out.println("AGAIN!  I am going to explore!!");
                client.close();
            } catch (Exception e) {
                System.out.println("接口请求失败"+e.getStackTrace());
            }
             *
             */
            return "1";
        } else {
            return "0";
        }
    }

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


    @Override
    public boolean changeUserInfo(JSONObject data) {
        User user = userDao.findByTel(data.getString("tel"));
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

    @Override
    public Iterable<User> findAll() { return userDao.findAll(); }
}
