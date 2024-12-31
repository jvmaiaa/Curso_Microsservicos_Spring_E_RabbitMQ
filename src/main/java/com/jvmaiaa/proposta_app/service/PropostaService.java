package com.jvmaiaa.proposta_app.service;

import com.jvmaiaa.proposta_app.dto.PropostaRequestDTO;
import com.jvmaiaa.proposta_app.dto.PropostaResponseDTO;
import com.jvmaiaa.proposta_app.entity.Proposta;
import com.jvmaiaa.proposta_app.mapper.PropostaMapper;
import com.jvmaiaa.proposta_app.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PropostaService {

    private PropostaRepository propostaRepository;

    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO){
        Proposta proposta = PropostaMapper.INSTANCE.converteDtoToProposta(requestDTO);
        propostaRepository.save(proposta);
        PropostaResponseDTO responseDTO = PropostaMapper.INSTANCE.converteEntityToDto(proposta);
        return responseDTO;
    }

    public List<PropostaResponseDTO> obterProposta() {
        return PropostaMapper.INSTANCE.converteListEntityToListDto(propostaRepository.findAll());

    }
}
