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

    private NotificacaoRabbitService notificacaoRabbitService;

    private String exchange;

    public PropostaService(PropostaRepository propostaRepository,
                           NotificacaoRabbitService notificacaoRabbitService,
                           @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO){
        Proposta proposta = PropostaMapper.INSTANCE.converteDtoToProposta(requestDTO);
        propostaRepository.save(proposta);

        notificarRabbitMQ(proposta);
        notificacaoRabbitService.notificar(proposta, exchange);

        return PropostaMapper.INSTANCE.converteEntityToDto(proposta);
    }

    private void notificarRabbitMQ(Proposta proposta){
        try {
            notificacaoRabbitService.notificar(proposta, exchange);
        } catch (RuntimeException e) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }

    public List<PropostaResponseDTO> obterProposta() {
        return PropostaMapper.INSTANCE.converteListEntityToListDto(propostaRepository.findAll());

    }
}
