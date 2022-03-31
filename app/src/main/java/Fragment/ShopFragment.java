package Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapter.ExploreAdapter;
import Adapter.PopularAdapter;
import Adapter.RecommenedAdapter;
import Adapter.ViewAllAdapter;
import Model.ExploreModel;
import Model.PopularModel;
import Model.RecommenedModel;
import Model.ViewallModel;

public class ShopFragment extends Fragment {

    RecyclerView popularrec,exploreRec,recommendedRec;
    FirebaseFirestore db;
    //popular items
    List<PopularModel> popularModelList;
    PopularAdapter popularAdapter;
    //explore items
    List<ExploreModel> exploreModelList;
    ExploreAdapter exploreAdapter;
    //recommended
    List<RecommenedModel> recommenedModelList;
    RecommenedAdapter recommenedAdapter;
    ///search
    EditText searchbar;
    private List<ViewallModel> viewallModelList;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root=  inflater.inflate(R.layout.fragment_shop, container, false);
        popularrec=root.findViewById(R.id.prop_rec);
        exploreRec=root.findViewById(R.id.explore_rec);
        recommendedRec=root.findViewById(R.id.recommended_rec);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Đang tải...");
        progressDialog.show();
        //firebase declare
        db=FirebaseFirestore.getInstance();
        //popular items
        popularrec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList= new ArrayList<>();
        popularAdapter=new PopularAdapter(getActivity(),popularModelList);
        popularrec.setAdapter(popularAdapter);
        db.collection("PopularProduct")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.cancel();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PopularModel popularModel= document.toObject(PopularModel.class);
                            popularModelList.add(popularModel);
                            popularAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Lỗi"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
        //explore items
        exploreRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        exploreModelList= new ArrayList<>();
        exploreAdapter=new ExploreAdapter(getActivity(),exploreModelList);
        exploreRec.setAdapter(exploreAdapter);
        db.collection("ExploreProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ExploreModel exploreModel= document.toObject(ExploreModel.class);
                            exploreModelList.add(exploreModel);
                            exploreAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Lỗi"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
        //recommended items
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommenedModelList= new ArrayList<>();
        recommenedAdapter=new RecommenedAdapter(getActivity(),recommenedModelList);
        recommendedRec.setAdapter(recommenedAdapter);
        db.collection("RecommendedProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            RecommenedModel recommenedModel= document.toObject(RecommenedModel.class);
                            recommenedModelList.add(recommenedModel);
                            recommenedAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Lỗi"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
        //Search
        searchbar=root.findViewById(R.id.search_box);
        recyclerViewSearch=root.findViewById(R.id.search_rec);
        viewallModelList= new ArrayList<>();
        viewAllAdapter= new ViewAllAdapter(getContext(),viewallModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().isEmpty()){
                    viewallModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }
                else {
                    searchProduct(chuanhoa(editable.toString()));
                }
            }
        });
        return root;
    }

    private void searchProduct(String type) {
        if(!type.isEmpty()){
            db.collection("AllProduction").whereGreaterThanOrEqualTo("type",type).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()&&task.getResult()!=null){
                            viewallModelList.clear();
                            viewAllAdapter.notifyDataSetChanged();
                            for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                ViewallModel viewallModel=doc.toObject(ViewallModel.class);
                                viewallModelList.add(viewallModel);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
    private String chuanhoa(String s){
        String name=s.trim().toLowerCase();
        String [] tu = name.split("\\s+");
        StringBuilder res= new StringBuilder();
        for(String str: tu){
            res.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).append(" ");
        }
        return res.toString().trim();
    }
}