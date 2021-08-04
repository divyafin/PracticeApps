package com.example.realmsavedataapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class RetriveDatafromRm extends AppCompatActivity {

    private Realm realm;
    List<DataModel> dataModelList;
    RecyclerView getdataRv;
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_datafrom_rm);

        realm=Realm.getDefaultInstance();
        getdataRv=findViewById(R.id.realmrecyclerview);
        dataModelList=new ArrayList<>();

        dataModelList=realm.where(DataModel.class).findAll();
        courseAdapter=new CourseAdapter(dataModelList,this);
        getdataRv.setLayoutManager(new LinearLayoutManager(this));
        getdataRv.setAdapter(courseAdapter);


    }
}