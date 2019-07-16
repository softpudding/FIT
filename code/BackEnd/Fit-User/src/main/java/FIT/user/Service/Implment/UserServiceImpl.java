package FIT.user.Service.Implment;

import FIT.user.Dao.UserDao;
import FIT.user.Entity.User;
import FIT.user.Service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
        } else if (!user.isIsactive()) {
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
    public String changePwd(String tel, String pwd) {
        User test = findByTel(tel);
        if (test != null) {
            test.setPassword(pwd);
            userDao.save(test);
            return "1";
        } else {
            return "0";
        }
    }

    @Override
    public boolean logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("USERID") == null) {
            System.err.println("没有session啊！");
            throw new Exception("Throw Exception!");
        } else {
            session.setAttribute("USERID", null);
            System.out.println(session.getId());
            System.out.println(session.getAttribute("USERID"));
            return true;
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
        //String ss = "13262606827";

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

            // System.out.println(SubmitResult);

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
        } else {
            user.setAvatar(data.getString("avatar"));
            user.setNickName(data.getString("nickName"));
            user.setBirthday(data.getString("birthday"));       // 前端直接传yyyy-mm-dd类型的？ 前端设置好输入类型
            user.setGender(data.getInteger("gender"));
            user.setHeight(data.getInteger("height"));
            user.setWeight(data.getFloat("weight"));
            userDao.save(user);
            return true;
        }
    }

    @Override
    public Iterable<User> findAll() {
        return userDao.findAll();
    }


}
