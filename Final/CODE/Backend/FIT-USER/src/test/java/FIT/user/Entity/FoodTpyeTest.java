package FIT.user.Entity;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodTpyeTest {

    @Test
    public void testFoodType() {
        System.out.println("Test for FoodTypeEntity");
        FoodTpye foodTpye = new FoodTpye();
        foodTpye.setCalory(123);
        foodTpye.setCarbohydrate(123.123);
        foodTpye.setFat(123.123);
        foodTpye.setId(12);
        foodTpye.setName("testName");
        foodTpye.setProtein(123.123);
        assertEquals(123, foodTpye.getCalory().intValue());
        assertEquals(123.123,foodTpye.getFat(),0.001);
        assertEquals(123.123,foodTpye.getCarbohydrate(),0.001);
        assertEquals(123.123,foodTpye.getProtein(),0.001);
        assertEquals(12,foodTpye.getId().intValue());
        assertEquals("testName",foodTpye.getName());
    }
}