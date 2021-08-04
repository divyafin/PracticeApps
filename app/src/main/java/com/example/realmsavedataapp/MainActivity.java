package com.example.realmsavedataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    EditText cname, cdescrp, ctrack, cduration;
    Button savedata, getdatafromrm;
    private Realm realm;
    List<DataModel> mlist;
    private String coursename, coursedesp, coursetrack, courseduration;
    String[] namecourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        cname = findViewById(R.id.cname);
        cdescrp = findViewById(R.id.cdescp);
        ctrack = findViewById(R.id.ctrack);
        cduration = findViewById(R.id.cduration);
        savedata = findViewById(R.id.savetorm);
        getdatafromrm = findViewById(R.id.getdatarm);
        mlist = new ArrayList<>();


        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                coursename = cname.getText().toString();
                coursedesp = cdescrp.getText().toString();
                coursetrack = ctrack.getText().toString();
                courseduration = cduration.getText().toString();
                mlist = realm.where(DataModel.class).findAll();


                if (cname.getText().toString().isEmpty()) {
                    cname.setError("Please enter the course name");
                } else if (cdescrp.getText().toString().isEmpty()) {
                    cdescrp.setError("Please enter the course description");
                } else if (ctrack.getText().toString().isEmpty()) {
                    ctrack.setError("Please enter the course track");
                } else if (cduration.getText().toString().isEmpty()) {
                    cduration.setError("please enter the course duration");
                } else {
                    if(realm.isEmpty()){
                        addtoRealmdb(coursename, coursedesp, coursetrack, courseduration);
                        Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_LONG).show();
                        cname.setText("");
                        cdescrp.setText("");
                        ctrack.setText("");
                        cduration.setText("");
                    }
                    else {
                        List<DataModel> myownlist = realm.copyFromRealm(mlist);
                        namecourse = new String[myownlist.size()];
                        for (int i = 0; i < myownlist.size(); i++) {
                            namecourse[i] = myownlist.get(i).getCoursename();
                        }
                        for (int j = 0; j < myownlist.size(); j++) {
                            if (namecourse[j].matches(coursename)) {
                                Toast.makeText(MainActivity.this, "Duplicates not allowed" + namecourse[j], Toast.LENGTH_LONG).show();
                                cname.setText("");
                                cdescrp.setText("");
                                ctrack.setText("");
                                cduration.setText("");
                            } else {
                                addtoRealmdb(coursename, coursedesp, coursetrack, courseduration);
                                Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_LONG).show();
                                cname.setText("");
                                cdescrp.setText("");
                                ctrack.setText("");
                                cduration.setText("");
                            }
                        }
                    }

                }
            }
        });

        getdatafromrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RetriveDatafromRm.class);
                startActivity(intent);
            }
        });


    }

    private void addtoRealmdb(String coursename, String coursedesp, String coursetrack, String courseduration) {

        DataModel modal = new DataModel();
        Number id = realm.where(DataModel.class).max("id");
        long nextId;
        if (id == null) {
            nextId = 1;
        } else {
            nextId = id.intValue() + 1;
        }


        modal.setId(nextId);
        modal.setCoursename(coursename);
        modal.setCoursedescrip(coursedesp);
        modal.setCoursetrack(coursetrack);
        modal.setCourseduration(courseduration);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(modal);
            }
        });
    }


}