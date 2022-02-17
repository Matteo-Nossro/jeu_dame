package fr.iut.ddim.jeu_dame;

import java.util.ArrayList;
import java.util.HashMap;


public class Algorithme_Prevision {
    public void PlayerNeedToEat(){}

    class PreviewAutorisedMouvementResult {
        private HashMap<Case, Piece> piecesRemove;
        private ArrayList<Case> lstMouvement;

        public PreviewAutorisedMouvementResult(HashMap<Case, Piece> piecesRemove, ArrayList<Case> lstMouvement) {
            this.piecesRemove = piecesRemove;
            this.lstMouvement = lstMouvement;
        }

        public HashMap<Case, Piece> getPiecesRemove() {
            return piecesRemove;
        }

        public ArrayList<Case> getLstMouvement() {
            return lstMouvement;
        }
    }

    public PreviewAutorisedMouvementResult PreviewAutorisedMouvement(Piece piece)
    {
        Plate plate = Plate.getInstance();

        HashMap<Case, Piece> piecesRemove = new HashMap<Case,Piece>();

        ArrayList<Case> lstDiago = new ArrayList<Case>();
        ArrayList<Case> lstDiagoSave = new ArrayList<Case>();

        lstDiago = GetDiagonnal(piece.getCase(),plate.getPlate());
        lstDiagoSave = lstDiago;
        for(int i=0; i<lstDiago.size();i++)
        {
            if(lstDiago.get(i).HasPiece())
            {
                if(lstDiago.get(i).getPiece().getColor()==piece.getColor())
                    lstDiagoSave.remove(i);
                else
                {
                    Case cDestination = lstDiago.get(i);

                    int rowDest = piece.getCase().getRow() - cDestination.getRow();
                    int colDest =  piece.getCase().getCol() - cDestination.getCol();
                    if(plate.getPlate()[rowDest][colDest].HasPiece())
                        lstDiagoSave.remove(i);
                    else
                    {
                        lstDiagoSave.remove(i);
                        piecesRemove.put(lstDiago.get(i),lstDiago.get(i).getPiece());
                        lstDiagoSave.add(plate.getPlate()[rowDest][colDest]);
                    }
                }
            }
        }
        return new PreviewAutorisedMouvementResult(piecesRemove,lstDiagoSave);
    }

    public ArrayList<Case> GetDiagonnal(Case caseDepart,Case[][] lstCase){
        ArrayList<Case> lstCaseDiago = new ArrayList<Case>();
        int col = caseDepart.getCol();
        int row = caseDepart.getRow();
        if(lstCase[col+1][row+1] != null)
            lstCaseDiago.add(lstCase[col+1][row+1]);
        if(lstCase[col-1][row-1] != null)
            lstCaseDiago.add(lstCase[col-1][row-1]);
        if(lstCase[col-1][row+1] != null)
            lstCaseDiago.add(lstCase[col-1][row+1]);
        if(lstCase[col+1][row-1] != null)
            lstCaseDiago.add(lstCase[col+1][row-1]);
        return lstCaseDiago;
    }

    public Boolean PlayerCanEat(String colorTurn){
        Plate plate = Plate.getInstance();
        ArrayList<Piece> lstPieces = new ArrayList<Piece>();
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<7;j++)
            {
                ArrayList<Case> lstDiago = new ArrayList<Case>();
                if(plate.getPlate()[i][j].HasPiece())
                    if(plate.getPlate()[i][j].getPiece().getColor() == colorTurn)
                        lstDiago = GetDiagonnal(plate.getPlate()[i][j].getPiece().getCase(),plate.getPlate());
                for(int k=0; k<lstDiago.size();k++)
                {
                    if(lstDiago.get(k).HasPiece())
                    {
                        if(lstDiago.get(k).getPiece().getColor()!= plate.getPlate()[i][j].getPiece().getColor())
                        {
                            Case cDestination = lstDiago.get(k);

                            int rowDest = plate.getPlate()[i][j].getPiece().getCase().getRow() - cDestination.getRow();
                            int colDest =  plate.getPlate()[i][j].getPiece().getCase().getCol() - cDestination.getCol();
                            if(!plate.getPlate()[rowDest][colDest].HasPiece())
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
