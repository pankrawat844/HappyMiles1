package Metadata;

/**
 * Created by Admin on 1/27/2017.
 */

public class Dashboard_Metadata {
    String title,descrp;

    public Dashboard_Metadata(String title, String descrp) {
        this.title = title;
        this.descrp = descrp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }
}
