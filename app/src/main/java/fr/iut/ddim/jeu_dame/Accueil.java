package fr.iut.ddim.jeu_dame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    /**
     * Afficher le profil du joueur
     * @param view
     */
    public void onClickAfficheProfil(View view) {
        Intent intentProfil = new Intent(getApplicationContext(), Profil.class);
        startActivity(intentProfil);
    }

    /**
     * Lancer le jeu
     * @param view
     */
    public void onClickLanceJeu(View view) {
        Intent intentGame = new Intent(getApplicationContext(), Dame.class);
        startActivity(intentGame);
    }

    /**
     * Fermer l'application
     * @param view
     */
    public void onClickQuitteJeu(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}