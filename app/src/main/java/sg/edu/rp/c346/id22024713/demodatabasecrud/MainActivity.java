package sg.edu.rp.c346.id22024713.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ListView lv;
    ArrayList<Note> al;
    ArrayAdapter<Note> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etContent = findViewById(R.id.editTextText);
        btnAdd = findViewById(R.id.button);
        btnEdit = findViewById(R.id.button2);
        btnRetrieve = findViewById(R.id.button3);
        lv = findViewById(R.id.listView);

        al = new ArrayList<Note>();
        aa = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        btnAdd.setOnClickListener(v -> {
            String data = etContent.getText().toString();
            DBHelper dbh = new DBHelper(MainActivity.this);
            long inserted_id = dbh.insertNote(data);

            if (inserted_id != -1) {
                al.clear();
                al.addAll(dbh.getAllNotes());
                aa.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Insert successful.", Toast.LENGTH_SHORT).show();
            }
        });

        btnRetrieve.setOnClickListener(v -> {
            DBHelper dbh = new DBHelper(MainActivity.this);
            al.clear();
            String filterText = etContent.getText().toString().trim();
            if(filterText.length() == 0) {
                al.addAll(dbh.getAllNotes());
            }
            else{
                al.addAll(dbh.getAllNotes(filterText));
            }
            aa.notifyDataSetChanged();
        });

        btnEdit.setOnClickListener(v -> {
            Note target = al.get(0);
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("data", target);
            startActivity(intent);
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Note data = al.get(position);
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        btnRetrieve.performClick();
    }
}