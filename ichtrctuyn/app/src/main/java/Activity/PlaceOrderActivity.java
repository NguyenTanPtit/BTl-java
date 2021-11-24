package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import Model.CartModel;

public class PlaceOrderActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_place_order);

        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        List<CartModel> cartModelList= (ArrayList<CartModel>) getIntent().getSerializableExtra("itemlist");
        if(cartModelList!=null&& cartModelList.size()>0){
            for(CartModel cartModel :cartModelList){
                HashMap<String,Object> cart=new HashMap<>();
                cart.put("productName",cartModel.getProductName());
                cart.put("productPrice",cartModel.getProductPrice());
                cart.put("currentDate",cartModel.getCurrentDate());
                cart.put("currentTime",cartModel.getCurrentTime());
                cart.put("totalQuantity",cartModel.getTotalQuantity());
                cart.put("totalPrice",cartModel.getTotalPrice());
                db.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cart).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(PlaceOrderActivity.this, "Đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}