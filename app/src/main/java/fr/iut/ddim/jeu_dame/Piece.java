package fr.iut.ddim.jeu_dame;

public class Piece {
    private String color;
    public String getColor() {
        return color;
    }
    public void setColor(String color){
        this.color =  color;
    }
    private Case caseProprio;
    public Case getCase() {
        return caseProprio;
    }
    public void setCase(Case caseP){
        this.caseProprio = caseP;
    }

    Piece(Case caseP,String color){
        this.color = color;
        this.caseProprio = caseP;
    }

    public void MovePiece(Case caseDepart,Case caseArrive){
        caseDepart.setPiece(null);
        caseArrive.setPiece(this);
        this.setCase(caseArrive);
    }
}
