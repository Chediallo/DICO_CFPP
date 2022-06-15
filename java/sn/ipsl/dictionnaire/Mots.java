package sn.ipsl.dictionnaire;

public class Mots {
    private String Mot;
    private String Type;
    private String Genre;
    private String Exemple;
    private String Description;
    private String Synonyme;
    private String Antonyme;

    public Mots() {
    }

    public Mots(String mot, String type, String genre, String exemple, String description, String synonyme, String antonyme) {
        Mot = mot;
        Type = type;
        Genre = genre;
        Exemple = exemple;
        Description = description;
        Synonyme = synonyme;
        Antonyme = antonyme;
    }

    public String getMot() {
        return Mot;
    }

    public void setMot(String mot) {
        Mot = mot;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getExemple() {
        return Exemple;
    }

    public void setExemple(String exemple) {
        Exemple = exemple;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSynonyme() {
        return Synonyme;
    }

    public void setSynonyme(String synonyme) {
        Synonyme = synonyme;
    }

    public String getAntonyme() {
        return Antonyme;
    }

    public void setAntonyme(String antonyme) {
        Antonyme = antonyme;
    }
}
