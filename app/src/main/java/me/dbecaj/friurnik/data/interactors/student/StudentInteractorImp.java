package me.dbecaj.friurnik.data.interactors.student;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.StudentModel;
import me.dbecaj.friurnik.data.models.StudentModel_Table;

/**
 * Created by Dominik on 20-Oct-17.
 */

public class StudentInteractorImp implements StudentInteractor {

    @Override
    public void getDefaultStudent(StudentListener listener) {
        List<StudentModel> defaultStudent = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.defaultStudent.is(true)).queryList();
        if(defaultStudent.isEmpty()) {
            listener.failure(R.string.error_no_default_student_in_database);

            return;
        }
        else if(defaultStudent.size() > 1) {
            listener.failure(R.string.error_multiple_default_students_in_database);

            return;
        }

        listener.successful(defaultStudent.get(0));
    }

    @Override
    public List<StudentModel> getAllStudents() {
        List<StudentModel> students = SQLite.select().from(StudentModel.class).queryList();

        return students;
    }

    @Override
    public void saveStudent(long studentId, StudentListener listener) {
        boolean isDefault = false;
        List<StudentModel> allStudents = SQLite.select().from(StudentModel.class).queryList();
        if(allStudents.isEmpty()) {
            isDefault = true;
        }

        List<StudentModel> sameStudents = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.studentId.is(studentId)).queryList();
        if(!sameStudents.isEmpty()) {
            listener.failure(R.string.error_student_already_in_database);
            return;
        }

        StudentModel studentModel = new StudentModel(studentId, isDefault);
        studentModel.save();
        listener.successful(studentModel);
    }

    @Override
    public boolean hasDefaultStudent() {
        List<StudentModel> defaultStudent = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.defaultStudent.is(true)).queryList();

        return !defaultStudent.isEmpty();
    }

    @Override
    public void getStudent(long studentId, StudentListener listener) {
        List<StudentModel> students = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.studentId.is(studentId)).queryList();

        if(students.isEmpty()) {
            listener.failure(R.string.error_student_not_found_in_database);
            return;
        }

        listener.successful(students.get(0));
    }

    @Override
    public boolean hasStudent(long studentId) {
        List<StudentModel> students = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.studentId.is(studentId)).queryList();

        return !students.isEmpty();
    }
}
