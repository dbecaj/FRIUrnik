package me.dbecaj.friurnik.data.interactors.student;

import android.database.sqlite.SQLiteQuery;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.interactors.GenericListener;
import me.dbecaj.friurnik.data.models.StudentModel;
import me.dbecaj.friurnik.data.models.StudentModel_Table;
import me.dbecaj.friurnik.data.system.ResourceProvider;

/**
 * Created by Dominik on 20-Oct-17.
 */

public class StudentInteractorImp implements StudentInteractor {

    @Override
    public StudentModel getDefaultStudent() {
        List<StudentModel> defaultStudent = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.defaultStudent.is(true)).queryList();

        return defaultStudent.get(0);
    }

    @Override
    public List<StudentModel> getAllStudents() {
        List<StudentModel> students = SQLite.select().from(StudentModel.class).queryList();

        return students;
    }

    @Override
    public void saveStudent(long studentId, StudentListener listener) {
        saveStudent(studentId, null, listener);
    }

    @Override
    public void saveStudent(long studentId, String nickname, StudentListener listener) {
        boolean isDefault = false;
        if(!hasDefaultStudent()) {
            isDefault = true;
        }

        if(hasStudent(studentId)) {
            listener.failure(R.string.error_student_already_in_database);
            return;
        }

        // Setting to null if empty because that way it does not save to database
        if(nickname != null && nickname.isEmpty()) {
            nickname = null;
        }

        StudentModel studentModel = new StudentModel(studentId, isDefault);
        // Give the default student the default nickname
        if(isDefault) {
            studentModel.setNickname(ResourceProvider.getString(R.string.me));
        }
        else {
            studentModel.setNickname(nickname);
        }
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
    public StudentModel getStudent(long studentId) {
        List<StudentModel> students = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.studentId.is(studentId)).queryList();

        return students.get(0);
    }

    @Override
    public boolean hasStudent(long studentId) {
        List<StudentModel> students = SQLite.select().from(StudentModel.class)
                .where(StudentModel_Table.studentId.is(studentId)).queryList();

        return !students.isEmpty();
    }

    @Override
    public void deleteStudent(long studentId, GenericListener listener) {
        if(!hasStudent(studentId)) {
            listener.failure(R.string.error_student_not_found_in_database);
            return;
        }
        // Prevent the deletion of a default student
        else if(getStudent(studentId).isDefaultStudent()) {
            listener.failure(R.string.error_trying_to_delete_default_student);
            return;
        }

        getStudent(studentId).delete();
        listener.success();
    }

    @Override
    public void updateStudent(long studentId, String nickname, StudentListener listener) {
        if(!hasStudent(studentId)) {
            listener.failure(R.string.error_student_not_found_in_database);
            return;
        }

        StudentModel student = getStudent(studentId);
        student.setStudentId(studentId);
        student.setNickname(nickname);
        if(student.update()) {
            listener.successful(student);
        }
        else {
            listener.failure(R.string.error_updating_student_database);
        }
    }

}
