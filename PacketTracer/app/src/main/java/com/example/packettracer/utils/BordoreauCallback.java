package com.example.packettracer.utils;

import com.example.packettracer.model.BordoreauQRDTO;

public interface BordoreauCallback {
    void onResponse(BordoreauQRDTO bordoreau,String oldoo);
    void onError(String errorMessage);
}
