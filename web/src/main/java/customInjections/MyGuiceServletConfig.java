package customInjections;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import servlets.ActivityServlet;
import servlets.AuthorityServlet;
import servlets.EchoServlet;
import servlets.UserServlet;

import javax.inject.Singleton;

public class MyGuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/echo/get").with(EchoServlet.class);
                serve("/echo/post").with(EchoServlet.class);
                serve("/ajax/user").with(UserServlet.class);
                serve("/ajax/authority").with(ActivityServlet.class);
                serve("/ajax/activity").with(AuthorityServlet.class);
                bind(EchoServlet.class).in(Singleton.class);
                bind(UserServlet.class).in(Singleton.class);
                bind(ActivityServlet.class).in(Singleton.class);
                bind(AuthorityServlet.class).in(Singleton.class);
                bindListener(Matchers.any(), new Log4JTypeListener());
            }
        });
    }
}
