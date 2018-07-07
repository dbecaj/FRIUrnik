package me.dbecaj.friurnik.ui.schedule;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.SubjectModel;

public class ScheduleListAdapter extends BaseAdapter {

    private ArrayList<SubjectModel> subjectList;
    // We will not store color info into the SubjectModel so we will store it separately
    private ArrayList<Integer> subjectColorList;
    private ArrayList<Pair<Integer, Integer>> availableColorList;
    private Context context;

    public ScheduleListAdapter(ArrayList<SubjectModel> subjects, Context context) {
        this.subjectList = subjects;
        this.context = context;
        subjectColorList = new ArrayList<>();
        availableColorList = new ArrayList<>();

        availableColorList.add(new Pair<Integer, Integer>(
                context.getResources().getColor(R.color.subject_blue_light, null),
                context.getResources().getColor(R.color.subject_blue_dark, null)));
        availableColorList.add(new Pair<Integer, Integer>(
                context.getResources().getColor(R.color.subject_green_light, null),
                context.getResources().getColor(R.color.subject_green_dark, null)));

        availableColorList.add(new Pair<Integer, Integer>(
                context.getResources().getColor(R.color.subject_orange_light, null),
                context.getResources().getColor(R.color.subject_orange_dark, null)));
    }

    @Override
    public int getCount() {
        return subjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void addItem(SubjectModel subject) {
        subjectList.add(subject);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.subject_layout, parent,
                    false);
        }

        SubjectModel subject = (SubjectModel)getItem(position);

        RelativeLayout hourLayout = (RelativeLayout)convertView
                .findViewById(R.id.subject_hour_layout);
        LinearLayout infoLayout = (LinearLayout)convertView
                .findViewById(R.id.subject_info_layout);
        TextView startHour = (TextView)convertView.findViewById(R.id.subject_start_hour);
        TextView endHour = (TextView)convertView.findViewById(R.id.subject_end_hour);
        TextView nameText = (TextView)convertView.findViewById(R.id.subject_name);
        TextView professorText = (TextView)convertView.findViewById(R.id.subject_professor);
        TextView classroomText = (TextView)convertView.findViewById(R.id.subject_classroom);

        // Padd the item based on the duration of the subject (make the view longer)
        int normalPaddingInDp = infoLayout.getPaddingStart();
        int extraPadding = normalPaddingInDp * (subject.getDuration()*2);
        infoLayout.setPadding(normalPaddingInDp, extraPadding, normalPaddingInDp,
                extraPadding);
        infoLayout.setBackgroundResource()

        startHour.setText(context.getString(R.string.placeholder_hour,
                subject.getStartHour()));
        endHour.setText(context.getString(R.string.placeholder_hour,
                subject.getEndHour()));
        nameText.setText(subject.getName());
        professorText.setText(subject.getProfessor());
        classroomText.setText(subject.getClassroom());

        return convertView;
    }

    public void clearItems() {
        subjectList.clear();
    }

    private int asingRandomColor() {
        Random rand = new Random();

    }
}
