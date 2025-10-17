package com.mballem.demo_park_api.web.dto.mapper;

import com.mballem.demo_park_api.entity.ClienteVagas;
import com.mballem.demo_park_api.web.dto.ClienteCreateDto;
import com.mballem.demo_park_api.web.dto.ClienteResponseDto;
import com.mballem.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.mballem.demo_park_api.web.dto.EstacionamentoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVagas toClienteVaga(EstacionamentoCreateDto createDto){
        return new ModelMapper().map(createDto, ClienteVagas.class);
    }

    public static EstacionamentoResponseDto toDto(ClienteVagas vagas){
        return new ModelMapper().map(vagas, EstacionamentoResponseDto.class);
    }
}
