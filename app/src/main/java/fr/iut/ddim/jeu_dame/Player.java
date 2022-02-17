package fr.iut.ddim.jeu_dame;

public class Player {

    private int id;
    private String name;
    private int nbJouer;
    private int nbGagner;
    private int nbPerdu;

    public void Player (int id,String name,int nbJouer,int nbGagner,int nbPerdu){
        this.id         = id;
        this.name       = name;
        this.nbJouer    = nbJouer;
        this.nbGagner   = nbGagner;
        this.nbPerdu    = nbPerdu;
    }

    public int getId() {
        return id;
    }


    public int getNbJouer() {
        return nbJouer;
    }

    public void setNbJouer(int nbJouer) {
        this.nbJouer = nbJouer;
    }

    public int getNbGagner() {
        return nbGagner;
    }

    public void setNbGagner(int nbGagner) {
        this.nbGagner = nbGagner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbPerdu() {
        return nbPerdu;
    }

    public void setNbPerdu(int nbPerdu) {
        this.nbPerdu = nbPerdu;
    }
}
