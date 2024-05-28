package com.example.packettracer.utils;

import com.example.packettracer.model.BordoreauQRDTO;
import com.example.packettracer.model.PacketStatus;
import com.example.packettracer.model.TransfertRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BordoreauApi {

    @PUT("api/bordoreaux/{id}/mobile")
    Call<BordoreauQRDTO> updateBordoreau(@Path("id") Long id, @Body BordoreauQRDTO bordoreau);

    @POST("/api/transferts/transferts")
    Call<Void> createTransfert(@Body TransfertRequest transfertRequest);

    @PUT("api/bordoreaux/{id}/mobile/transit")
    Call<Void> updateBordoreauStringLivreur(@Path("id") Long id, @Body String newStringLivreur);

    @PUT("api/packets/{id}/status")
    Call<Void> updatePacketStatus(@Path("id") Long packetId, @Body PacketStatus status);

}
