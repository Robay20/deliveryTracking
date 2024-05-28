package com.example.packettracer.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.packettracer.model.BordoreauQRDTO;

import java.util.List;
@Dao
public interface BordoreauDao {

    @Query("SELECT * FROM BordoreauQRDTO")
    List<BordoreauQRDTO> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BordoreauQRDTO bordoreau);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BordoreauQRDTO> bordereaux);

    @Delete
    void delete(BordoreauQRDTO bordoreau);

    @Query("DELETE FROM BordoreauQRDTO")
    void deleteAllBordoreaux();

}

