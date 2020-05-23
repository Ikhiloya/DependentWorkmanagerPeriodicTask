package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.view.fragment.staff;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.R;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffDetailFragment extends Fragment implements View.OnClickListener {
    private Integer staffLocalId;
    private String fullName;

    public StaffDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff_detail, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;
        TextView staffIdTxt = view.findViewById(R.id.staffId);
        TextView firstNameTxt = view.findViewById(R.id.firstName);
        TextView lastNameTxt = view.findViewById(R.id.lastName);
        FloatingActionButton addFab = view.findViewById(R.id.addFab);

        assert getArguments() != null;
        String blank = "--";
        StaffDetailFragmentArgs args = StaffDetailFragmentArgs.fromBundle(getArguments());
        Timber.i(" args.getStaffID() %s", args.getStaffID());
        String staffId = (args.getStaffID() == null || args.getStaffID().equals("null")) ? blank : args.getStaffID();
        String firstName = args.getFirstName();
        String lastName = args.getLastName();
        fullName = firstName + " " + lastName;
        staffLocalId = args.getStaffLocalId();

        //set views
        staffIdTxt.setText(staffId);
        firstNameTxt.setText(firstName);
        lastNameTxt.setText(lastName);
        addFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(requireView()).navigate(
                StaffDetailFragmentDirections
                        .actionStaffDetailFragmentToBeneficiaryFragment(staffLocalId, fullName));
    }
}
