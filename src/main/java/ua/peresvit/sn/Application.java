package ua.peresvit.sn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Handles favicon.ico requests assuring no <code>404 Not Found</code> error is returned.
     */
   /* @Controller
    static class FaviconController {
        @RequestMapping("favicon.ico")
        String favicon() {
            return "forward:/resources/frontend/image/logo-in-sidebar.png";
        }
    }*/




}