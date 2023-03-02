//package com.meawallet;
//
//import com.meawallet.usercrud.ui.UserMenu;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//public class UserCrudApplication {
//
//    public static void main(String[] args) {
//        var applicationContext = new AnnotationConfigApplicationContext("com.meawallet.usercrud", "org.springframework.data");
//        var userMenu = applicationContext.getBean(UserMenu.class);
//        userMenu.startMenu();
//    }
//
//}

package com.meawallet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.meawallet.",
        exclude = HibernateJpaAutoConfiguration.class)
public class UserCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCrudApplication.class);
    }

}
