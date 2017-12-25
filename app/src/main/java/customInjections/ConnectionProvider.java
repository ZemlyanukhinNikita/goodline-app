package customInjections;

import com.google.inject.throwingproviders.CheckedProvider;
import service.MyException;

import java.io.IOException;
import java.sql.SQLException;

public interface ConnectionProvider<T> extends CheckedProvider<T> {
    T get() throws MyException, IOException, SQLException;
}