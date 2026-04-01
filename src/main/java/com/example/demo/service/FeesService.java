package com.example.demo.service;

import com.example.demo.model.Fees;
import com.example.demo.model.FeesRequest;
import com.example.demo.model.Resident;
import com.example.demo.repository.FeesRepository;
import com.example.demo.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeesService {

    private final FeesRepository feesRepository;
    private final ResidentRepository residentRepository;

    public FeesService(FeesRepository feesRepository, ResidentRepository residentRepository) {
        this.feesRepository = feesRepository;
        this.residentRepository = residentRepository;
    }

    // ✅ ADD FEE
    public Fees addFee(FeesRequest request) {

        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new RuntimeException("Resident not found"));

        Fees fee = new Fees();
        fee.setAmount(request.getAmount());
        fee.setResident(resident);
        fee.setStatus("PENDING");

        // 🔥 OPTIONAL (GOOD PRACTICE)
        fee.setPaidDate(null);

        return feesRepository.save(fee);
    }

    // ✅ GET ALL FEES
    public List<Fees> getAllFees() {
        return feesRepository.findAll();
    }

    // ✅ PAY FEE
    public Fees payFee(Long id) {

        Fees fee = feesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fee not found"));

        // 🔥 VALIDATION (IMPORTANT FOR VIVA)
        if ("PAID".equalsIgnoreCase(fee.getStatus())) {
            throw new RuntimeException("Fee already paid");
        }

        fee.setStatus("PAID");
        fee.setPaidDate(LocalDate.now()); // ✅ FIXED

        return feesRepository.save(fee);
    }
}