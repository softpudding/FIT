package FIT.user.Service;

import FIT.user.Dao.UserDao;
import FIT.user.Entity.User;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService2 {

    @Autowired
    private UserDao userDao;

    public String  changePwd(String tel, String pwd) throws Exception{

        /**
         *
         * url:
         * https://api.im.jpush.cn/v1/users/{tel}/password
         *
         * 设置请求头
         *  put.setHeader("Content-Type", "application/json");
         *  put.setHeader("Authorization","Basic ZmY0YTZiZTQ3MWEyNDhiMjMyNTY5OGQzOjZmNjE1NmUzNDI1ODEyYmJlM2IzMDMzOA==");
         *
         * body:(json)
         *      {
         * 	"new_password":"000000"
         *      }
         */

        String url = "https://api.im.jpush.cn/v1/users/"+ tel + "/password";

        try {

            HttpClient client = new DefaultHttpClient();
            HttpPut put = new HttpPut(url);

            // 设置body   这里不是json格式哦
            HashMap<String,String> urlParameters = new HashMap<>();
            urlParameters.put("new_password",pwd);
            //put.setEntity(new UrlEncodedFormEntity((Iterable<? extends NameValuePair>) urlParameters));

            // try 1
            String payload = "{" +
                    "\"new_password\": \"adminadmin\"" +
                    "}";
            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_FORM_URLENCODED);
            put.setEntity(entity);
            // try 1 end

            // 设置头部
            put.setHeader("Content-Type", "application/json");
            put.setHeader("Authorization","Basic ZmY0YTZiZTQ3MWEyNDhiMjMyNTY5OGQzOjZmNjE1NmUzNDI1ODEyYmJlM2IzMDMzOA==");

            HttpResponse response = client.execute(put);
            System.out.println("\nSending 'PUT' request to URL : " + url);
            System.out.println("Post parameters : " + put.getEntity().getContent());
            System.out.println("YQC TEST urlParameters : " + urlParameters.toString());
            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result.toString());
            return result.toString();

        }

        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
}
