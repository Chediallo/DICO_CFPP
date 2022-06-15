package sn.ipsl.dictionnaire;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {
    ArrayList<Mots> motsArrayList;
    ArrayList<Mots> NewMotsArrayList;
    Context context;

    public RecyclerAdapter(ArrayList<Mots> motsArrayList, Context context) {
        this.motsArrayList = motsArrayList ;
        this.context = context;
        NewMotsArrayList=new ArrayList<>(motsArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mots mot = motsArrayList.get(position);
        holder.setDetailsEtudiant(mot);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),Update.class);
                intent.putExtra("mot",mot.getMot());
                intent.putExtra("type",mot.getType());
                intent.putExtra("genre",mot.getGenre());
                intent.putExtra("exemple",mot.getExemple());
                intent.putExtra("description",mot.getDescription());
                intent.putExtra("synonyme",mot.getSynonyme());
                intent.putExtra("antonyme",mot.getAntonyme());
                view.getContext().startActivity(intent);
            }
        });







    }

    @Override
    public int getItemCount() {
        return motsArrayList.size();
    }

    public void filterList(ArrayList<Mots> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        motsArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Mots>FilteretudiantArrayList=new ArrayList<>();
            if(charSequence.toString().isEmpty()||charSequence.length()==0){
                FilteretudiantArrayList.addAll(NewMotsArrayList)  ;
            } else{
                String motSaisie=charSequence.toString().toLowerCase();
                for(Mots mots:NewMotsArrayList){
                    if(mots.getMot().toLowerCase().contains(motSaisie)){
                        FilteretudiantArrayList.add(mots);

                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=FilteretudiantArrayList;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            motsArrayList.clear();
            motsArrayList.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMot, txtType, txtGenre, txtExemple, txtDescription, txtSynonyme,txtAntonyme;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialisation de nos variables
            txtMot = itemView.findViewById(R.id.txtMotsFr);
            txtType = itemView.findViewById(R.id.txtTypeFr);
            txtGenre = itemView.findViewById(R.id.txtGenreFr);
            txtExemple = itemView.findViewById(R.id.txtExempleFr);
            txtDescription = itemView.findViewById(R.id.txtDescriptionFr);
            txtSynonyme = itemView.findViewById(R.id.txtSynonymsFr);
            txtAntonyme = itemView.findViewById(R.id.txtAntonymsFr);


        }

        void setDetailsEtudiant(Mots mot) {
            txtMot.setText(mot.getMot());
            txtType.setText(mot.getType());
            txtGenre.setText(mot.getGenre());
            txtExemple.setText(mot.getExemple());
            txtDescription.setText(mot.getDescription());
            txtSynonyme.setText(mot.getSynonyme());
            txtAntonyme.setText(mot.getAntonyme());

        }
    }
    public void Show(){

    }
}