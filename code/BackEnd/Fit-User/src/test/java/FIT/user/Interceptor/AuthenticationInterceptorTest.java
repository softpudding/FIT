package FIT.user.Interceptor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationInterceptorTest {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    Object object;

    @Autowired



}