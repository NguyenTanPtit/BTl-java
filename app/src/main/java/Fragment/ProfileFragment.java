package Fragment;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.Activity.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Objects;

import Activity.UpdateUserActivity;
import Activity.Welcome;
import de.hdodenhof.circleimageview.CircleImageView;
import user.User;


public class ProfileFragment extends Fragment {

    private CircleImageView profileImg;
    private TextView uname, uphone, uaddress, usex, uemail;
    private Button update,logout;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImg = root.findViewById(R.id.User_profile_img);
        uname = root.findViewById(R.id.user_name);
        uphone = root.findViewById(R.id.user_phone);
        uaddress = root.findViewById(R.id.user_address);
        usex = root.findViewById(R.id.user_sex);
        uemail = root.findViewById(R.id.user_email);
        update = root.findViewById(R.id.button_update);
        logout = root.findViewById(R.id.button_logout);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Đang tải...");
        progressDialog.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userId = user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null) {
                    progressDialog.cancel();
                    uname.setText(userprofile.getName());
                    uemail.setText(userprofile.getEmail());
                    uphone.setText(userprofile.getPhone());
                    uaddress.setText(userprofile.getAddress());
                    usex.setText(userprofile.getSex());
                    Glide.with(requireContext()).load(userprofile.getProfileImg()).into(profileImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi" + error.getMessage() + "!", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), UpdateUserActivity.class);
            startActivity(i);
        });
        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), Welcome.class));
        });
        return root;
    }
}