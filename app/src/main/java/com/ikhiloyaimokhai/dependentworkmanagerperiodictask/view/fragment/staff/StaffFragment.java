package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.view.fragment.staff;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.R;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.adapter.StaffAdapter;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.dependency.Injector;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.viewmodel.EmployeeViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffFragment extends Fragment implements StaffAdapter.ListItemClickListener, View.OnClickListener {
    private List<Staff> staff;
    private StaffAdapter staffAdapter;
    private EmployeeViewModel employeeViewModel;

    public StaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        employeeViewModel = ViewModelProviders.of(requireActivity(),
                Injector.provideViewModelFactory(getActivity())).get(EmployeeViewModel.class);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;
        Context context = getContext();
        RecyclerView staffRecycler = view.findViewById(R.id.staffRecycler);
        FloatingActionButton addFab = view.findViewById(R.id.addFab);
        staffRecycler.setHasFixedSize(true);
        staffRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        staff = new ArrayList<>();
        staffAdapter = new StaffAdapter(staff, this);
        staffRecycler.setAdapter(staffAdapter);
        addFab.setOnClickListener(this);
        getStaff();
    }

    private void getStaff() {
        employeeViewModel.getStaff().observe(getViewLifecycleOwner(), data -> {
            staff.clear();
            staff.addAll(data);
            staffAdapter.notifyDataSetChanged();
            Timber.i("Data fetched locally%s", staff.toString());
        });
    }

    @Override
    public void onListItemClick(Staff staff, int adapterPosition) {
        Navigation.findNavController(requireView()).navigate(
                StaffFragmentDirections.actionStaffFragmentToStaffDetailFragment(
                        String.valueOf(staff.getId()),
                        staff.getFirstName(),
                        staff.getLastName(),
                        staff.getLocalId()
                ));
    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(requireView()).navigate(
                StaffFragmentDirections.actionStaffFragmentToAddStaffFragment());
    }
}
