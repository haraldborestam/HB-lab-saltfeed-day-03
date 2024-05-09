package se.saltcode.saltfeed.domain.hero.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.transaction.NotSupportedException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hero {

    // Fields
    private String url;
    private String name;

    // Constructor
    public Hero(String name, String url) {
        this.name = name;
        this.url = url;
        System.out.println("\n\nHero created! \nName: " + this.name + "\nUrl: " + this.url + "\n\n");

    }

    // Den här anropas aldrig av oss. MEN den anropas automatiskt av Spring.
    // Anledningen är att den har 'get' i namnet.
    // Default läget är faktiskt att Spring skickar den här variabeln till klienten automatiskt.
    // Anledningen är för att Spring använder classens Getters för att bygga ihop sin respons.
    public int getId() {
        String urlString = url;
        String[] urlArray = url.split("/");
        String idString = urlArray[urlArray.length - 1];
        int id = -1;
        try {
            id = Integer.parseInt(idString);
        } catch (Exception e) {
            System.out.println("\n\n WARNING WARNING WARNING WARNING \n URL NOT PARSED \n WARNING WARNING WARNING WARNING \n\n");
        }
        return id;
    }

    // Getter metod
    public String getUrl() {
        return url;
    }
    // Setter metod
    public void setUrl(String url) {
        this.url = url;
    }
    // Getter metod
    public String getName() {
        return name;
    }
    // Setter metod
    public void setName(String name) {
        this.name = name;
    }
}
