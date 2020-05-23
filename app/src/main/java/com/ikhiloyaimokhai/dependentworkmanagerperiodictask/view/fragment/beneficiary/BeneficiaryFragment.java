package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.view.fragment.beneficiary;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.R;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.adapter.BeneficiaryAdapter;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.dependency.Injector;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.viewmodel.EmployeeViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeneficiaryFragment extends Fragment implements View.OnClickListener, BeneficiaryAdapter.ListItemClickListener {
    private List<Beneficiary> beneficiaries;
    private BeneficiaryAdapter beneficiaryAdapter;
    private EmployeeViewModel employeeViewModel;
    private Integer staffLocalId;

    public BeneficiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beneficiary, container, false);
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
        Context context = getContext();
        RecyclerView beneficiaryRecycler = view.findViewById(R.id.beneficiaryRecycler);
        FloatingActionButton addFab = view.findViewById(R.id.addFab);
        TextView fullNameTxt = view.findViewById(R.id.fullName);
        BeneficiaryFragmentArgs args = BeneficiaryFragmentArgs.fromBundle(getArguments());
        String fullName = args.getFullName();
        staffLocalId = args.getStaffLocalId();

        fullNameTxt.setText(fullName);
        beneficiaryRecycler.setHasFixedSize(true);
        beneficiaryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        beneficiaries = new ArrayList<>();
        beneficiaryAdapter = new BeneficiaryAdapter(beneficiaries, this);
        beneficiaryRecycler.setAdapter(beneficiaryAdapter);
        addFab.setOnClickListener(this);
        getBeneficiaries(staffLocalId);
    }

    private void getBeneficiaries(int staffLocalId) {
        employeeViewModel.getBeneficiariesByStaff(staffLocalId).observe(getViewLifecycleOwner(), data -> {
            beneficiaries.clear();
            beneficiaries.addAll(data);
            beneficiaryAdapter.notifyDataSetChanged();
            Timber.i("Data fetched locally %s", beneficiaries.toString());
        });
    }

    @Override
    public void onListItemClick(Beneficiary beneficiary, int adapterPosition) {
        Navigation.findNavController(requireView()).navigate(
                BeneficiaryFragmentDirections.actionBeneficiaryFragmentToBeneficiaryDetailFragment(
                        beneficiary.getFirstName(),
                        beneficiary.getLastName(),
                        beneficiary.getRelationship())
        );
    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(requireView()).navigate(
                BeneficiaryFragmentDirections.actionBeneficiaryFragmentToAddBeneficiaryFragment(staffLocalId));
    }
}
