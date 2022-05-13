package br.com.andersontres.domain;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CarMapper {

    CarDto carToCarDto(Car car);

    List<CarDto> carToCarDto(List<Car> cars);

    Car carDtoToCar(CarDto dto);

    List<Car> carDtoToCar(List<CarDto> dtos);
}
