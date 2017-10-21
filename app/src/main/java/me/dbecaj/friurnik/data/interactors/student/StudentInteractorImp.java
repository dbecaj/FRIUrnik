package me.dbecaj.friurnik.data.interactors.student;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.models.student.StudentModel;
import me.dbecaj.friurnik.data.models.student.StudentModel_Table;
import me.dbecaj.friurnik.data.system.ResourceProvider;

/**
 * Created by Dominik on 20-Oct-17.
 */

public class StudentInteractorImp implements StudentInteractor {

    @Override
    public void getDefaultStudent(StudentListener listener) {
        List<StudentModel> defaultStudent = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.isDefault.is(true)).queryList();
        if(defaultStudent.isEmpty()) {
            listener.failure(ResourceProvider.getString(R.string.error_no_default_student_in_database));

            return;
        }
        else if(defaultStudent.size() > 1) {
            listener.failure(ResourceProvider
                    .getString(R.string.error_multiple_default_students_in_database));

            return;
        }

        listener.successful(defaultStudent.get(0).getStudentId());
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
            listener.failure(ResourceProvider.getString(R.string.error_student_already_in_database));
            return;
        }

        StudentModel studentModel = new StudentModel(studentId, isDefault);
        studentModel.save();
        listener.successful(studentId);
    }

    @Override
    public boolean hasDefaultStudent() {
        List<StudentModel> defaultStudent = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.isDefault.is(true)).queryList();
        if(defaultStudent.isEmpty()) {
            return false;
        }

        return true;
    }
}
