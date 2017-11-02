package me.dbecaj.friurnik.data.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import me.dbecaj.friurnik.R;
import me.dbecaj.friurnik.data.database.FRIUrnikDatabase;
import me.dbecaj.friurnik.data.system.ResourceProvider;

/**
 * Created by Dominik on 20-Oct-17.
 */

@Table(database = FRIUrnikDatabase.class)
public class StudentModel extends BaseModel{

    @Column
    @PrimaryKey
    private long studentId;

    @Column
    @NotNull
    private boolean defaultStudent;

    @Column
    private String nickname;

    protected StudentModel() {}

    public StudentModel(long studentId, boolean defaultStudent) {
        this.studentId = studentId;
        this.defaultStudent = defaultStudent;

        if(defaultStudent) {
            nickname = ResourceProvider.getString(R.string.me);
        }
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public void setDefault(boolean defaultStudent) {
        this.defaultStudent = defaultStudent;
    }

    public long getStudentId() {
        return studentId;
    }

    public boolean isDefault() {
        return defaultStudent;
    }

    public boolean isDefaultStudent() {
        return defaultStudent;
    }

    public void setDefaultStudent(boolean defaultStudent) {
        this.defaultStudent = defaultStudent;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean hasNickname() {
        return nickname != null;
    }
}
