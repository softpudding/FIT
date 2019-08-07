package FIT.Administer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class NewsDao {
    @Autowired
    private NewsRepo newsRepo;

    public void save(News news) {
        newsRepo.saveAndFlush(news);
    }

    public Iterable<News> findAllByActive(Integer act) {return  newsRepo.findAllByActive(act);}

    //public Iterable<User> findAllByActive(Integer act) { return userRepository.findAllByActive(act);}
}
