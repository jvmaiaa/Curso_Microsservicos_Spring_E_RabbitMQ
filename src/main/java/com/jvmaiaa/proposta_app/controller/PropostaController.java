package com.jvmaiaa.proposta_app.controller;

import com.jvmaiaa.proposta_app.dto.PropostaRequestDTO;
import com.jvmaiaa.proposta_app.dto.PropostaResponseDTO;
import com.jvmaiaa.proposta_app.service.PropostaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDTO> criar(@RequestBody PropostaRequestDTO requestDTO) {
        PropostaResponseDTO response = propostaService.criar(requestDTO);
        return null;
    }
}
