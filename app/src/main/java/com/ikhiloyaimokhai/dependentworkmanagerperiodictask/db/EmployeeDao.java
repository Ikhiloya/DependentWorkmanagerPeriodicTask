package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model.Staff;

import java.net.BindException;
import java.util.List;

@Dao
public interface EmployeeDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveStaff(Staff staff);

    @Transaction
    @Query("SELECT *  FROM staff where localId=:localId")
    Staff findStaffByLocalId(int localId);


    @Transaction
    @Query("SELECT * from staff where state= 'pending'")
    List<Staff> getPendingStaff();

    @Transaction
    @Query("SELECT * from staff")
    LiveData<List<Staff>> getStaff();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveBeneficiary(Beneficiary beneficiary);

    @Transaction
    @Query("SELECT *  FROM beneficiary where localId=:localId")
    Beneficiary findBeneficiaryByRLocalId(int localId);

    @Transaction
    @Query("SELECT *  FROM beneficiary where  staffLocalId=:staffLocalId")
    Beneficiary findBeneficiaryByStaffLocalId(int staffLocalId);

    @Transaction
    @Query("SELECT * from beneficiary where state= 'pending'")
    List<Beneficiary> getPendingBeneficiaries();

    @Transaction
    @Query("SELECT * from beneficiary where staffLocalId=:staffLocalId")
    LiveData<List<Beneficiary>> getBeneficiariesByStaff(int staffLocalId);
}
