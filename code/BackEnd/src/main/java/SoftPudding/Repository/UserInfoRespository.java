package SoftPudding.Repository;

import SoftPudding.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRespository extends JpaRepository<UserInfo,Integer> {

    UserInfo findByUserId(Integer userId);
}
