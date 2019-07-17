package FIT.user.Service.Implment;


import FIT.user.Entity.User;
import FIT.user.Service.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String getToken(User user) {
        {
            String token="";
            token= JWT.create().withAudience(user.getTel())// 将 user tel 保存到 token 里面
                    .sign(Algorithm.HMAC256(user.getPassword()));// 以 password 作为 token 的密钥
            return token;
        }
    }
}
