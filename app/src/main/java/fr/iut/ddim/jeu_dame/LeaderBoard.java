package fr.iut.ddim.jeu_dame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LeaderBoard extends AppCompatActivity {

    private Button btReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
    }

    /**
     * bouton de retour
     * a lié
     * @param view
     */
    public void onClickReturn(View view){
        this.finish();
    }

}