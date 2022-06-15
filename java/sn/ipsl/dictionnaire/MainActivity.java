package sn.ipsl.dictionnaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnImgAjouter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Mots>etudiants;
    RecyclerAdapter recylerAdapter;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingPB = findViewById(R.id.idProgressBar);

        btnImgAjouter = findViewById(R.id.buttonAddImage);
        btnImgAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AjouterMots.class);
                startActivity(i);
            }
        });

        recyclerView = findViewById(R.id.recyle);
        bulid();


    }
    public void bulid(){

        databaseReference = FirebaseDatabase.getInstance().getReference("Mots");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etudiants = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    loadingPB.setVisibility(View.GONE);

                    etudiants.add(dataSnapshot.getValue(Mots.class));
                }

                recylerAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });    //databaseReference.addListenerForSingleValueEvent(valueEventListener)
        recylerAdapter = new RecyclerAdapter(etudiants, getApplicationContext());
        recyclerView.setAdapter(recylerAdapter);
    }


    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Mots> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Mots item : etudiants) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getMot().toLowerCase().contains(text.toLowerCase()) ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            recylerAdapter.filterList(filteredlist);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.searchs);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Saisir ici");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_settings){
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}