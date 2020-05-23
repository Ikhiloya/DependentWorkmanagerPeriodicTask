package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.EmployeeApp;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.db.EmployeeDao;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.service.EmployeeService;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.Constant;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.WorkerUtils;

import java.util.List;

import retrofit2.Call;
import timber.log.Timber;

public class BeneficiaryWorker extends WorkerContract<Beneficiary, Beneficiary> {
    private static String TAG = BeneficiaryWorker.class.getSimpleName();
    private EmployeeService employeeService;
    private EmployeeDao employeeDao;

    public BeneficiaryWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        EmployeeApp employeeApp = EmployeeApp.get();
        employeeService = employeeApp.getEmployeeService();
        employeeDao = employeeApp.getEmployeeDao();
    }

    @Override
    protected List<Beneficiary> loadPendingItemsFromDb() {
        Timber.i("loading pending Beneficiaries from db..........");
        return employeeDao.getPendingBeneficiaries();
    }

    @Override
    protected boolean hasParentRelationship(Beneficiary beneficiary) {
        //return true since it is a child Entity
        return true;
    }

    @Override
    protected boolean checkParentStatus(Beneficiary beneficiary) {
        Timber.i("%s: in checkParentStatus.........", TAG);
        Staff staff = employeeDao.findStaffByLocalId(beneficiary.getStaffLocalId());
        return staff.getState().equalsIgnoreCase(Constant.SYNCED);
    }

    @Override
    protected Call<Beneficiary> createCall(Beneficiary beneficiary) {
        Timber.i("%s: creating call...", TAG);
        Timber.i("%s: staff data........%s", TAG, beneficiary.toString());
        return employeeService.createBeneficiary(beneficiary);
    }

    /* updates the local data with the remote ID as well as the state to [SYNCED] **/
    @Override
    protected void saveCallResult(@NonNull Beneficiary responseData, Beneficiary localData) {
        localData.setId(responseData.getId());
        localData.setState(Constant.SYNCED);
        employeeDao.saveBeneficiary(localData);
    }

    @Override
    protected void onPostFailed(Beneficiary beneficiary) {
        WorkerUtils.makeStatusNotification("failed to save " +
                        beneficiary.getFirstName() + " " + beneficiary.getLastName(),
                getApplicationContext());
    }
}
