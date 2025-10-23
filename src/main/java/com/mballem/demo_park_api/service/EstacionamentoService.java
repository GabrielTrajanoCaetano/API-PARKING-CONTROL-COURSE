package com.mballem.demo_park_api.service;

import com.mballem.demo_park_api.entity.Cliente;
import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.entity.Vaga;
import com.mballem.demo_park_api.repository.ClienteVagaRepository;
import com.mballem.demo_park_api.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;
    private final ClienteVagaRepository vagaRepository;

    @Transactional
    public ClienteVagas checkIn(ClienteVagas clienteVagas){
        Cliente cliente = clienteService.buscarPorCpf(clienteVagas.getCliente().getCpf());
        clienteVagas.setCliente(cliente);

        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        clienteVagas.setVaga(vaga);

        clienteVagas.setDataEntrada(LocalDateTime.now());

        clienteVagas.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.salvar(clienteVagas);
    }

    @Transactional
    public ClienteVagas checkOut(String recibo) {
        ClienteVagas clienteVagas = clienteVagaService.buscarVagaPeloRecibo(recibo);

        LocalDateTime dataSaida = LocalDateTime.now();

        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVagas.getDataEntrada(), dataSaida);
        clienteVagas.setValor(valor);

        long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVagas.getCliente().getCpf());

        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes);
        clienteVagas.setDesconto(desconto);

        clienteVagas.setDataSaida(dataSaida);
        clienteVagas.getVaga().setStatus(Vaga.StatusVaga.LIVRE);

       return clienteVagaService.salvar(clienteVagas);
    }
}
