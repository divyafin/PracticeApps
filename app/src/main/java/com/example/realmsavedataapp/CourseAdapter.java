package com.example.realmsavedataapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.realm.Realm;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Realm realm;
    List<DataModel> modelList;
    Context context;
    ImageView editcour, deletecour;

    public CourseAdapter(List<DataModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getcourses_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        DataModel modal = modelList.get(position);
        holder.cname.setText(modal.getCoursename());
        holder.cdesp.setText(modal.getCoursedescrip());
        holder.ctrack.setText(modal.getCoursetrack());
        holder.cdura.setText(modal.getCourseduration());

        editcour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UpdateCourse.class);
                i.putExtra("coursename", modal.getCoursename());
                i.putExtra("coursedesp", modal.getCoursedescrip());
                i.putExtra("coursetrack", modal.getCoursetrack());
                i.putExtra("coursedura", modal.getCourseduration());
                i.putExtra("id", modal.getId());
                context.startActivity(i);
            }
        });

        deletecour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm=Realm.getDefaultInstance();
                DataModel model = realm.where(DataModel.class).equalTo("id",modal.getId() ).findFirst();
                // on below line we are executing a realm transaction.
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // on below line we are calling a method for deleting this course
                        modal.deleteFromRealm();
                    }
                });
                Toast.makeText(context, "Course Deleted.", Toast.LENGTH_SHORT).show();
                // after that we are opening a new activity via an intent.
                Intent i = new Intent(context, RetriveDatafromRm.class);
                context.startActivity(i);

            }
        });
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cname, cdesp, ctrack, cdura;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cname = itemView.findViewById(R.id.getcname);
            cdesp = itemView.findViewById(R.id.getcdesp);
            ctrack = itemView.findViewById(R.id.gettrack);
            cdura = itemView.findViewById(R.id.getcduration);
            editcour = itemView.findViewById(R.id.editcour);
            deletecour = itemView.findViewById(R.id.deletecour);
        }
    }
}
