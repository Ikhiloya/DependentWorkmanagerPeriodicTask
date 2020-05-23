package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.view.fragment.beneficiary;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.R;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.dependency.Injector;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.Constant;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.Helper;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.viewmodel.EmployeeViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBeneficiaryFragment extends Fragment implements View.OnClickListener {
    private TextInputLayout firstNameInput;
    private TextInputLayout lastNameInput;
    private Spinner relationshipSpinner;
    private EmployeeViewModel employeeViewModel;
    private Integer staffLocalId;

    public AddBeneficiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_beneficiary, container, false);
        employeeViewModel = ViewModelProviders.of(requireActivity(),
                Injector.provideViewModelFactory(getActivity())).get(EmployeeViewModel.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;
        assert getArguments() != null;
        AddBeneficiaryFragmentArgs args = AddBeneficiaryFragmentArgs.fromBundle(getArguments());
        staffLocalId = args.getStaffLocalId();
        firstNameInput = view.findViewById(R.id.firstName);
        lastNameInput = view.findViewById(R.id.lastName);
        relationshipSpinner = view.findViewById(R.id.relationshipSpinner);
        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            validateFields();
        }
    }

    private void validateFields() {
        String firstName = Helper.getString(firstNameInput);
        String lastName = Helper.getString(lastNameInput);
        String relationship = (String) relationshipSpinner.getSelectedItem();
        if (TextUtils.isEmpty(firstName)) {
            Objects.requireNonNull(firstNameInput.getEditText()).setError(getResources().getString(R.string.first_name_required));
        } else if (TextUtils.isEmpty(lastName)) {
            Objects.requireNonNull(lastNameInput.getEditText()).setError(getResources().getString(R.string.last_name_required));
        } else {
            saveBeneficiary(new Beneficiary(staffLocalId, firstName, lastName, relationship, Constant.PENDING));
        }
    }

    private void saveBeneficiary(Beneficiary beneficiary) {
        Injector.provideExecutors().diskIO().execute(() -> employeeViewModel.saveBeneficiary(beneficiary));
        Navigation.findNavController(requireView()).navigateUp();
    }
}
