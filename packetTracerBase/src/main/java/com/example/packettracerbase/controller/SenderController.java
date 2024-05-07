package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.AuthenticationRequest;
import com.example.packettracerbase.model.Sender;
import com.example.packettracerbase.service.AuthenticationService;
import com.example.packettracerbase.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/senders")
public class SenderController {

    private final SenderService senderService;

    private final AuthenticationService authenticationService;


    @Autowired
    public SenderController(SenderService senderService,AuthenticationService authenticationService) {
        this.senderService = senderService;
        this.authenticationService = authenticationService;

    }

    @GetMapping
    public ResponseEntity<List<Sender>> getAllSenders() {
        List<Sender> senders = senderService.getAllSenders();
        return new ResponseEntity<>(senders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sender> getSenderById(@PathVariable String id) {
        Sender sender = senderService.getSenderById(id)
                .orElseThrow(() -> new RuntimeException("Sender not found with id: " + id));
        return new ResponseEntity<>(sender, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sender> createSender(@RequestBody Sender sender) {
        Sender createdSender = senderService.createSender(sender);
        return new ResponseEntity<>(createdSender, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sender> updateSender(@PathVariable String id, @RequestBody Sender senderDetails) {
        Sender updatedSender = senderService.updateSender(id, senderDetails);
        return new ResponseEntity<>(updatedSender, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSender(@PathVariable String id) {
        senderService.deleteSender(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginSender(@RequestBody AuthenticationRequest request) {
        Optional<String> cinSender = authenticationService.authenticateSender(request.getUsername(), request.getPassword());
        if (cinSender.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Sender login successful");
            response.put("cinSender", cinSender.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sender login failed", HttpStatus.UNAUTHORIZED);
        }
    }

}
