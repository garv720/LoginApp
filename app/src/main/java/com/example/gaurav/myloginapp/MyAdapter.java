package com.example.gaurav.myloginapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Student> dataList;
    private Context context;
    private List<Marks> marksList = new ArrayList<>();


    public MyAdapter(List<Student> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);

        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int pos) {

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            public int bmeMark;
            public int fcpMark;
            public int chemistryMark;
            public int physicsMark;
            public int mathsMark;

            @Override
            public void onClick(final View view) {

                final DatabaseHelper db = new DatabaseHelper(view.getContext());

                View promptsView = LayoutInflater.from(view.getContext())
                        .inflate(R.layout.prompts, null, false);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setView(promptsView);

                final TextView stuName = (TextView)promptsView.findViewById(R.id.stuName);
                final EditText maths = (EditText) promptsView.findViewById(R.id.mathsedit);
                final EditText physics = (EditText) promptsView.findViewById(R.id.physicsedit);
                final EditText chemistry = (EditText) promptsView.findViewById(R.id.chemistryedit);
                final EditText fcp = (EditText) promptsView.findViewById(R.id.fcpedit);
                final EditText bme = (EditText) promptsView.findViewById(R.id.bmeedit);

                stuName.setText(dataList.get(pos).getName().toUpperCase());
                marksList.addAll(db.getMarks());

                for (int k = 0; k < marksList.size(); k++) {
                    if (dataList.get(pos).getEmail().equals(marksList.get(k).getEmail())) {
                        maths.setText(marksList.get(k).getMaths());
                        physics.setText(marksList.get(k).getPhysics());
                        chemistry.setText(marksList.get(k).getChemistry());
                        fcp.setText(marksList.get(k).getFcp());
                        bme.setText(marksList.get(k).getBme());
                    }
                }


                    builder
                        .setCancelable(false)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public boolean already;
                            public boolean inserted = true;

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (String.valueOf(maths.getText()).equals("N/A"))
                                    mathsMark = 0;
                                else
                                    mathsMark = Integer.parseInt(maths.getText().toString());

                                if (String.valueOf(physics.getText()).equals("N/A"))
                                    physicsMark = 0;
                                else
                                    physicsMark = Integer.parseInt(physics.getText().toString());

                                if (String.valueOf(chemistry.getText()).equals("N/A"))
                                    chemistryMark = 0;
                                else
                                    chemistryMark = Integer.parseInt(chemistry.getText().toString());

                                if (String.valueOf(fcp.getText()).equals("N/A"))
                                    fcpMark = 0;
                                else
                                    fcpMark = Integer.parseInt(fcp.getText().toString());

                                if (String.valueOf(bme.getText()).equals("N/A"))
                                    bmeMark = 0;
                                else
                                    bmeMark = Integer.parseInt(bme.getText().toString());
                                if (mathsMark <= 100 && physicsMark <= 100 && chemistryMark <=100 && fcpMark <= 100 && bmeMark <= 100) {

                                    for (int k = 0; k < marksList.size(); k++) {
                                        if (marksList.get(k).getEmail().equals(dataList.get(pos).getEmail())) {
                                            inserted = db.updateMarks(dataList.get(pos).getEmail(), String.valueOf(mathsMark),
                                                    String.valueOf(physicsMark), String.valueOf(chemistryMark), String.valueOf(fcpMark),
                                                    String.valueOf(bmeMark));
                                            already = true;
                                        }
                                    }
                                    if (!already) {
                                        inserted = db.setMarks(dataList.get(pos).getEmail(), String.valueOf(mathsMark),
                                                String.valueOf(physicsMark), String.valueOf(chemistryMark), String.valueOf(fcpMark),
                                                String.valueOf(bmeMark));
                                        if (inserted) {
                                            Toast.makeText(view.getContext(), "Marks Inserted", Toast.LENGTH_LONG).show();
                                        } else
                                            Toast.makeText(view.getContext(), "Error", Toast.LENGTH_LONG).show();
                                    } else {
                                        if (inserted) {
                                            Toast.makeText(view.getContext(), "Marks Updated", Toast.LENGTH_LONG).show();
                                        } else
                                            Toast.makeText(view.getContext(), "Error", Toast.LENGTH_LONG).show();
                                    }
                                }else
                                    Toast.makeText(context, "Enter Valid Marks", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Student data = dataList.get(pos);

        myViewHolder.name.setText(data.getName());
        myViewHolder.branch.setText(data.getBranch());
        myViewHolder.marks.setText(setMarks(dataList.get(pos).getEmail()));

    }

    String setMarks(String email) {
        DatabaseHelper db = new DatabaseHelper(context);
        List<Marks> marksList = new ArrayList<>();
        marksList.addAll(db.getMarks());
        String avg = null;
        int maths = 0, physics= 0, chemistry= 0, fcp= 0, bme= 0;
        for (int i=0;i<marksList.size();i++){
            Marks marks = marksList.get(i);
            if (marks.getEmail().equals(email)){
                if (marks.getMaths().equals("N/A"))
                    maths = 0;
                else if (marks.getPhysics().equals("N/A"))
                    physics = 0;
                else if (marks.getChemistry().equals("N/A"))
                    chemistry = 0;
                else if (marks.getFcp().equals("N/A"))
                    fcp = 0;
                else if (marks.getBme().equals("N/A"))
                    bme = 0;
                else {
                    maths = Integer.parseInt(marks.getMaths());
                    physics = Integer.parseInt(marks.getPhysics());
                    chemistry = Integer.parseInt(marks.getChemistry());
                    fcp = Integer.parseInt(marks.getFcp());
                    bme = Integer.parseInt(marks.getBme());
                    avg = String.valueOf((maths+physics+chemistry+fcp+bme)/5) +" %";
                    break;
                }
            }
        }
        return avg;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView branch;
        private final TextView marks;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            branch = (TextView)itemView.findViewById(R.id.Branch);
            marks = (TextView)itemView.findViewById(R.id.marks);
        }
    }
}
