package sn.ipsl.dictionnaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    private EditText editMots , editType, editGenre, editExemple, editDescription, editSynonyme, editAntonyme;
    private Button modifier,suprimmer;
    private String mot, type, genre, exemple, description, synonyme, antonyme;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialisation
        editMots = findViewById(R.id.editMotsFr);
        editType = findViewById(R.id.editTypeFr);
        editGenre = findViewById(R.id.editGenreFr);
        editExemple = findViewById(R.id.editExempleFr);
        editDescription = findViewById(R.id.editDescriptionFr);
        editSynonyme = findViewById(R.id.editSynonymeFr);
        editAntonyme = findViewById(R.id.editAntonymeFr);


        mot = getIntent().getStringExtra("mot");
        type = getIntent().getStringExtra("type");
        genre = getIntent().getStringExtra("genre");
        exemple = getIntent().getStringExtra("exemple");
        description = getIntent().getStringExtra("description");
        synonyme = getIntent().getStringExtra("synonyme");
        antonyme = getIntent().getStringExtra("antonyme");

        // Recupération des EditText
        editMots.setText(mot);
        editType.setText(type);
        editGenre.setText(genre);
        editExemple.setText(exemple);
        editDescription.setText(description);
        editSynonyme.setText(synonyme);
        editAntonyme.setText(antonyme);

        modifier = findViewById(R.id.btnModifiermotsFr);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mot = editMots.getText().toString();
                String type = editType.getText().toString();
                String genre = editGenre.getText().toString();
                String exemple = editExemple.getText().toString();
                String description = editDescription.getText().toString();
                String synonyme = editSynonyme.getText().toString();
                String antonyme = editAntonyme.getText().toString();

                updateData(mot,type,genre,exemple,description,synonyme,antonyme);
            }
        });

        suprimmer = findViewById(R.id.btnSuprimmermotsFr);
        suprimmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mot = editMots.getText().toString();
                deleteData(mot);
            }
        });

    }

    //Mise à jour

    private void updateData(String mot, String type, String genre, String exemple, String description, String synonyme, String antonyme){
        Map<String, Object> mots=new HashMap<>();
        mots.put("Mot",mot);
        mots.put("Type",type);
        mots.put("Genre",genre);
        mots.put("Exemple",exemple);
        mots.put("Description",description);
        mots.put("Synonyme",synonyme);
        mots.put("Antonyme",antonyme);
        db = FirebaseDatabase.getInstance().getReference("Mots");
        db.child(editMots.getText().toString()).updateChildren(mots).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(Update.this,"Mot mis à jour ",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Update.this, MainActivity.class);
                    startActivity(i);

                }else {
                    Toast.makeText(Update.this,"Echec de la mise à jour du mot",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Supression

    private void deleteData(String mot){
        db = FirebaseDatabase.getInstance().getReference("Mots");
        db.child(editMots.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Update.this,"Mot suprimmé",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Update.this,MainActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(Update.this,"Echec de la supression du mot",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}