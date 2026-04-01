package com.example.demo.controller;

import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    // ✅ GET ALL ROOMS
    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // ✅ ADD ROOM (ADMIN)
    @PostMapping
    public Room addRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }
}