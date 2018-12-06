package com.contactwithsmsdemo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.contactwithsmsdemo.model.Sms;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleRecord(Sms... atCheckinData);

    @Update
    void updateOnlySingleRecord(Sms... atCheckinData);

    @Query("SELECT COUNT(*) FROM Sms")
    int getCheckInDataCount();

    @Query("SELECT * FROM Sms")
    List<Sms> getCheckInDataList();

    @Query("DELETE FROM Sms")
    void deleteCheckInData();

}
