package com.example.realmsavedataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class UpdateCourse extends AppCompatActivity {

    EditText edcname, edcdesp, edctrack, edcdura;
    String edtcname;
    String edtcdesp;
    String edtctrack;
    String edtcdura;
    long edtid;
    String txtcname;
    String txtcdesp;
    String txtctrack;
    String txtcdura;
    private Realm realm;
    private long id;
    Button updatecourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        realm = Realm.getDefaultInstance();
        edcname = findViewById(R.id.edtcname);
        edcdesp = findViewById(R.id.edtcdescp);
        edctrack = findViewById(R.id.edtctrack);
        edcdura = findViewById(R.id.edtcduration);
        updatecourses = findViewById(R.id.edtsave);

        edtcname = getIntent().getStringExtra("coursename");
        edtcdesp = getIntent().getStringExtra("coursedesp");
        edtctrack = getIntent().getStringExtra("coursetrack");
        edtcdura = getIntent().getStringExtra("coursedura");
        edtid = getIntent().getLongExtra("id",0);

        edcname.setText(edtcname);
        edcdesp.setText(edtcdesp);
        edctrack.setText(edtctrack);
        edcdura.setText(edtcdura);

        updatecourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtcname = edcname.getText().toString();
                 txtcdesp = edcdesp.getText().toString();
                 txtctrack = edctrack.getText().toString();
                 txtcdura = edcdura.getText().toString();

               realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        DataModel db = realm.where(DataModel.class).equalTo("id", edtid).findFirst();
                        if(db == null) {
                            db = realm.createObject(DataModel.class, edtid);
                        }

                        db.setCoursename(txtcname);
                        db.setCoursedescrip(txtcdesp);
                        db.setCoursetrack(txtctrack);
                        db.setCourseduration(txtcdura);
                        realm.copyToRealmOrUpdate(db);
                    }
                });
                Toast.makeText(UpdateCourse.this, "Course Updated.", Toast.LENGTH_SHORT).show();

                // on below line we are opening our activity for read course activity to view updated course.
                Intent i = new Intent(UpdateCourse.this, RetriveDatafromRm.class);
                startActivity(i);
                finish();
            }
        });
    }


}