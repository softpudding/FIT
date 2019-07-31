package FIT.Administer;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class NewsService {

    @Autowired
    private NewsRepo newsRepo;

    public void save(News news) {
        newsRepo.saveAndFlush(news);
    }

    public Iterable<News> findAll() {
        return newsRepo.findAll();
    }

    public News findById(Integer id) {
        return newsRepo.getOne(id);
    }

    public void show(News news) {
        news.setIf_active(1);
        newsRepo.saveAndFlush(news);
    }

    public void hide(News news) {
        news.setIf_active(0);
        newsRepo.saveAndFlush(news);
    }
}
