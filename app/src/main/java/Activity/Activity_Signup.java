package Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.Activity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import user.User;


public class Activity_Signup extends AppCompatActivity {
    private EditText emailuser,hoten,pass,comfirmpass;
    private Button sigup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        init();
    }
    public void init(){
        hoten=findViewById(R.id.edthoten);
        emailuser=findViewById(R.id.edtemail);
        pass=findViewById(R.id.signuppass);
        comfirmpass=findViewById(R.id.edtcofirmpass);
        sigup=findViewById(R.id.btnsignup);
        progressDialog = new ProgressDialog(this);

        firebaseAuth= FirebaseAuth.getInstance();

        sigup.setOnClickListener(view -> signup());
    }
    public void signup() {
        String name = hoten.getText().toString().trim();
        String email = emailuser.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String passwordcomfirm = comfirmpass.getText().toString().trim();
        String address="",phone="",sex="";
        if (email.isEmpty()) {
            emailuser.setError("Bạn chưa nhập email!");
            emailuser.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailuser.setError("Email không đúng!");
            emailuser.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            hoten.setError("Bạn chưa nhập họ tên!");
            hoten.requestFocus();
            return;
        }
        if(password.length()<8||password.length()>16){
            pass.setError("Mật khẩu cần dài từ 8-16 kí tự");
            pass.requestFocus();
            return;
        }
        if(!checknum(password)){
            pass.setError("Mật khẩu cần ít nhất 1 số");
            pass.requestFocus();
            return;
        }
        if(!check1lowercase(password)){
            pass.setError("Mật khẩu cần ít nhất 1 chữ thường");
            pass.requestFocus();
            return;
        }
        if(checkWhiteSpace(password)){
            pass.setError("Mật khẩu không được chứa khoảng trắng");
            pass.requestFocus();
            return;
        }

        if (passwordcomfirm.isEmpty()) {
            comfirmpass.setError("Bạn cần xác nhận mật khẩu!");
            comfirmpass.requestFocus();
            return;
        }
        if (!password.equals(passwordcomfirm)) {
            comfirmpass.setError("Mật khẩu xác nhận không đúng");
            comfirmpass.requestFocus();
            return;
        }
        progressDialog.setMessage("Đang đăng kí...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(email, name, password,sex,phone,address);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        progressDialog.cancel();
                                        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                        user1.sendEmailVerification();
                                        Toast.makeText(Activity_Signup.this, "Chúc mừng bạn đã đăng kí thành công! vui lòng kiểm tra email để xác nhận", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(this,Login.class));
                                    } else {
                                        progressDialog.cancel();
                                        Toast.makeText(Activity_Signup.this, "Đăng kí thất bại, vui lòng thử lại!", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        progressDialog.cancel();
                        Toast.makeText(Activity_Signup.this, "Đăng kí thất bại, vui lòng kiểm tra lại!", Toast.LENGTH_LONG).show();
                    }
                });

    }
    public void loginn(View view){
        startActivity(new Intent(Activity_Signup.this, Login.class));
    }
    public static boolean checknum(String s) {
        String pattern = ".*\\d.*";
        return s.matches(pattern);
    }
    public static boolean check1lowercase(String s) {
        String pattern = ".*\\w.*";
        return s.matches(pattern);
    }
    public static boolean checkWhiteSpace(String s) {
        return s.contains(" ");
    }


}