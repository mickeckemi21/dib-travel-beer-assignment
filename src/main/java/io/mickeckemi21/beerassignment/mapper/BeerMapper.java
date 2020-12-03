package io.mickeckemi21.beerassignment.mapper;

import io.mickeckemi21.beerassignment.client.PunkApiBeer;
import io.mickeckemi21.beerassignment.dto.BeerDto;
import io.mickeckemi21.beerassignment.model.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    @Mapping(target = "averageTemperature",
            expression = "java(" +
                    "new java.text.DecimalFormat(\"#.00\")" +
                    ".format(beer.getTemperatures().stream()" +
                        ".mapToDouble(temperature -> temperature)" +
                        ".average()" +
                        ".orElseThrow(() -> new RuntimeException(\"Couldn't resolve average temperature\"))))"
    )
    BeerDto beerToBeerDto(Beer beer);

    @Mapping(target = "internalId",
            expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "temperatures",
            expression = "java(punkApiBeer.getMethod().getMashTemp().stream()" +
                    ".map(mashTemp -> mashTemp.getTemp().getValue())" +
                    ".collect(java.util.stream.Collectors.toList()))")
    Beer punkApiBeerToBeer(PunkApiBeer punkApiBeer);

}
