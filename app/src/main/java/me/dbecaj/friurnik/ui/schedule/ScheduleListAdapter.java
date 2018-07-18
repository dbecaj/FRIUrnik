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
    // subjectColorList will store the availableColorList index for each subject
    private ArrayList<Integer> subjectColorList;
    private ArrayList<Pair<Integer, Integer>> availableColorList;
    private Context context;

    public ScheduleListAdapter(ArrayList<SubjectModel> subjects, Context context) {
        this.subjectList = subjects;
        this.context = context;
        subjectColorList = new ArrayList<>();
        availableColorList = new ArrayList<>();

        // Add all available color pairs
        availableColorList.add(new Pair<>(R.color.subject_blue_light, R.color.subject_blue_dark));
        availableColorList.add(new Pair<>(R.color.subject_green_light, R.color.subject_green_dark));
        availableColorList.add(new Pair<>(R.color.subject_orange_light, R.color.subject_orange_dark));
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
        SubjectModel subject = (SubjectModel) getItem(position);

        // Load 2h or 3h layout depending on the subject duration
        if (subject.getDuration() == 2){
            convertView = LayoutInflater.from(context).inflate(R.layout.subject2h_layout, parent,
                    false);
        }
        else {
            convertView = LayoutInflater.from(context).inflate(R.layout.subject3h_layout, parent,
                    false);
        }


        RelativeLayout hourLayout = convertView
                .findViewById(R.id.subject_hour_layout);
        LinearLayout infoLayout = convertView
                .findViewById(R.id.subject_info_layout);
        TextView startHour = convertView.findViewById(R.id.subject_start_hour);
        TextView endHour = convertView.findViewById(R.id.subject_end_hour);
        TextView nameText = convertView.findViewById(R.id.subject_name);
        TextView professorText = convertView.findViewById(R.id.subject_professor);
        TextView classroomText = convertView.findViewById(R.id.subject_classroom);
        View lineSeperator = convertView.findViewById(R.id.subject_hour_line);

        // Add a random color available to the background
        Pair<Integer, Integer> randColor = availableColorList.get(assignRandomColor());
        hourLayout.setBackgroundResource(randColor.first);
        infoLayout.setBackgroundResource(randColor.second);
        lineSeperator.setBackgroundResource(randColor.first);
        // If there is a 3h layout we have 2 lines so we need to color the second one as well
        View lineSeperator2 = convertView.findViewById(R.id.subject_hour_line2);
        if (lineSeperator2 != null) {
            lineSeperator2.setBackgroundResource(randColor.first);
        }

        // Fill in the data
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
        subjectColorList.clear();
    }

    private int assignRandomColor() {
        Random rand = new Random();
        int randIndex = rand.nextInt(availableColorList.size());
        // Ensure that each subject has it's own color OR if there isn't enough colors that the
        // current subject's color is not the same as the previous one
        while (subjectColorList.contains(randIndex) &&
                (subjectList.size() > availableColorList.size() &&
                        !subjectColorList.isEmpty() &&
                        subjectColorList.get(subjectColorList.size() - 1) == randIndex)) {
            randIndex = rand.nextInt(availableColorList.size());
        }

        subjectColorList.add(randIndex);
        return randIndex;
    }
}
