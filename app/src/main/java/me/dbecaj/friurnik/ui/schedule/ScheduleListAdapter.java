package me.dbecaj.friurnik.ui.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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

        LinearLayout holderLayout = (LinearLayout)convertView
                .findViewById(R.id.subject_holder_layout);
        TextView startHour = (TextView)convertView.findViewById(R.id.subject_start_hour);
        TextView endHour = (TextView)convertView.findViewById(R.id.subject_end_hour);
        TextView nameText = (TextView)convertView.findViewById(R.id.subject_name);
        TextView professorText = (TextView)convertView.findViewById(R.id.subject_professor);
        TextView classroomText = (TextView)convertView.findViewById(R.id.subject_classroom);

        // Padd the item based on the duration of the subject (make the view longer)
        int normalPaddingInDp = holderLayout.getPaddingStart();
        int extraPadding = normalPaddingInDp * (subject.getDuration()*2);
        holderLayout.setPadding(normalPaddingInDp, extraPadding, normalPaddingInDp,
                extraPadding);

        startHour.setText(context.getString(R.string.placeholder_hour,
                subject.getStartHour()));
        endHour.setText(context.getString(R.string.placeholder_hour,
                subject.getEndHour()));
        nameText.setText(subject.getName());
        professorText.setText(subject.getProfessor());
        classroomText.setText(subject.getClassroom());

        return convertView;
    }
}
