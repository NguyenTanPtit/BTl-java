package Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Activity.R;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityLogin extends AppCompatActivity {
    private ListView lv;
    private ArrayList<String> listmonhoc;
    private String[] listmon;
    private ArrayAdapter<String> adapter;
    private Context context;
    Button button,btupdate;
    EditText editText;
    private int pos=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        context=this;
        lv=(ListView)findViewById(R.id.Lv);
        button=(Button) findViewById(R.id.but);
        editText=(EditText) findViewById((R.id.edt));
        btupdate=(Button)findViewById(R.id.btupdate);
        //listmon=context.getResources().ArrayList(R.array.Listmon);
        //thêm phần tử vào lisview
        listmonhoc=new ArrayList<String>();
        listmonhoc.add("Tiếng Anh A22");
        listmonhoc.add("Cơ sở dữ liệu");
        listmonhoc.add("Lập trình với python");
        listmonhoc.add("Hệ điều hành");
        listmonhoc.add("Lập trình hướng đối tượng");
        listmonhoc.add("Mạng máy tính");
        listmonhoc.add("Kỹ năng thuyết trình");
        adapter= new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,listmonhoc  );
        lv.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item= editText.getText().toString().trim();
                if(TextUtils.isEmpty(item)){
                    Toast.makeText(context,"Bạn chưa nhập môn học",Toast.LENGTH_SHORT).show();
                    return;
                }
                listmonhoc.add(item);
                adapter.notifyDataSetChanged();
            }
        });
        //thay đổi nội dung phần tử trong listview
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               editText.setText(listmonhoc.get(position));
               pos= position;
           }
       });
       btupdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listmonhoc.set(pos,editText.getText().toString());
               adapter.notifyDataSetChanged();
           }
       });
       //xóa phần tử trong lisview
       lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               AlertDialog.Builder dialog =new AlertDialog.Builder(context);
               dialog.setTitle("Comfirm");
               dialog.setMessage("Do u want to delete?");
               dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       listmonhoc.remove(pos);
                       adapter.notifyDataSetChanged();
                   }
               });
               dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               AlertDialog alertDialog =dialog.create();
               alertDialog.show();
               return false;
           }
       });
    }
}