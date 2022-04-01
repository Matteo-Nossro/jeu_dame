package fr.iut.ddim.jeu_dame;

import android.widget.ImageView;

public class Case {

    private Piece piece = null;
    private ImageView imageCase;
    public ImageView getImageCase(){ return  imageCase;}
    public  void setImageCase (ImageView imageCase){ this.imageCase = imageCase;}

    private ImageView lueurcase;
    public ImageView getlueurcase(){ return  lueurcase;}
    public  void setlueurcase(ImageView lueurcase){ this.lueurcase = lueurcase;}

    private int col ;
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    private int row ;
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    Case(int row,int col,ImageView imageCase){
        this.col = col;
        this.row = row;
        this.imageCase = imageCase;
        this.lueurcase = null;
    }
    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;

    }
    public boolean hasPiece(){
        return (piece != null);
    }
}
