package me.dbecaj.friurnik.ui.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.SubjectModel;
import me.dbecaj.friurnik.data.system.ResourceProvider;

public class ScheduleListAdapter extends BaseAdapter {

    private ArrayList<SubjectModel> subjects;
    private Context context;

    public ScheduleListAdapter(ArrayList<SubjectModel> subjects, Context context) {
        this.subjects = subjects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void addItem(SubjectModel subject) {
        subjects.add(subject);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.subject_layout, parent,
                    false);
        }

        SubjectModel subject = (SubjectModel)getItem(position);

        TextView nameText = (TextView)convertView.findViewById(R.id.subject_name);
        TextView timeText = (TextView)convertView.findViewById(R.id.subject_time);
        TextView professorText = (TextView)convertView.findViewById(R.id.subject_professor);
        TextView classroomText = (TextView)convertView.findViewById(R.id.subject_classroom);

        nameText.setText(subject.getName());
        timeText.setText(ResourceProvider.getString(R.string.placeholder_subject_time,
                subject.getStartHour(), subject.getEndHour()));
        professorText.setText(subject.getProfessor());
        classroomText.setText(subject.getClassroom());

        return convertView;
    }
}
