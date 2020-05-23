package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.service;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.BuildConfig;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmployeeService {
    @POST(BuildConfig.STAFF)
    Call<Staff> createStaff(@Body Staff staff);

    @POST(BuildConfig.STAFF)
    Call<List<Staff>> getStaffMembers();

    @POST(BuildConfig.BENEFICIARY)
    Call<Beneficiary> createBeneficiary(@Body Beneficiary beneficiary);

    @POST(BuildConfig.BENEFICIARY)
    Call<List<Beneficiary>> getBeneficiaries();
}
