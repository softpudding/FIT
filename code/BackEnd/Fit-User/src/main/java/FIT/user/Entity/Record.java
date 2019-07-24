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

    @Column(name = "nutrition1")
    private String nutrition1;

    @Column(name = "nutrition2")
    private String nutrition2;

    @Column(name = "nutrition3")
    private String nutrition3;

    @Column(name = "nutrition4")
    private String nutrition4;

    @Column(name = "nutrition5")
    private String nutrition6;

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

    public Integer getId() {
        return id;
    }

    public String getNutrition1() {
        return nutrition1;
    }

    public String getNutrition2() {
        return nutrition2;
    }

    public String getNutrition3() {
        return nutrition3;
    }

    public void setNutrition1(String nutrition1) {
        this.nutrition1 = nutrition1;
    }

    public void setNutrition2(String nutrition2) {
        this.nutrition2 = nutrition2;
    }

    public String getNutrition4() {
        return nutrition4;
    }

    public void setNutrition4(String nutrition4) {
        this.nutrition4 = nutrition4;
    }

    public void setNutrition3(String nutrition3) {
        this.nutrition3 = nutrition3;
    }

    public void setNutrition6(String nutrition6) {
        this.nutrition6 = nutrition6;
    }

    public String getNutrition6() {
        return nutrition6;
    }
}
