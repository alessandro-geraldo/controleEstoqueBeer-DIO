package com.geraldo.beercontrol.mapper;

import com.geraldo.beercontrol.DTO.BeerDTO;
import com.geraldo.beercontrol.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer toModel(BeerDTO beerDTO);
    BeerDTO toDTO(Beer beer);
}
