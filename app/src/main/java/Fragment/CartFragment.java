package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Activity.Normal_User;
import Activity.PlaceOrderActivity;
import Activity.UpdateUserActivity;
import Adapter.CartAdapter;
import Model.CartModel;
import user.User;

public class CartFragment extends Fragment {
    RecyclerView cart_RC;
    List<CartModel> cartModelList;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference reference;

    CartAdapter cartAdapter;
    TextView totalAmount;
    Button buynow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root= inflater.inflate(R.layout.fragment_gift, container, false);

        //firebase declare
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        user=FirebaseAuth.getInstance().getCurrentUser();

        cart_RC=root.findViewById(R.id.recyclerview);
        totalAmount=root.findViewById(R.id.totalAmount);
        buynow=root.findViewById(R.id.buy_now);
        cart_RC.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList = new ArrayList<>();
        cartAdapter =new CartAdapter(getActivity(), cartModelList);
        cart_RC.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        String documentID= documentSnapshot.getId();
                        CartModel cartModel= documentSnapshot.toObject(CartModel.class);
                        cartModel.setId(documentID);
                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();
                    }
                    calculateTotalAmount();
                }
            }
        });
        final User[] user1 = new User[1];
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User users = snapshot.getValue(User.class);
                user1[0] = users;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi"+error.getMessage()+"!", Toast.LENGTH_SHORT).show();
            }
        });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartModelList.size()>0) {
                    if(user1[0].getAddress().equals("")){
                        Toast.makeText(view.getContext(),"Vui lòng cập nhật thông tin địa chỉ của bạn!",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), UpdateUserActivity.class));
                    }else {
                        Intent i = new Intent(getContext(), PlaceOrderActivity.class);
                        i.putExtra("itemlist", (Serializable) cartModelList);
                        startActivity(i);
                    }
                }else {
                    Toast.makeText(view.getContext(), "Bạn chưa có mặt hàng nào! Hãy thêm một vài món vào giỏ hàng", Toast.LENGTH_LONG).show();
                    Intent i= new Intent(getContext(), Normal_User.class);
                    startActivity(i);
                }
            }
        });
        return root;
    }

    private void calculateTotalAmount() {
        double totalamount=0.0;
        for(CartModel cartModel:cartModelList){
            totalamount+=cartModel.getTotalPrice();
        }
        totalAmount.setText("Thành Tiền: "+totalamount+" đ");
    }

}