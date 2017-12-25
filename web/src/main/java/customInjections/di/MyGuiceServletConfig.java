package customInjections.di;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.throwingproviders.ThrowingProviderBinder;
import customInjections.ConnectionProvider;
import customInjections.InjectConnection;
import customInjections.logger.Log4JTypeListener;
import service.DbConnectionService;
import servlets.ActivityServlet;
import servlets.AuthorityServlet;
import servlets.EchoServlet;
import servlets.UserServlet;

import javax.inject.Singleton;
import java.sql.Connection;

public class MyGuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/echo/get").with(EchoServlet.class);
                serve("/echo/post").with(EchoServlet.class);
                serve("/ajax/user").with(UserServlet.class);
                serve("/ajax/authority").with(AuthorityServlet.class);
                serve("/ajax/activity").with(ActivityServlet.class);
                bind(EchoServlet.class).in(Singleton.class);
                bind(UserServlet.class).in(Singleton.class);
                bind(AuthorityServlet.class).in(Singleton.class);
                bind(ActivityServlet.class).in(Singleton.class);
                bindListener(Matchers.any(), new Log4JTypeListener());
                bind(Gson.class).toProvider(SerializeJsonProvider.class).in(Singleton.class);
                ThrowingProviderBinder.create(binder())
                        .bind(ConnectionProvider.class, Connection.class)
                        .annotatedWith(InjectConnection.class)
                        .to(DbConnectionService.class)
                        .in(com.google.inject.Singleton.class);
            }
        });
    }


}
