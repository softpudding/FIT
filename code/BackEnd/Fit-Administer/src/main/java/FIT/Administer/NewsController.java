package FIT.Administer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(path = "/news")
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
    @PostMapping(path = "/getOneNews")
    public @ResponseBody
    News getOneNews(Integer id) {
        return newsService.findById(id);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/showNews")
    public @ResponseBody
    boolean showNews(@RequestBody Integer id) {
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
    @PostMapping(path = "/hideNews")
    public @ResponseBody boolean hideNews(@RequestBody Integer id) {
        News news = newsService.findById(id);
        System.out.println("IDIDIID");
        System.out.println("IDIDIID" + id);
        if (news == null) {
            return false;
        }
        else {
            newsService.hide(news);
            return true;
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/showActiveNews")
    public @ResponseBody Iterable<News> getNews() {

        Iterable<News> iterable = newsService.findAllByActive(1);
        System.out.println("hello All News");
        return iterable;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(path = "/AddNews")
    public @ResponseBody boolean AddNews(@RequestBody JSONObject data) {



        News n = new News();
        n.setTittle(data.getString("tittle"));
        n.setNews(data.getString("news"));
        n.setActive(1);

        // get time stamp
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();// 获取当前时间
        String time = sdf.format(date.getTime());
        n.setTime_stamp(time);
        
        newsService.save(n);
        return true;
    }

}
