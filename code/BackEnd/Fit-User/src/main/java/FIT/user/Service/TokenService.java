package FIT.user.Service;

import FIT.user.Entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;


public interface TokenService {
    String getToken(User user);
}