package com.example.demo.controller;

import com.example.demo.model.Resident;
import com.example.demo.model.Room;
import com.example.demo.repository.ResidentRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/residents")
@CrossOrigin
public class ResidentController {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private RoomRepository roomRepository;

    // ✅ GET ALL RESIDENTS
    @GetMapping
    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    // ✅ ADD RESIDENT WITH ROOM
    @PostMapping
    public Resident addResident(@RequestBody Resident resident) {

        // 🔥 Fetch room from DB
        Long roomId = resident.getRoom().getRoomId();
        Room room = roomRepository.findById(roomId).orElse(null);

        resident.setRoom(room);

        return residentRepository.save(resident);
    }
}