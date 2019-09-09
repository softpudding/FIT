package FIT.user.Entity;

import javax.persistence.*;

@Entity
public class FoodTpye {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "calory")
    private Integer calory;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "carbohydrate")
    private Double carbohydrate;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public void setCarbohydrate(Double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbohydrate() {
        return carbohydrate;
    }

    public Double getFat() {
        return fat;
    }

    public Double getProtein() {
        return protein;
    }

    public Integer getCalory() {
        return calory;
    }

    public void setCalory(Integer calory) {
        this.calory = calory;
    }

}
