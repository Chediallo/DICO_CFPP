package sn.ipsl.dictionnaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AjouterMots extends AppCompatActivity {

    private EditText editMots , editType, editGenre, editExemple, editDescription, editSynonyme, editAntonyme;
    private Button ajouterMots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_mots);

        editMots = findViewById(R.id.editMotsFr);
        editType = findViewById(R.id.editTypeFr);
        editGenre = findViewById(R.id.editGenreFr);
        editExemple = findViewById(R.id.editExempleFr);
        editDescription = findViewById(R.id.editDescriptionFr);
        editSynonyme = findViewById(R.id.editSynonymeFr);
        editAntonyme = findViewById(R.id.editAntonymeFr);

        ajouterMots = findViewById(R.id.btnAjoutermotsFr);
        ajouterMots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mot = editMots.getText().toString();
                String type = editType.getText().toString();
                String genre = editGenre.getText().toString();
                String exemple = editExemple.getText().toString();
                String description = editDescription.getText().toString();
                String synonyme = editSynonyme.getText().toString();
                String antonyme = editAntonyme.getText().toString();

                if (TextUtils.isEmpty(mot)) {
                    editMots.setError("Veuillez renseigner le mot ");
                } else if (TextUtils.isEmpty(type)) {
                    editType.setError("Veuillez renseigner le type du mot ");
                } else if (TextUtils.isEmpty(genre)) {
                    editGenre.setError("Veuillez renseigner le genre du mot ");
                } else if (TextUtils.isEmpty(exemple)){
                    editExemple.setError("Veuillez donner un exemple du mot ");
                } else if (TextUtils.isEmpty(description)){
                    editDescription.setError("Veuillez renseigner une description du mot ");
                } else if (TextUtils.isEmpty(synonyme)){
                    editSynonyme.setError("Veuillez renseigner un synonyme du mot ");
                }else if (TextUtils.isEmpty(antonyme)){
                    editAntonyme.setError("Veuillez renseigner un antnonyme du mot ");
                }

                else {
                    ajouterMotsFirebase(mot,type,genre,exemple,description,synonyme,antonyme);

                }
            }
        });
    }

    private void ajouterMotsFirebase(String mot, String type, String genre, String exemple, String description, String synonyme, String antonyme) {
        Map<String, Object> mots=new HashMap<>();
        mots.put("Mot",mot);
        mots.put("Type",type);
        mots.put("Genre",genre);
        mots.put("Exemple",exemple);
        mots.put("Description",description);
        mots.put("Synonyme",synonyme);
        mots.put("Antonyme",antonyme);

        // Ajout des données dans FIREBASE
        FirebaseDatabase.getInstance().getReference("Mots").child(editMots.getText().toString())
                .setValue(mots)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Nouveau mot ajouté avec succes",Toast.LENGTH_LONG).show();
                        editMots.setText("");
                        editType.setText("");
                        editGenre.setText("");
                        editExemple.setText("");
                        editDescription.setText("");
                        editSynonyme.setText("");
                        editAntonyme.setText("");
                        Intent i = new Intent(AjouterMots.this, MainActivity.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Echec d'ajout du mot",Toast.LENGTH_LONG).show();
            }
        });
    }
}