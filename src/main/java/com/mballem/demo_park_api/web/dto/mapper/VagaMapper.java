package com.mballem.demo_park_api.web.dto.mapper;

import com.mballem.demo_park_api.entity.Vaga;
import com.mballem.demo_park_api.web.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaResponseDto vagaResponseDto){
        return new ModelMapper().map(vagaResponseDto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga){
        return new ModelMapper().map(vaga, VagaResponseDto.class);



    }
}
