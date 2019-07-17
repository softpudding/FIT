package SoftPudding.Dao;

import SoftPudding.Entity.User;
import SoftPudding.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private UserRepository userRepository;

    public User findByTel(String tel) {
        return userRepository.findByTel(tel);
    }
    //asasd
    public void save(User user) { userRepository.saveAndFlush(user); }
}
