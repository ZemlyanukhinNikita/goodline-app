/**
 * Created by Nikita Zemlyanukhin on 12.10.2017.
 */
public class Resources {
    private Long id;
    private String path;

    public Resources(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
