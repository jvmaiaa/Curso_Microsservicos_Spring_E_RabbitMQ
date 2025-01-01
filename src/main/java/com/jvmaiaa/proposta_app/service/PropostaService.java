package com.jvmaiaa.proposta_app.service;

import com.jvmaiaa.proposta_app.dto.PropostaRequestDTO;
import com.jvmaiaa.proposta_app.dto.PropostaResponseDTO;
import com.jvmaiaa.proposta_app.entity.Proposta;
import com.jvmaiaa.proposta_app.mapper.PropostaMapper;
import com.jvmaiaa.proposta_app.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private PropostaRepository propostaRepository;

    private NotificacaoService notificacaoService;

    private String exchange;

    public PropostaService(PropostaRepository propostaRepository,
                           NotificacaoService notificacaoService,
                           @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoService = notificacaoService;
        this.exchange = exchange;
    }

    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO){
        Proposta proposta = PropostaMapper.INSTANCE.converteDtoToProposta(requestDTO);
        propostaRepository.save(proposta);

        PropostaResponseDTO response = PropostaMapper.INSTANCE.converteEntityToDto(proposta);
        notificacaoService.notificar(response, exchange);

        return response;
    }

    public List<PropostaResponseDTO> obterProposta() {
        return PropostaMapper.INSTANCE.converteListEntityToListDto(propostaRepository.findAll());

    }
}
