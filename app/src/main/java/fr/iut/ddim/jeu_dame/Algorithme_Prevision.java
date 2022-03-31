package fr.iut.ddim.jeu_dame;

import android.util.Log;

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

    public PreviewAutorisedMouvementResult PreviewAutorisedMouvement(Piece piece,Case[][] plate)
    {


        HashMap<Case, Piece> piecesRemove = new HashMap<Case,Piece>();

        ArrayList<Case> lstDiago = new ArrayList<Case>();
        ArrayList<Case> lstDiagoSave = new ArrayList<Case>();

        lstDiago = GetDiagonnal(piece.getCase(),plate);
        lstDiagoSave = (ArrayList<Case>) lstDiago.clone();
        for(int i=0; i<lstDiago.size();i++)
        {
            if(lstDiago.get(i).HasPiece())
            {
                if(lstDiago.get(i).getPiece().getColor()==piece.getColor())
                    lstDiagoSave.remove(lstDiago.get(i).getPiece().getCase());
                else
                {
                    Case cDestination = lstDiago.get(i);

                    int rowDest = piece.getCase().getRow() - cDestination.getRow();
                    int colDest =  piece.getCase().getCol() - cDestination.getCol();
                    if(plate[rowDest][colDest].HasPiece())
                        lstDiagoSave.remove(i);
                    else
                    {
                        lstDiagoSave.remove(i);
                        piecesRemove.put(lstDiago.get(i),lstDiago.get(i).getPiece());
                        lstDiagoSave.add(plate[rowDest][colDest]);
                    }
                }
            }
        }
        return new PreviewAutorisedMouvementResult(piecesRemove,lstDiagoSave);
    }
    //TODO : revoir gestion coté crach cause index = -1
    public ArrayList<Case> GetDiagonnal(Case caseDepart,Case[][] lstCase){
        ArrayList<Case> lstCaseDiago = new ArrayList<Case>();
        int col = caseDepart.getCol();
        int row = caseDepart.getRow();
        if(row != 8 && col != 8)
            lstCaseDiago.add(lstCase[row+1][col+1]);
        if(row != 0 && col != 0)
            lstCaseDiago.add(lstCase[row-1][col-1]);
        if(row != 0 && col != 8)
            lstCaseDiago.add(lstCase[row-1][col+1]);
        if(row != 8 && col != 0)
            lstCaseDiago.add(lstCase[row+1][col-1]);
        return lstCaseDiago;
    }

    public Boolean PlayerCanEat(String colorTurn,Case [][]plate){

        ArrayList<Piece> lstPieces = new ArrayList<Piece>();
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<7;j++)
            {
                ArrayList<Case> lstDiago = new ArrayList<Case>();
                if(plate[i][j].HasPiece())
                    if(plate[i][j].getPiece().getColor() == colorTurn)
                        lstDiago = GetDiagonnal(plate[i][j].getPiece().getCase(),plate);
                for(int k=0; k<lstDiago.size();k++)
                {
                    if(lstDiago.get(k).HasPiece())
                    {
                        if(lstDiago.get(k).getPiece().getColor()!= plate[i][j].getPiece().getColor())
                        {
                            Case cDestination = lstDiago.get(k);

                            int rowDest = plate[i][j].getPiece().getCase().getRow() - cDestination.getRow();
                            int colDest =  plate[i][j].getPiece().getCase().getCol() - cDestination.getCol();
                            if(!plate[rowDest][colDest].HasPiece())
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
