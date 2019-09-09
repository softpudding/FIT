package FIT.user.Entity;

import javax.persistence.*;

@Entity
public class Recognition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tel")
    private String tel;

    @Column(name = "time_stamp")
    private String timeStamp;

    @Column(name = "object_type")
    private Integer objectType;

    @Column(name = "food1")
    private String foodK1;            //  如果是一次识别铁盘类型的食物，是不是会需要多个列去记录食物？

    @Column(name = "food2")
    private String foodK2;

    @Column(name = "food3")
    private String foodK3;

    @Column(name = "food4")
    private String foodK4;

    @Column(name = "food5")
    private String foodK5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public String getFoodK1() {
        return foodK1;
    }

    public void setFoodK1(String foodK1) {
        this.foodK1 = foodK1;
    }

    public String getFoodK2() {
        return foodK2;
    }

    public String getFoodK3() {
        return foodK3;
    }

    public String getFoodK4() {
        return foodK4;
    }

    public void setFoodK2(String foodK2) {
        this.foodK2 = foodK2;
    }

    public String getFoodK5() {
        return foodK5;
    }

    public void setFoodK3(String foodK3) {
        this.foodK3 = foodK3;
    }

    public void setFoodK5(String foodK5) {
        this.foodK5 = foodK5;
    }

    public void setFoodK4(String foodK4) {
        this.foodK4 = foodK4;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
