package SoftPudding.Dao;

import SoftPudding.Entity.User;
import SoftPudding.Entity.UserInfo;
import SoftPudding.Repository.UserInfoRespository;
import SoftPudding.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRespository userInfoRespository;

    public User findByTel(String tel) {
        return userRepository.findByTel(tel);
    }

    public void save(User user) { userRepository.saveAndFlush(user); }

    public void saveUserInfo(UserInfo userInfo) { userInfoRespository.save(userInfo); }
}
