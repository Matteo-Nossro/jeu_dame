package fr.iut.ddim.jeu_dame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public EditText nom;

    public String idJoueur;
    public String nomJoueur;
    public String nbPartiesJoueur;
    public String nbVictoiresJoueur;
    public String nbDefaitesJoueur;

    public String apiUrl;
    public String apiGetOnePlayer;
    public String urlGetOnePlayer;
    public String apiCreatePlayer;
    public String urlCreatePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom = this.findViewById(R.id.lib_nom);
        nom.setText("");
        apiUrl = "https://iutdijon.u-bourgogne.fr/mmiddim/Etudiants/DDIM2122/ngm202223/API_Dames/api/";
    }


    /**
     * Se connecter si le nom de joueur saisie est valide
     * @param view
     * @throws IOException
     */
    public void onClickConnexion(View view) throws IOException {
        // Construction du lien de l'API
        nomJoueur = nom.getText().toString();
        apiGetOnePlayer = apiUrl + "read_one.php?name=";
        urlGetOnePlayer = apiGetOnePlayer + nomJoueur;

        // Construction de la requête HTTP
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder()
                .url(urlGetOnePlayer)
                .build();
        client.newCall(requete).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject resultJSON = new JSONObject(response.body().string());
                        setJoueurApi(resultJSON);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Ce nom n'existe pas !",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * Créer le joueur avec le nom fournit dans le libellé si le nom n'existe pas déjà
     * @param view
     * @throws IOException
     */
    public void onClickCreeJoueur(View view) throws IOException {
        // Construction du lien de l'API
        nomJoueur = nom.getText().toString();

        if (nomJoueur.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            MainActivity.this,
                            "Ce nom n'est pas valide !",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        }
        else {
            apiCreatePlayer = apiUrl + "create.php?name=";
            urlCreatePlayer = apiCreatePlayer + nomJoueur;

            Log.i("URL", urlCreatePlayer);
            // Construction de la requête HTTP
            OkHttpClient client = new OkHttpClient();
            Request requeteCree = new Request.Builder()
                    .url(urlCreatePlayer)
                    .build();
            client.newCall(requeteCree).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) {
                    Log.i("api", urlCreatePlayer);
                    if (response.isSuccessful()) {
                        Log.i("Réponse", "OK");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(
                                        MainActivity.this,
                                        "Joueur créé, vous pouvez vous connecter !",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(
                                        MainActivity.this,
                                        "Ce nom est déjà prit !",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        });
                    }
                }
            });
        }
    }

    /**
     * Affecter les informations de la base de données au joueur
     * @param resultJSON
     */
    private void setJoueurApi(JSONObject resultJSON) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                idJoueur = resultJSON.optString("ID");
                nomJoueur = resultJSON.optString("Nom");
                nbPartiesJoueur = resultJSON.optString("NombreParties");
                nbVictoiresJoueur = resultJSON.optString("NombreVictoires");
                nbDefaitesJoueur = resultJSON.optString("NombreDefaites");

                Game.joueur1.setId(resultJSON.optInt("ID"));
                Game.joueur1.setNom(resultJSON.optString("Nom"));
                Game.joueur1.setNbParties(resultJSON.optInt("NombreParties"));
                Game.joueur1.setNbVictoires(resultJSON.optInt("NombreVictoires"));
                Game.joueur1.setNbDefaites(resultJSON.optInt("NombreDefaites"));

                // On vérifie que le joueur est bien implémenté et on passe sur l'écran d'accueil
                if (idJoueur != "" && nomJoueur != "" && nbPartiesJoueur != "" &&
                        nbVictoiresJoueur != "" && nbDefaitesJoueur != "") {
                    Intent intentAccueil = new Intent(getApplicationContext(), Accueil.class);
                    startActivity(intentAccueil);
                }
            }
        });
    }


}

