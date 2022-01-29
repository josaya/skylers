package com.example.skylers.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;


/**
 * Created by Programmer Josaya on 4/21/2017.
 */


public class SharedPreference {
    private SharedPreferences app_prefs;
    private Context context;

    @Inject
    public SharedPreference(@ApplicationContext Context context) {
        app_prefs = context.getSharedPreferences(AppUtils.PREF_NAME,
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public Boolean getIsAgentLoggedIn() {
        return app_prefs.getBoolean("is_logged_in", false);
    }
    public void setIsAgentLoggedIn(Boolean loged_in) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean("is_logged_in", loged_in);
        edit.apply();
    }

    public String getSearchCriteria()
    {
        return app_prefs.getString("search_criteria", "");
    }
    public void setSearchCriteria(String s_criteria) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("search_criteria", s_criteria);
        edit.apply();
    }

    public String getAgentId() {
        return app_prefs.getString("agent_id", "");
    }

    public void setAgentId(String agent_id) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("agent_id", agent_id);
        edit.apply();
    }

    public String getUserPic() {
        return app_prefs.getString("user_pic", "");
    }

    public void setUserPic(String userPic) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("user_pic", userPic);
        edit.apply();
    }

    public String getFirstName() {
        return app_prefs.getString("first_name", "");
    }

    public void setFirstName(String fName) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("first_name", fName);
        edit.apply();
    }

    public String getMiddleName() {
        return app_prefs.getString("middle_name", "");
    }

    public void setMiddleName(String mName) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("middle_name", mName);
        edit.apply();
    }

    public String getSurName() {
        return app_prefs.getString("sur_name", "");
    }

    public void setSurName(String sName) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("sur_name", sName);
        edit.apply();
    }

    public String getIdNumber() {
        return app_prefs.getString("id_number", "");
    }

    public void setIdNumber(String idNumber) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("id_number", idNumber);
        edit.apply();
    }

    public String getPhoneNumber() {
        return app_prefs.getString("phone_number", "");
    }

    public void setPhoneNumber(String phoneNumber) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("phone_number", phoneNumber);
        edit.apply();
    }

    public String getKRAPIN() {
        return app_prefs.getString("kra_pin", "");
    }

    public void setKRAPIN(String kraPin) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("kra_pin", kraPin);
        edit.apply();
    }

    public String getEmail() {
        return app_prefs.getString("email", "");
    }

    public void setEmail(String email) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("email", email);
        edit.apply();
    }

    public String getDOB() {
        return app_prefs.getString("dob", "");
    }

    public void setDOB(String dob) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("dob", dob);
        edit.apply();
    }

    public String getGender() {
        return app_prefs.getString("gender", "");
    }

    public void setGender(String gender) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("gender", gender);
        edit.apply();
    }

    public String getAddress() {
        return app_prefs.getString("address", "");
    }

    public void setAddress(String address) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("address", address);
        edit.apply();
    }

    public String getCountry() {
        return app_prefs.getString("country", "");
    }

    public void setCountry(String country) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("country", country);
        edit.apply();
    }

    public String getOccupation() {
        return app_prefs.getString("occupation", "");
    }

    public void setOccupation(String occupation) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("occupation", occupation);
        edit.apply();
    }

    //Next of Kin details
    public String getNKName() {
        return app_prefs.getString("nkName", "");
    }

    public void setNKName(String nk_name) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("nkName", nk_name);
        edit.apply();
    }

    public String getIDNumber() {
        return app_prefs.getString("nkIdNumber", "");
    }

    public void setIDNumber(String nk_id_number) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("nkIdNumber", nk_id_number);
        edit.apply();
    }

    public String getBenevolentName() {
        return app_prefs.getString("benevolent_name", "");
    }

    public void setBenevolentName(String benevolent_name) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("benevolent_name", benevolent_name);
        edit.apply();
    }

    public String getBenevolentID() {
        return app_prefs.getString("benevolent_id", "");
    }

    public void setBenevolentID(String benevolent_id) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("benevolent_id", benevolent_id);
        edit.apply();
    }
}
