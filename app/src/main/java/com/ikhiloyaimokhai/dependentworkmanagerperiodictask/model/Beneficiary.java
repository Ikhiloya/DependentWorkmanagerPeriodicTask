package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Beneficiary {
    @PrimaryKey(autoGenerate = true)
    private Integer localId;

    private Integer staffLocalId;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("staffId")
    @Expose
    private Long staffId;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("relationship")
    @Expose
    private String relationship;

    @SerializedName("state")
    @Expose
    private String state;

    public Beneficiary() {
    }

    @Ignore
    public Beneficiary(Integer staffLocalId, String firstName, String lastName, String relationship, String state) {
        this.staffLocalId = staffLocalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.relationship = relationship;
        this.state = state;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }


    public Integer getStaffLocalId() {
        return staffLocalId;
    }

    public void setStaffLocalId(Integer staffLocalId) {
        this.staffLocalId = staffLocalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "roomId=" + localId +
                ", staffLocalId=" + staffLocalId +
                ", id=" + id +
                ", staffId=" + staffId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", relationship='" + relationship + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
