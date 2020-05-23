package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.factory;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.repository.EmployeeRepository;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.viewmodel.EmployeeViewModel;


public class EmployeeViewModelFactory implements ViewModelProvider.Factory {
    private final EmployeeRepository employeeRepository;

    public EmployeeViewModelFactory(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(EmployeeViewModel.class))
            return (T) new EmployeeViewModel(employeeRepository);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}

