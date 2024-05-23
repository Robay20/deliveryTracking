package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Sender;

import java.util.List;
import java.util.Optional;

public interface SenderService {
    List<Sender> getAllSenders();
    Optional<Sender> getSenderById(String id);
    Sender createSender(Sender sender);
    Sender updateSender(String id, Sender senderDetails);
    void deleteSender(String id);
}
