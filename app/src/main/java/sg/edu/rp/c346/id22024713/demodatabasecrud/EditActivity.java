package sg.edu.rp.c346.id22024713.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    TextView tvID;
    EditText etContent;
    Button btnUpdate, btnDelete;
    Note data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvID = findViewById(R.id.textView2);
        etContent = findViewById(R.id.editTextText2);
        btnUpdate = findViewById(R.id.button4);
        btnDelete = findViewById(R.id.button5);

        Intent intent = getIntent();
        data = (Note) intent.getSerializableExtra("data");

        tvID.setText("ID: " + data.getId());
        etContent.setText(data.getNoteContent());

        btnUpdate.setOnClickListener(v -> {
            DBHelper dbh = new DBHelper(EditActivity.this);
            data.setNoteContent(etContent.getText().toString());
            dbh.updateNote(data);
            dbh.close();
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            DBHelper dbh = new DBHelper(EditActivity.this);
            dbh.deleteNote(data.getId());
            finish();
        });
    }
}