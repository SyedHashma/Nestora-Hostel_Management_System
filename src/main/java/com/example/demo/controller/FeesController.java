package com.example.demo.controller;

import com.example.demo.model.Fees;
import com.example.demo.model.FeesRequest;
import com.example.demo.service.FeesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fees")
@CrossOrigin
public class FeesController {

    private final FeesService feesService;

    public FeesController(FeesService feesService) {
        this.feesService = feesService;
    }

    // ✅ ADD FEE
    @PostMapping
    public Fees addFee(@RequestBody FeesRequest request) {
        return feesService.addFee(request);
    }

    // ✅ GET ALL FEES
    @GetMapping
    public List<Fees> getAllFees() {
        return feesService.getAllFees();
    }

    // 🔥 ADD THIS (VERY IMPORTANT)
    @PutMapping("/pay/{id}")
    public Fees payFee(@PathVariable Long id) {
        return feesService.payFee(id);
    }
}