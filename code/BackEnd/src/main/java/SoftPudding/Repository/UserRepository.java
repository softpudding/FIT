package SoftPudding.Repository;

import SoftPudding.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByTel(String tel);

}
