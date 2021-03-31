package cz.muni.fi.pa165;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(App.class.getPackageName());
        var bean = ctx.getBean(App.class);

    }
}

