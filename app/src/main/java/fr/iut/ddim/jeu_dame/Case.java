package fr.iut.ddim.jeu_dame;

public class Case {

    private Piece piece = null;
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
    Case(int row,int col){
        this.col = col;
        this.row = row;
    }
    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public boolean HasPiece(){
        return (piece == null);
    }
}
