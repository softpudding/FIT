package SoftPudding.Entity;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tel")
    private String tel;

    @Column(name = "password")
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "isactive")
    private boolean isactive;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTel() { return tel; }

    public void setTel(String tel) { this.tel = tel; }

    public String getPassword() { return password; }

    public void setPassword(String password) {this.password = password; }

    public String getNickName() { return nickName; }

    public void setNickName(String nickName) { this.nickName = nickName; }

    public boolean getIsactive() { return isactive; }

    public void setIsactive(boolean isactive) { this.isactive = isactive; }
}
