package fr.iut.ddim.jeu_dame;

public class Player {
    private int id;
    private String nom;
    private int nbParties;
    private int nbVictoires;
    private int nbDefaites;

    public void Player (int id, String name, int nbJouer, int nbGagner, int nbPerdu) {
        this.id = id;
        this.nom = name;
        this.nbParties = nbJouer;
        this.nbVictoires = nbGagner;
        this.nbDefaites = nbPerdu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbParties() {
        return nbParties;
    }

    public void setNbParties(int nbParties) {
        this.nbParties = nbParties;
    }

    public int getNbVictoires() {
        return nbVictoires;
    }

    public void setNbVictoires(int nbVictoires) {
        this.nbVictoires = nbVictoires;
    }

    public int getNbDefaites() {
        return nbDefaites;
    }

    public void setNbDefaites(int nbDefaites) {
        this.nbDefaites = nbDefaites;
    }
}