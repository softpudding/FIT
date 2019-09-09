package FIT.Administer;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class NewsService {

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private NewsDao newsDao;

    public void save(News news) {
        newsRepo.saveAndFlush(news);
    }

    public Iterable<News> findAll() {
        return newsRepo.findAll();
    }

    public News findById(Integer id) {
        return newsRepo.getOne(id);
    }

    public Iterable<News> findAllByActive(Integer a) {
        System.out.println(a);
        Iterable<News> is =  newsDao.findAllByActive(a);
        for(News news:is) {
            System.out.println("one News");
        }
        return is;
    }

    public void show(News news) {
        news.setActive(1);
        newsDao.save(news);
    }

    public void hide(News news) {
        news.setActive(0);
        newsDao.save(news);
    }
}
