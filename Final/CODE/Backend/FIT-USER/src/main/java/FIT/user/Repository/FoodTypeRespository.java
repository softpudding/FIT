package FIT.user.Repository;

import FIT.user.Entity.FoodTpye;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRespository extends JpaRepository<FoodTpye,Integer> {
    FoodTpye findByName(String name);
}
