package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.R;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.Constant;

import java.util.List;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.ViewHolder> {
    private List<Beneficiary> beneficiaries;
    private ListItemClickListener listItemClickListener;


    public BeneficiaryAdapter(List<Beneficiary> beneficiaries, ListItemClickListener listItemClickListener) {
        this.beneficiaries = beneficiaries;
        this.listItemClickListener = listItemClickListener;
    }

    /**
     * an interface to handle click events on a card
     */
    public interface ListItemClickListener {
        void onListItemClick(Beneficiary beneficiary, int adapterPosition);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.beneficiary_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(beneficiaries.get(position), this.listItemClickListener);
    }


    @Override
    public int getItemCount() {
        return beneficiaries != null ? beneficiaries.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullName;
        private TextView relationship;
        private ImageView stateIcon;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            relationship = itemView.findViewById(R.id.relationship);
            stateIcon = itemView.findViewById(R.id.stateIcon);
        }

        @SuppressLint("SetTextI18n")
        void bind(Beneficiary beneficiary, ListItemClickListener listItemClickListener) {
            fullName.setText(beneficiary.getFirstName().trim() + " " + beneficiary.getLastName().trim());
            relationship.setText(beneficiary.getRelationship());

            if (beneficiary.getState() != null) {
                if (beneficiary.getState().equalsIgnoreCase(Constant.PENDING) || beneficiary.getState().equalsIgnoreCase(Constant.UPDATING)) {
                    stateIcon.setImageResource(R.drawable.ic_offline_pin_pending_24dp);
                } else {
                    stateIcon.setImageResource(R.drawable.ic_offline_pin_active_24dp);
                }
                stateIcon.setVisibility(View.VISIBLE);
            } else {
                stateIcon.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> listItemClickListener.onListItemClick(beneficiary, getAdapterPosition()));
        }
    }
}

