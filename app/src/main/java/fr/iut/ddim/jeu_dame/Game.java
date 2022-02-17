package fr.iut.ddim.jeu_dame;

import android.widget.GridView;

import java.util.ArrayList;
public class Game {
    private Game(){

    }
    private static Game instance;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
    private Plate plate;
    private Piece selectedPiece;
    private Algorithme_Prevision algorithme = new Algorithme_Prevision();
    private Algorithme_Prevision.PreviewAutorisedMouvementResult previewMouvement;
    private String colorTurn;
    private GridView gridView;
    public GridView GetgridView() {
        return gridView;
    }
    public void setgridView(GridView gridView){
        this.gridView = gridView;
    }

    public void StartGame(Player player1, Player player2,GridView gridView){
        this.gridView = gridView;

        plate = Plate.getInstance();
        colorTurn = "noir";
    }

    public void OnClickPiece(Piece piece){//sender /event
        if(piece.getColor()==colorTurn)
            previewMouvement = algorithme.PreviewAutorisedMouvement(piece);
        this.selectedPiece = piece;

    }

    public void OnClickCase(Case caseVisee)
    {
        if(selectedPiece != null){
            if(previewMouvement.getLstMouvement().contains(caseVisee)) // On a les cases ou l'on peut aller
                if(previewMouvement.getPiecesRemove().containsKey(caseVisee)){ // la case ï¿½limine une piece
                    Piece pieceDead = previewMouvement.getPiecesRemove().get(caseVisee);
                    pieceDead.getCase().setPiece(null);
                    pieceDead.setCase(null);
                }
            selectedPiece.MovePiece(selectedPiece.getCase(), caseVisee);
            EndTurn();

            // On kill bouge la piece et on kill au besoin
        }

    }

    public void EndTurn(){
        if(!algorithme.PlayerCanEat(colorTurn))
        {
            if(colorTurn == "noir")
                colorTurn = "blanc";
            else if(colorTurn == "blanc")
                colorTurn = "noir";
        }

    }
}
