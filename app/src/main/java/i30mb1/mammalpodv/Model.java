package i30mb1.mammalpodv;

public class Model {

    public Model(String name) {
        this.name = name;
    }

    String name;
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
