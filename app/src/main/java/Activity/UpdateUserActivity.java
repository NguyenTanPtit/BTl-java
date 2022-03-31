package Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Activity.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateUserActivity extends AppCompatActivity {
    CircleImageView profileImg;
    EditText name,phone,address;
    Button update;
    RadioButton male,female,other;

    FirebaseStorage storage;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase db;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_user);
        //firebase
        storage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        profileImg=findViewById(R.id.profile_img);
        update=findViewById(R.id.update_profile);
        name=findViewById(R.id.edt_profile_name);
        phone=findViewById(R.id.edt_profile_phone);
        address=findViewById(R.id.edt_profile_add);
        male=findViewById(R.id.radioButtonMale);
        female=findViewById(R.id.radioButtonFemale);
        other=findViewById(R.id.radioButtonOther);

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,33);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }
    private void updateProfile(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentuserId=user.getUid();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref=firebaseDatabase.getReference("Users").child(currentuserId);
        String uname= name.getText().toString().trim();
        if(uname.isEmpty()){
            name.setError("Họ tên không được trống");
            return;
        }
        String uphone=phone.getText().toString().trim();
        if(uphone.isEmpty()){
            phone.setError("Số điện thoại không được trống");
            return;
        }
        if(uphone.length()<10||uphone.length()>12){
            phone.setError( "Số điện thoại chưa đúng định dạng");
            return;
        }
        String uaddress=address.getText().toString().trim();
        if(uaddress.isEmpty()){
            address.setError("Địa chỉ không được bỏ trống");
            return;
        }
        String usex ="";
        if(male.isChecked()){
            usex="Nam";
        }
        else if(female.isChecked()){
            usex="Nữ";
        }
        else if(other.isChecked())
            usex="Khác";
        if(usex.isEmpty()){
            Toast.makeText(this,"Giới tính cần được cập nhật",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,Object> infor= new HashMap<>();
        infor.put("name",uname);
        infor.put("phone",uphone);
        infor.put("address",uaddress);
        infor.put("sex",usex);

        myref.updateChildren(infor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(UpdateUserActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            Uri profileUri=data.getData();
            profileImg.setImageURI(profileUri);
            final StorageReference reference= storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UpdateUserActivity.this, "Đang upload", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            db.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImg").setValue(uri.toString());

                            Toast.makeText(UpdateUserActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}