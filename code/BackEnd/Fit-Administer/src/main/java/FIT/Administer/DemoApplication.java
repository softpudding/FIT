package FIT.Administer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"FIT.user","FIT.Administer"})
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        System.out.println("MAIN启动了");
        SpringApplication.run(DemoApplication.class, args);
    }

}