package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.TransfertDesktop;
import com.example.packettracerbase.model.Transfert;

import java.util.List;
import java.util.Optional;

public interface TransfertService {
    List<Transfert> getAllTransferts();
    Optional<Transfert> getTransfertById(Long id);
    Transfert createTransfert(Transfert transfert);
    Transfert updateTransfert(Long id, Transfert transfertDetails);
    void deleteTransfert(Long id);

    List<Transfert> getTransfersByPacketId(Long packetId);

    List<TransfertDesktop> getTransfersDTOByPacketId(Long packetId);

    String getAllTransfertsAsJson();

    TransfertDesktop createTransfert(TransfertDesktop transfertDesktop);
}
