package com.millie.financemanager.controller;

import com.millie.financemanager.dto.InstallmentRequestDto;
import com.millie.financemanager.dto.InstallmentResponseDto;
import com.millie.financemanager.entity.Installment;
import com.millie.financemanager.service.InstallmentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/installment")
public class InstallmentController {

    private final InstallmentService installmentService;
    private final ModelMapper modelMapper;

    public InstallmentController(InstallmentService installmentService, ModelMapper modelMapper) {
        this.installmentService = installmentService;
        this.modelMapper = modelMapper;
    }

    @PatchMapping("/{installmentId}")
    public ResponseEntity<InstallmentResponseDto> updateInstallment(@PathVariable Long installmentId, @RequestBody @Valid InstallmentRequestDto installment) {
        InstallmentResponseDto updatedInstallment = convertToDto(installmentService.updateInstallmentStatus(installmentId, installment));
        return ResponseEntity.status(HttpStatus.OK).body(updatedInstallment);
    }

    private InstallmentResponseDto convertToDto(Installment installment) {
        return modelMapper.map(installment, InstallmentResponseDto.class);
    }
}
