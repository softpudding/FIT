package FIT.Administer;


import javax.persistence.*;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "time_stamp")
    private String time_stamp;

    @Column(name = "tittle")
    private String tittle;

    @Column(name = "news")
    private String news;

    @Column(name = "active")
    private Integer active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActive() {
        return active;
    }

    public String getNews() {
        return news;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public void setActive(Integer if_active) {
        this.active = if_active;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getTittle() {
        return tittle;
    }

}
