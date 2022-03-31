package fr.iut.ddim.jeu_dame;

import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Piece {

    private String color;
    public String getColor() {
        return color;
    }
    public void setColor(String color){this.color =  color;    }

    private Case caseProprio;
    public Case getCase() {
        return caseProprio;
    }
    public void setCase(Case caseP){this.caseProprio = caseP;}

    private ImageView imagePion;
    public ImageView getImagePion(){ return  imagePion;}
    public  void setImagePion (ImageView imagePion){ this.imagePion = imagePion;}

    Piece(Case caseP,String color,ImageView imagePion){
        this.color = color;
        this.caseProprio = caseP;
        this.imagePion = imagePion;
    }

    public void MovePiece(Case caseDepart,Case caseArrive){

        Piece p = caseDepart.getPiece();
        RelativeLayout parentDepart = (RelativeLayout)p.getImagePion().getParent();
        parentDepart.removeView(p.getImagePion());

        RelativeLayout parentArriver = (RelativeLayout) caseArrive.getImageCase().getParent();
        parentArriver.addView(imagePion);


        caseDepart.setPiece(null);
        caseArrive.setPiece(this);
        this.setCase(caseArrive);

    }
}
