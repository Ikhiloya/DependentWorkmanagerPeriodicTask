package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.EmployeeApp;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.db.EmployeeDao;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.service.EmployeeService;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.Constant;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.WorkerUtils;

import java.util.List;

import retrofit2.Call;
import timber.log.Timber;

public class StaffWorker extends WorkerContract<Staff, Staff> {
    private static String TAG = StaffWorker.class.getSimpleName();
    private EmployeeService employeeService;
    private EmployeeDao employeeDao;

    public StaffWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        EmployeeApp employeeApp = EmployeeApp.get();
        employeeService = employeeApp.getEmployeeService();
        employeeDao = employeeApp.getEmployeeDao();
    }


    @Override
    protected List<Staff> loadPendingItemsFromDb() {
        Timber.i("loading pending Staff from db..........");
        return employeeDao.getPendingStaff();
    }

    @Override
    protected boolean hasParentRelationship(Staff staff) {
        //return false since this is the parent Entity
        return false;
    }

    @Override
    protected boolean checkParentStatus(Staff staff) {
        //return false since this is the parent Entity
        return false;
    }

    @Override
    protected Call<Staff> createCall(Staff staff) {
        Timber.i("%s: creating call...", TAG);
        Timber.i("%s: staff data........%s", TAG, staff.toString());
        return employeeService.createStaff(staff);
    }

    /* updates the local data with the remote ID as well as the state to [SYNCED] **/
    @Override
    protected void saveCallResult(@NonNull Staff responseData, Staff localData) {
        localData.setId(responseData.getId());
        localData.setState(Constant.SYNCED);
        employeeDao.saveStaff(localData);
    }


    @Override
    protected void onPostFailed(Staff staff) {
        WorkerUtils.makeStatusNotification("failed to save " +
                        staff.getFirstName() + " " + staff.getLastName(),
                getApplicationContext());
    }
}
