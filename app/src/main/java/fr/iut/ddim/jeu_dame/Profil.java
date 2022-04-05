package fr.iut.ddim.jeu_dame;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Profil extends AppCompatActivity {


    //private TextView id;
    private TextView nom;
    private TextView nbParties;
    private TextView nbVictoires;
    private TextView nbDefaites;

    // La PopUp
    PopUpChangeNom pop;
    private EditText editTextNom;
    private String nomEnCours;
    private String apiUrl;
    private String apiVerifieSiExist;
    private String apiUpdateUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //id = this.findViewById(R.id.libID);
        nom = this.findViewById(R.id.libNom);
        nbParties = this.findViewById(R.id.libNbParties);
        nbVictoires = this.findViewById(R.id.libNbVictoires);
        nbDefaites = this.findViewById(R.id.libNbDefaites);

        //id.setText(String.valueOf(Game.joueur1.getId()));
        nom.setText(Game.joueur1.getNom());
        nbParties.setText(String.valueOf("Parties jouées : " + Game.joueur1.getNbParties()));
        nbVictoires.setText(String.valueOf("Victoires : " + Game.joueur1.getNbVictoires()));
        nbDefaites.setText(String.valueOf("Défaites : " + Game.joueur1.getNbDefaites()));

        // La PopUp
        pop = new PopUpChangeNom(this);
        editTextNom = pop.findViewById(R.id.editTextNom);
        apiUrl = "https://iutdijon.u-bourgogne.fr/";
        apiUrl += "mmiddim/Etudiants/DDIM2122/ngm202223/API_Dames/api/";
    }

    public void onClickAffichePopUpChangeNom(View view) {
        pop.build();
    }

    public void onClickValideNom(View view) {
        nomEnCours = editTextNom.getText().toString();
        apiVerifieSiExist = apiUrl + "read_one.php?name=" + nom;

        if (Game.joueur1.getNom() == nomEnCours) {
            Toast.makeText(
                    pop.getContext(),
                    "C'est le même nom !",
                    Toast.LENGTH_LONG
            );
        }
        else {
            // Construction de la requête HTTP
            OkHttpClient client = new OkHttpClient();
            Request requete = new Request.Builder()
                    .url(apiVerifieSiExist)
                    .build();
            client.newCall(requete).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().toString();
                        // Si le nom de joueur n'est pas déjà prit
                        if (result == "Joueur non trouvé !") {
                            // OK
                        }
                        else {
                            // Pas Ok
                        }
                    }
                    else {
                    }
                }
            });
        }
    }

    public void onClickAnnule(View view) {
        pop.dismiss();
    }
}