package fr.iut.ddim.jeu_dame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Accueil extends AppCompatActivity {

    private Button btPlay;
    private Button btLeaderBoard;
    private Button btSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);


        btPlay = findViewById(R.id.Play);
        btLeaderBoard = findViewById(R.id.LeaderBoard);
        btSettings = findViewById(R.id.Settings);


    }
    /// setup les boutons de navigations

    /**
     * lance la partie
     * @param view
     */
    public void onClickPlay(View view) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void onClickLeaderBoard(View view) {
        Intent leaderBoardActivity = new Intent(this, LeaderBoard.class);
        startActivity(leaderBoardActivity);
    }

    public void onClickSettings(View view) {
        Intent settingsActivity = new Intent(this, Settings.class);
        startActivity(settingsActivity);
    }
}