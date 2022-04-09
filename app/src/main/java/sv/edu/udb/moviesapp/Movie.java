package sv.edu.udb.moviesapp;

public class Movie {

    String key;
    private String title;
    private String description;
    private String premierYear;
    private String score;
    private String imagen;

    public  Movie(){

    }

    public Movie(String title, String description, String premierYear, String score, String imagen) {
        this.title = title;
        this.description = description;
        this.premierYear = premierYear;
        this.score = score;
        this.imagen = imagen;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPremierYear() {
        return premierYear;
    }

    public void setPremierYear(String premierYear) {
        this.premierYear = premierYear;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getImagen(){return imagen;}

    public void setImagen(String imagen){this.imagen=imagen;}
}
