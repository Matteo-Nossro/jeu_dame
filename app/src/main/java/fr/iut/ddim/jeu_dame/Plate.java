package fr.iut.ddim.jeu_dame;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Plate {
    private Case[][] plate;
    public Case[][] getPlate() {
        return plate;
    }
    public void setPlate(Case[][] plate) {
        this.plate = plate;
    }

    private Plate(){
        ConstructPlateInitial(7,7);
    }
    private static Plate instance;

    public static Plate getInstance() {
        if (instance == null) {
            instance = new Plate();
        }
        return instance;
    }

    public void ConstructPlateInitial(int col,int row){
        Game g = Game.getInstance();
        GridView grid = g.GetgridView();
        grid.setNumColumns(col);
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++){
                LinearLayout l = new LinearLayout(grid.getContext());
                View v = new ImageView(grid.getContext()); // création de la case visuel

                l.addView(v);
                grid.addView(l,j);

                Case c = new Case(i,j);
                plate[i][j] = c;
                if(i<=3 && j%2==1)
                {
                    View v1 = new ImageView(grid.getContext()); // création de la case visuel
                    l.addView(v1);

                    Piece p = new Piece(c,"noir");
                    c.setPiece(p);
                }
                if(i>=(row-3) && j%2==1)
                {
                    View v2 = new ImageView(grid.getContext()); // création de la case visuel
                    l.addView(v2);

                    Piece p = new Piece(c,"blanc");
                    c.setPiece(p);
                }
            }
        }

    }
}
