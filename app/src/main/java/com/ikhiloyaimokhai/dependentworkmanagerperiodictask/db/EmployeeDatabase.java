package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.Constant;

@Database(entities = {Staff.class, Beneficiary.class},
        version = 1, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {
    private static volatile EmployeeDatabase INSTANCE;

    public abstract EmployeeDao employeeDao();

    public static EmployeeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EmployeeDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EmployeeDatabase.class, Constant.EMPLOYEE_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
