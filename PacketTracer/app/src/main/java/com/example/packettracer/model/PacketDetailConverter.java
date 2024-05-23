package com.example.packettracer.model;

import androidx.room.TypeConverter;

import com.example.packettracer.model.PacketDetailDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PacketDetailConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<PacketDetailDTO> stringToPacketDetailsList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<PacketDetailDTO>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String packetDetailsListToString(List<PacketDetailDTO> someObjects) {
        return gson.toJson(someObjects);
    }
}
