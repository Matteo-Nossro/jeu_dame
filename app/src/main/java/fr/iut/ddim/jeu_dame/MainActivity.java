package fr.iut.ddim.jeu_dame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import fr.iut.ddim.jeu_dame.Game;

public class MainActivity extends AppCompatActivity {


    private GridView gridView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Game g = Game.getInstance();
        g.StartGame(new Player(),new Player(),gridView);
       // this.gridView = (GridView)findViewById(R.id.Plateau);

    }
}