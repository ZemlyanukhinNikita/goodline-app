package customInjections.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provider;

public class SerializeJsonProvider implements Provider<Gson> {
    @Override
    public Gson get() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
}
