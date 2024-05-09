package se.saltcode.saltfeed.domain.hero.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.transaction.NotSupportedException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hero {

    private String url;
    private String name;

    public Hero(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public int getId() throws NotSupportedException {
        throw new NotSupportedException("We need a way to get the id out from the url");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
