package fr.iut.ddim.jeu_dame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    private GridLayout gridLayout;
    private static Case[][] plateBack = new Case[8][8];
    private Piece selectedPiece;
    private Algorithme_Prevision algorithme = new Algorithme_Prevision();
    private Algorithme_Prevision.PreviewAutorisedMouvementResult previewMouvement;
    private String colorTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gridLayout = (GridLayout)findViewById(R.id.plate);
        ImageView v = new ImageView(this); // création de la case visuel
        v.setImageDrawable(getDrawable(R.drawable.noir_2mdpi));
       // g.StartGame(new Player(),new Player(),gridLayout);
        this.ConstructPlateInitial(8,8);
        this.StartGame();
    }



    public Case[][] getPlateBack() {
        return plateBack;
    }

    public void setPlateBack(Case[][] value){
        this.plateBack=value;
    }

    //plateau
    public void ConstructPlateInitial(int col,int row){

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
                        OnClickCase(c);
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
                        OnClickPiece(p);
                    }
                });

                c.setPiece(p);
            }
        }

    }

    public void StartGame(){
        colorTurn = "noir";
    }

    public void OnClickPiece(Piece piece){//sender /event
        Log.i("color",""+piece.getColor());

        if(piece.getColor()==colorTurn) {
            previewMouvement = algorithme.PreviewAutorisedMouvement(piece,plateBack);
            this.selectedPiece = piece;
        }

    }

    public void OnClickCase(Case caseVisee)
    {
        if(selectedPiece != null){
            if(previewMouvement.getLstMouvement().contains(caseVisee)) { // On a les cases ou l'on peut aller
                if (previewMouvement.getPiecesRemove().containsKey(caseVisee)) { // la case ï¿½limine une piece
                    Piece pieceDead = previewMouvement.getPiecesRemove().get(caseVisee);
                    pieceDead.getCase().setPiece(null);
                    pieceDead.setCase(null);
                }
                selectedPiece.MovePiece(selectedPiece.getCase(), caseVisee);
                EndTurn();
            }


        }
        selectedPiece=null;
    }

    public void EndTurn(){
      //  if(!algorithme.PlayerCanEat(colorTurn,plateBack))
        {
            if(colorTurn == "noir")
                colorTurn = "blanc";
            else if(colorTurn == "blanc")
                colorTurn = "noir";
        }

    }
}

