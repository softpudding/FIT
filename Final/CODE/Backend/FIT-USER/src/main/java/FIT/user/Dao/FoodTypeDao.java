package FIT.user.Dao;


import FIT.user.Entity.FoodTpye;
import FIT.user.Repository.FoodTypeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FoodTypeDao {

    @Autowired
    private FoodTypeRespository foodTypeRespository;


    public FoodTpye findByName(String name) {
        return foodTypeRespository.findByName(name);
    }
}
