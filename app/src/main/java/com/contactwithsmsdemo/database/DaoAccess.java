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
    void insertOnlySingleRecord(Sms... smsData);

    @Update
    void updateOnlySingleRecord(Sms... smsData);

    @Query("SELECT COUNT(*) FROM SMSDbModel")
    int getSendSmsDataCount();

    @Query("SELECT * FROM SMSDbModel ORDER BY DateTime DESC")
    List<Sms> getSmsDataList();

    @Query("DELETE FROM SMSDbModel")
    void deleteSmsData();

}
