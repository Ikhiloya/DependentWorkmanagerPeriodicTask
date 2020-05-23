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
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.Constant;

import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    private List<Staff> staff;
    private ListItemClickListener listItemClickListener;

    public StaffAdapter(List<Staff> staff, ListItemClickListener listItemClickListener) {
        this.staff = staff;
        this.listItemClickListener = listItemClickListener;
    }

    /**
     * an interface to handle click events on a card
     */
    public interface ListItemClickListener {
        void onListItemClick(Staff staff, int adapterPosition);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.staff_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(staff.get(position), this.listItemClickListener);
    }

    @Override
    public int getItemCount() {
        return staff != null ? staff.size() : 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullName;
        private TextView staffId;
        private ImageView stateIcon;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            staffId = itemView.findViewById(R.id.staffId);
            stateIcon = itemView.findViewById(R.id.stateIcon);
        }


        @SuppressLint("SetTextI18n")
        void bind(Staff staff, ListItemClickListener listItemClickListener) {
            fullName.setText(staff.getFirstName().trim() + " " + staff.getLastName().trim());
            String id = staff.getId() == null ? "--" : String.valueOf(staff.getId());
            staffId.setText(id);

            if (staff.getState() != null) {
                if (staff.getState().equalsIgnoreCase(Constant.PENDING) || staff.getState().equalsIgnoreCase(Constant.UPDATING)) {
                    stateIcon.setImageResource(R.drawable.ic_offline_pin_pending_24dp);
                } else {
                    stateIcon.setImageResource(R.drawable.ic_offline_pin_active_24dp);
                }
                stateIcon.setVisibility(View.VISIBLE);
            } else {
                stateIcon.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> listItemClickListener.onListItemClick(staff, getAdapterPosition()));
        }
    }
}

