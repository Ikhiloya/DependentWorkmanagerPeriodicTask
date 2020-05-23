package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.repository.EmployeeRepository;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
    private EmployeeRepository employeeRepository;


    public EmployeeViewModel(EmployeeRepository employeeRepository) {
        super(employeeRepository.getApplication());
        this.employeeRepository = employeeRepository;
    }

    public void saveStaff(Staff staff) {
        employeeRepository.saveStaff(staff);
    }

    public LiveData<List<Staff>> getStaff() {
        return employeeRepository.getStaff();
    }

    public void saveBeneficiary(Beneficiary beneficiary) {
        employeeRepository.saveBeneficiary(beneficiary);
    }

    public LiveData<List<Beneficiary>> getBeneficiariesByStaff(int staffRoomId) {
        return employeeRepository.getBeneficiariesByStaff(staffRoomId);
    }
}
