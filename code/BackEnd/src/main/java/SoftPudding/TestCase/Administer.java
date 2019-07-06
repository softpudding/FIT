package SoftPudding.TestCase;
import javax.persistence.*;

@Entity
public class Administer {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
        @Column
        private String password;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }


        public String getPwd() {
            return password;
        }

        public void setPwd(String pwd) {
            this.password = pwd;
        }



}
