package com.example.packettracerbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PacketTracerBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PacketTracerBaseApplication.class, args);
    }

}
