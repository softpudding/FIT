package FIT.user.Dao;

import FIT.user.Repository.UserRepository;
import FIT.user.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private UserRepository userRepository;

    public User findByTel(String tel) {
        return userRepository.findByTel(tel);
    }

    // asasd
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

}
