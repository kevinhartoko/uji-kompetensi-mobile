package com.example.aldoduha.ujikompetensi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by aldoduha on 12/10/2017.
 */

public class KYNIntervieweeListAdapter extends ArrayAdapter<KYNIntervieweeModel> {
    private int resourceId;
    private List<KYNIntervieweeModel> intervieweeModels;

    public KYNIntervieweeListAdapter(Context context, int resourceId, List<KYNIntervieweeModel> intervieweeModels) {
        super(context, resourceId, intervieweeModels);
        this.resourceId = resourceId;
        this.intervieweeModels = intervieweeModels;
    }

    public void updateList(List<KYNIntervieweeModel> model){
        intervieweeModels.clear();
        intervieweeModels.addAll(model);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TextView name;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }
        final KYNIntervieweeModel intervieweeModel = intervieweeModels.get(position);
        name = (TextView)convertView.findViewById(R.id.textviewNama);
        TextView dob = (TextView) convertView.findViewById(R.id.textviewDOB);
        TextView gender = (TextView)convertView.findViewById(R.id.textviewGender);
        TextView address = (TextView)convertView.findViewById(R.id.textviewAddress);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        if(intervieweeModel.getNama()!=null){
            name.setText(intervieweeModel.getNama());
        }
        if(intervieweeModel.getDob()!=null){
            dob.setText(format.format(intervieweeModel.getDob()));
        }
        if(intervieweeModel.getGender()!=null){
            if(intervieweeModel.getGender().equals("L"))
                gender.setText("Laki-Laki");
            else if(intervieweeModel.getGender().equals("P"))
                gender.setText("Perempuan");
            else
                gender.setText("");
        }
        if(intervieweeModel.getAddress()!=null){
            address.setText(intervieweeModel.getAddress());
        }
        return convertView;
    }
}
