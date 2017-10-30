package me.dbecaj.friurnik.data.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import me.dbecaj.friurnik.data.database.FRIUrnikDatabase;

/**
 * Created by Dominik on 20-Oct-17.
 */

@Table(database = FRIUrnikDatabase.class)
public class StudentModel extends BaseModel{

    @Column
    @PrimaryKey
    private long studentId;

    @Column
    private boolean isDefault;

    protected StudentModel() {}

    public StudentModel(long studentId, boolean isDefault) {
        this.studentId = studentId;
        this.isDefault = isDefault;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public long getStudentId() {
        return studentId;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
