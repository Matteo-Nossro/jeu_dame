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

    public PreviewAutorisedMouvementResult previewAutorisedMouvement(Piece piece, Case[][] plate, boolean mustEatStart)
    {


        HashMap<Case, Piece> piecesRemove = new HashMap<Case,Piece>();

        ArrayList<Case> lstDiago = new ArrayList<Case>();
        ArrayList<Case> lstDiagoSave = new ArrayList<Case>();

        lstDiago = getDiagonnal(piece.getCase(),plate);
        lstDiagoSave = (ArrayList<Case>) lstDiago.clone();
        for(int i=0; i<lstDiago.size();i++)
        {
            if(lstDiago.get(i).hasPiece())
            {
                if(lstDiago.get(i).getPiece().getColor()==piece.getColor())
                    lstDiagoSave.remove(lstDiago.get(i).getPiece().getCase());
                else
                {
                    Case cDestination = lstDiago.get(i);

                    int rowDest;
                    rowDest = cDestination.getRow()+( cDestination.getRow() - piece.getCase().getRow() );

                    int colDest;
                    colDest = cDestination.getCol()+(cDestination.getCol() - piece.getCase().getCol() );
                    if ( rowDest != -1 && colDest != -1 && rowDest !=8 && colDest !=8){

                        if(plate[rowDest][colDest].hasPiece())
                            lstDiagoSave.remove(cDestination);
                        else
                        {
                            lstDiagoSave.remove(cDestination);
                            piecesRemove.put(plate[rowDest][colDest],lstDiago.get(i).getPiece());
                            lstDiagoSave.add(plate[rowDest][colDest]);
                        }
                    }
                    else
                        lstDiagoSave.remove(cDestination);

                }
            }
        }
        ArrayList<Case> lstRemove = new ArrayList<Case>();

        // Gestion obligation manger debut
        if(mustEatStart)
        {
            ArrayList<Case> lstRemove1 = new ArrayList<Case>();
            int movingBack1;
            int movingBack2;
            movingBack1 = piece.getCase().getRow()-1;
            movingBack2 = piece.getCase().getRow()+1;

            for(int i = 0; i < lstDiagoSave.size() ;i++)
                if (movingBack1 == lstDiagoSave.get(i).getRow() ||movingBack2 == lstDiagoSave.get(i).getRow() )
                    lstRemove.add(lstDiagoSave.get(i));

        }
        else{
            //#region gestion mouvement arrière
            int movingBack;
            if (piece.getColor() == "noir")
                movingBack = piece.getCase().getRow()-1;
            else
                movingBack = piece.getCase().getRow()+1;

            for(int i = 0; i < lstDiagoSave.size() ;i++)
                if (movingBack == lstDiagoSave.get(i).getRow())
                    lstRemove.add(lstDiagoSave.get(i));
            //#endregion
        }
        for(int i = 0; i < lstRemove.size() ;i++)
            lstDiagoSave.remove(lstRemove.get(i));
        ///////////////////

            return new PreviewAutorisedMouvementResult(piecesRemove,lstDiagoSave);
    }
    public ArrayList<Case> getDiagonnal(Case caseDepart, Case[][] lstCase){
        ArrayList<Case> lstCaseDiago = new ArrayList<Case>();
        int col = caseDepart.getCol();
        int row = caseDepart.getRow();
        if(row != 7 && col != 7)
            lstCaseDiago.add(lstCase[row+1][col+1]);
        if(row != 0 && col != 0)
            lstCaseDiago.add(lstCase[row-1][col-1]);
        if(row != 0 && col != 7)
            lstCaseDiago.add(lstCase[row-1][col+1]);
        if(row != 7 && col != 0)
            lstCaseDiago.add(lstCase[row+1][col-1]);
        return lstCaseDiago;
    }

    public Boolean playerCanEat(String colorTurn, Case [][]plate){

        ArrayList<Piece> lstPieces = new ArrayList<Piece>();
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<7;j++)
            {
                ArrayList<Case> lstDiago = new ArrayList<Case>();
                if(plate[i][j].hasPiece())
                    if(plate[i][j].getPiece().getColor() == colorTurn)
                        lstDiago = getDiagonnal(plate[i][j].getPiece().getCase(),plate);
                for(int k=0; k<lstDiago.size();k++)
                {
                    if(lstDiago.get(k).hasPiece())
                    {
                        if(lstDiago.get(k).getPiece().getColor()!= plate[i][j].getPiece().getColor())
                        {
                            Case cDestination = lstDiago.get(k);

                            int rowDest = cDestination.getRow() + cDestination.getRow() -plate[i][j].getPiece().getCase().getRow() ;
                            int colDest = cDestination.getCol() + cDestination.getCol()- plate[i][j].getPiece().getCase().getCol()  ;
                            if ( rowDest != -1 && colDest != -1 && rowDest !=8 && colDest !=8)
                                if(!plate[rowDest][colDest].hasPiece())
                                    return true;

                        }
                    }
                }
            }
        }
        return false;
    }
}
