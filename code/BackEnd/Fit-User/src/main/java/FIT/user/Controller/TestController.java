package FIT.user.Controller;

import FIT.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;
// import CN DOTA!

@CrossOrigin(origins = "*", maxAge = 0)
@Controller
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    private UserService userService;
    @CrossOrigin(origins = "*", maxAge = 0)
    @PostMapping(path = "/cookie")
    public @ResponseBody
    void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        //设置浏览器以UTF-8编码进行接收,解决中文乱码问题
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //获取浏览器访问访问服务器时传递过来的cookie数组
        Cookie[] cookies = request.getCookies();
        //如果用户是第一次访问，那么得到的cookies将是null
        if (cookies != null) {
            out.write("您上次访问的时间是：");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("lastAccessTime")) {
                    Long lastAccessTime = Long.parseLong(cookie.getValue());
                    Date date = new Date(lastAccessTime);
                    out.write(date.toLocaleString());
                }
            }
        } else {
            out.write("这是您第一次访问本站！");
        }

        //用户访问过之后重新设置用户的访问时间，存储到cookie中，然后发送到客户端浏览器
        Cookie cookie = new Cookie("lastAccessTime", System.currentTimeMillis() + "");//创建一个cookie，cookie的名字是lastAccessTime
        //将cookie对象添加到response对象中，这样服务器在输出response对象中的内容时就会把cookie也输出到客户端浏览器
        response.addCookie(cookie);
    }

    @CrossOrigin(origins = "*", maxAge = 0)
    @GetMapping(path = "/cookiepost")
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @CrossOrigin(origins = "*", maxAge = 0)
    @GetMapping(path = "/session")
    public void doGetSession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        session.setAttribute("demo", "EHome");

        String sessionID = session.getId();

        if (session.isNew()) {
            response.getWriter().println("SessionID 是: " + sessionID);
        } else {
            response.getWriter().println(("already Have SessionID: ") + sessionID);
        }

    }
}
