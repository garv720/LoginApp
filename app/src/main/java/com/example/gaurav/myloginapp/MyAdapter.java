package com.example.gaurav.myloginapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private List<Marks> marksList;


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

            @Override
            public void onClick(final View view) {
                View promptsView = LayoutInflater.from(view.getContext())
                        .inflate(R.layout.prompts, null, false);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setView(promptsView);

                final EditText maths = (EditText) promptsView.findViewById(R.id.mathsedit);
                final EditText physics = (EditText) promptsView.findViewById(R.id.physicsedit);
                final EditText chemistry = (EditText) promptsView.findViewById(R.id.chemistryedit);
                final EditText fcp = (EditText) promptsView.findViewById(R.id.fcpedit);
                final EditText bme = (EditText) promptsView.findViewById(R.id.bmeedit);

                builder
                        .setCancelable(false)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public boolean already;
                            public boolean inserted = true;

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseHelper db = new DatabaseHelper(view.getContext());
                                marksList = new ArrayList<>();

                                marksList.addAll(db.getMarks());
                                for (int k = 0; k < marksList.size(); k++) {
                                    if (marksList.get(k).getEmail().equals(dataList.get(pos).getEmail())) {
                                        inserted = db.updateMarks(dataList.get(pos).getEmail(), maths.getText().toString(),
                                                physics.getText().toString(), chemistry.getText().toString(), fcp.getText().toString(),
                                                bme.getText().toString());
                                        already = true;
                                    }
                                }
                                if (!already) {
                                    inserted = db.setMarks(dataList.get(pos).getEmail(), maths.getText().toString(),
                                            physics.getText().toString(), chemistry.getText().toString(), fcp.getText().toString(),
                                            bme.getText().toString());
                                    if (inserted) {
                                        Toast.makeText(view.getContext(), "Marks Inserted", Toast.LENGTH_LONG).show();
                                    } else
                                        Toast.makeText(view.getContext(), "Error", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    if (inserted) {
                                        Toast.makeText(view.getContext(), "Marks Updated", Toast.LENGTH_LONG).show();
                                    } else
                                        Toast.makeText(view.getContext(), "Error", Toast.LENGTH_LONG).show();
                                }
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
        for (int i=0;i<marksList.size();i++){
            Marks marks = marksList.get(i);
            if (marks.getEmail().equals(email)){
                avg = String.valueOf((Double.parseDouble(marks.getMaths()) + Double.parseDouble(marks.getPhysics())
                        + Double.parseDouble(marks.getChemistry()) +Double.parseDouble( marks.getFcp())
                        + Double.parseDouble(marks.getBme())) / 5)+" %";
                break;
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
