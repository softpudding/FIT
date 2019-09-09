package FIT.user.Entity;

import javax.persistence.*;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tel")
    private String tel;

    @Column(name = "time_stamp")
    private String timeStamp;

    @Column(name = "cal")
    private double cal;

    @Column(name = "food")
    private String food;

    @Column(name = "weight")
    private double weight;

    @Column(name = "fat")
    private double fat;

    @Column(name = "protein")
    private double protein;

    @Column(name = "carbohydrate")
    private double carbohydrate;

    @Column(name = "if_is_vegetable")
    private boolean if_is_vegetable;

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    public Integer getId() {
        return id;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public double getFat() {
        return fat;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public boolean isIf_is_vegetable() {
        return if_is_vegetable;
    }

    public void setIf_is_vegetable(boolean if_is_vegetable) {
        this.if_is_vegetable = if_is_vegetable;
    }

}
