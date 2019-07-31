package FIT.Administer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NewsController {

    @Autowired
    NewsService newsService;


    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/getAllNews")
    public @ResponseBody
    Iterable<News> getAllNews() {
        return newsService.findAll();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/getAllNews")
    public @ResponseBody
    String getOneNews(Integer id) {
        return newsService.findById(id).getNews();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/showNews")
    public @ResponseBody
    boolean showNews(Integer id) {
        News news = newsService.findById(id);
        if (news == null) {
            return false;
        }
        else {
            newsService.show(news);
            return true;
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/showNews")
    public @ResponseBody boolean  hideNews(Integer id) {
        News news = newsService.findById(id);
        if (news == null) {
            return false;
        }
        else {
            newsService.hide(news);
            return true;
        }
    }
}
