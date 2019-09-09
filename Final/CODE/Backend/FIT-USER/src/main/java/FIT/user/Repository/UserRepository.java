package FIT.user.Repository;

import FIT.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByTel(String tel);

}
