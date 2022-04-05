package fr.iut.ddim.jeu_dame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Dame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dame);

        this.gridLayout = (GridLayout)findViewById(R.id.plate);
        ImageView v = new ImageView(this); // création de la case visuel
        v.setImageDrawable(getDrawable(R.drawable.noir_2mdpi));
        this.constructPlateInitial(8,8);
        this.startGame();
    }

    private GridLayout gridLayout;
    private static Case[][] plateBack = new Case[8][8];
    private Piece selectedPiece;
    private Algorithme_Prevision algorithme = new Algorithme_Prevision();
    private Algorithme_Prevision.PreviewAutorisedMouvementResult previewMouvement;
    private String colorTurn;
    private boolean mustEat = false;
    private boolean mustEatStart =false;

    public Case[][] getPlateBack() {
        return plateBack;
    }

    public void setPlateBack(Case[][] value){
        this.plateBack=value;
    }

    //plateau
    public void constructPlateInitial(int col, int row){

        gridLayout.setColumnCount(col);
        gridLayout.setRowCount(row);
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++){

                int reverse = 1;
                if(i%2 == 1)
                    reverse = 0;

                RelativeLayout caseLayout = new RelativeLayout(this);
                caseLayout.setBackgroundColor(0xFFFF0000);

                GridLayout.LayoutParams param =new GridLayout.LayoutParams();
                param.height = 0;
                param.width = 0;
                param.rightMargin = 0;
                param.topMargin = 0;
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(j, 1, 1);
                param.rowSpec = GridLayout.spec(i, 1, 1);
                caseLayout.setLayoutParams(param);

                gridLayout.addView(caseLayout);

                ImageView imgCase = new ImageView(this); // création de la case visuel
                imgCase.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                ));
                imgCase.setScaleType(ImageView.ScaleType.CENTER_CROP);
                caseLayout.addView(imgCase);

                if(j%2==reverse)
                    imgCase.setImageDrawable(getDrawable(R.drawable.noir_2mdpi));
                else
                    imgCase.setImageDrawable(getDrawable(R.drawable.blanc_5ldpi));

                Case c = new Case(i,j,imgCase);
                plateBack[i][j] =c;

                imgCase.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        onClickCase(c);
                    }
                });

                if ((i > 2 && i < 5) || j%2!=reverse)
                    continue;

                int drawable = i <= 2 ? R.drawable.pion_noir : R.drawable.pion_blanc;
                String color = i<=2 ? "noir": "blanc";
                ImageView imgPion = new ImageView(this); // création de la case visuel
                imgPion.setImageDrawable(getDrawable(drawable));
                imgPion.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                imgPion.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

                caseLayout.addView(imgPion);

                Piece p = new Piece(c,color,imgPion);


                imgPion.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        onClickPiece(p);
                    }
                });

                c.setPiece(p);
            }
        }

    }

    public void startGame(){
        colorTurn = "noir";
    }

    public void onClickPiece(Piece piece){//sender /event
        Log.i("color",""+piece.getColor());
        if (!mustEat) {
            if (piece.getColor() == colorTurn) {
                previewMouvement = algorithme.previewAutorisedMouvement(piece, plateBack,mustEatStart);
                resetDisplayMovement();
                showDisplayMovement();

                this.selectedPiece = piece;
            }
        }
    }

    public void resetDisplayMovement(){
        for (int i = 0; i< gridLayout.getChildCount();i++){
            RelativeLayout enfantGrid = (RelativeLayout)gridLayout.getChildAt(i);
            ArrayList<View> lstRemoveLueur = new ArrayList<View>();
            for (int j = 0 ; j < enfantGrid.getChildCount();j++){
                View enfantLayout = enfantGrid.getChildAt(j);
                if (enfantLayout.getTag()!= null && enfantLayout.getTag().equals("lueur"))
                    lstRemoveLueur.add(enfantLayout);
            }
            for (int k = 0;k< lstRemoveLueur.size();k++)
                enfantGrid.removeView(lstRemoveLueur.get(k));
        }
    }
    public void showDisplayMovement(){
        for (int i = 0; i < previewMouvement.getLstMouvement().size();i++){
            RelativeLayout parentArriver = (RelativeLayout) previewMouvement.getLstMouvement().get(i).getImageCase().getParent();

            ImageView lueurCase = new ImageView(this); // création de la case visuel
            lueurCase.setImageDrawable(getDrawable(R.drawable.lueurcase));
            lueurCase.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            lueurCase.setTag("lueur");


            lueurCase.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

            parentArriver.addView(lueurCase);

        }
    }
    public void onClickCase(Case caseVisee)
    {

        if(selectedPiece != null){
            if(previewMouvement.getLstMouvement().contains(caseVisee)) { // On a les cases ou l'on peut aller
                selectedPiece.movePiece(selectedPiece.getCase(), caseVisee);
                if (previewMouvement.getPiecesRemove().containsKey(caseVisee)) { // la case ï¿½limine une piece
                    Piece pieceDead = previewMouvement.getPiecesRemove().get(caseVisee);
                    pieceDead.eatPiece();
                    pieceDead.getCase().setPiece(null);

                    pieceDead.setCase(null);

                    previewMouvement = algorithme.previewAutorisedMouvement(selectedPiece,plateBack,false);

                    if (previewMouvement.getPiecesRemove().size() == 0){
                        selectedPiece=null;
                        mustEat = false;
                        resetDisplayMovement();
                        endTurn();
                    }else{
                        mustEat = true;
                        resetDisplayMovement();
                        showDisplayMovement();
                    }
                }else{
                    resetDisplayMovement();
                    endTurn();

                }
            }
        }
    }

    public void endTurn(){
        //  if(!algorithme.PlayerCanEat(colorTurn,plateBack))


        if(colorTurn == "noir"){
            colorTurn = "blanc";
        }
        else if(colorTurn == "blanc")
            colorTurn = "noir";
        mustEatStart = algorithme.playerCanEat(colorTurn,plateBack);

    }
}