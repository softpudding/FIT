/*
package FIT.user.Service;

import FIT.user.Dao.UserDao;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


        String url = "https://api.im.jpush.cn/v1/users/"+ tel + "/password";

        try {

            //ttpClient client = new DefaultHttpClient();
            HttpClient client = HttpClientBuilder.create().build();
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
            System.out.println("YQC TEST payload : " + payload);
            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            return "succeed";
        }

        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
*/