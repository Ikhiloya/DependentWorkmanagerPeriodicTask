package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.db.EmployeeDao;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.db.EmployeeDatabase;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;

import java.util.List;

public class EmployeeRepository {
    private static String LOG_TAG = EmployeeRepository.class.getSimpleName();
    private final android.app.Application application;
    private static EmployeeDao employeeDao;


    public EmployeeRepository(Application application) {
        this.application = application;
        EmployeeDatabase db = EmployeeDatabase.getDatabase(application);
        employeeDao = db.employeeDao();
    }

    public LiveData<List<Staff>> getStaff() {
        return employeeDao.getStaff();
    }

    public void saveStaff(Staff staff) {
        employeeDao.saveStaff(staff);
    }

    public LiveData<List<Beneficiary>> getBeneficiariesByStaff(int staffRoomId) {
        return employeeDao.getBeneficiariesByStaff(staffRoomId);
    }

    public void saveBeneficiary(Beneficiary beneficiary) {
        employeeDao.saveBeneficiary(beneficiary);
    }

    public Application getApplication() {
        return application;
    }

}
