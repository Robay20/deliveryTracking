package com.example.packettracer.utils;

import com.example.packettracer.model.BordoreauQRDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BordoreauApi {

    @PUT("api/bordoreaux/{id}/mobile")
    Call<BordoreauQRDTO> updateBordoreau(@Path("id") Long id, @Body BordoreauQRDTO bordoreau);
}
