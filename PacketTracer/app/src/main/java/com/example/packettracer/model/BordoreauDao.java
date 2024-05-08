package com.example.packettracer.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.packettracer.model.BordoreauQRDTO;

import java.util.List;

@Dao
public interface BordoreauDao {
    @Query("SELECT * FROM BordoreauQRDTO")
    List<BordoreauQRDTO> getAll();

    @Insert
    void insertAll(BordoreauQRDTO... bordoreaus);

    @Delete
    void delete(BordoreauQRDTO bordoreau);
}
