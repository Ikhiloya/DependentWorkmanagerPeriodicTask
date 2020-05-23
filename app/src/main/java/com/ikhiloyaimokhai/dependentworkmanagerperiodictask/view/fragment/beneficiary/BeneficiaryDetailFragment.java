package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.view.fragment.beneficiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeneficiaryDetailFragment extends Fragment {

    public BeneficiaryDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beneficiary_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;
        TextView firstNameTxt = view.findViewById(R.id.firstName);
        TextView lastNameTxt = view.findViewById(R.id.lastName);
        TextView relationshipTxt = view.findViewById(R.id.relationship);

        assert getArguments() != null;
        String blank = "--";
        BeneficiaryDetailFragmentArgs args = BeneficiaryDetailFragmentArgs.fromBundle(getArguments());
        String firstName = args.getFirstName();
        String lastName = args.getLastName();
        String relationship = args.getRelationship();

        //set views
        firstNameTxt.setText(firstName);
        lastNameTxt.setText(lastName);
        relationshipTxt.setText(relationship);
    }

}
