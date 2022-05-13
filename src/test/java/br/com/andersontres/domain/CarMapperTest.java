package br.com.andersontres.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
class CarMapperTest {

    @Inject
    private CarMapper mapper;

    @Test
    void givenCar_whenCarToCarDto_thenReturnCarDto() {
        var car = getCar();

        var result = mapper.carToCarDto(car);

        assertEquals(car.getId(), result.getId());
        assertEquals(car.getName(), result.getName());
    }

    @Test
    void givenCarNull_whenCarToCarDto_thenReturnNull() {
        Car car = null;

        var result = mapper.carToCarDto(car);

        assertNull(result);
    }

    @Test
    void givenCarList_whenCarToCarDto_thenReturnCarDtoList() {
        var car = getCar();
        var carList = List.of(car);

        var result = mapper.carToCarDto(carList);

        assertEquals(car.getId(), result.get(0).getId());
        assertEquals(car.getName(), result.get(0).getName());
        assertEquals(carList.size(), result.size());
    }

    @Test
    void givenCarListNull_whenCarToCarDto_thenReturnNull() {
        List<Car> carList = null;

        var result = mapper.carToCarDto(carList);

        assertNull(result);
    }

    @Test
    void givenCarDto_whenCarDtoToCar_thenReturnCar() {
        var carDto = getCarDto();

        var result = mapper.carDtoToCar(carDto);

        assertEquals(carDto.getId(), result.getId());
        assertEquals(carDto.getName(), result.getName());
    }

    @Test
    void givenCarDtoNull_whenCarDtoToCar_thenReturnNull() {
        CarDto carDto = null;

        var result = mapper.carDtoToCar(carDto);

        assertNull(result);
    }

    @Test
    void givenCarDtoList_whenCarDtoToCar_thenReturnCarList() {
        var carDto = getCarDto();
        var carDtoList = List.of(carDto);

        var result = mapper.carDtoToCar(carDtoList);

        assertEquals(carDto.getId(), result.get(0).getId());
        assertEquals(carDto.getName(), result.get(0).getName());
        assertEquals(carDtoList.size(), result.size());
    }

    @Test
    void givenCarDtoListNull_whenCarDtoToCar_thenReturnCarList() {
        List<CarDto> carDtoList = null;

        var result = mapper.carDtoToCar(carDtoList);

        assertNull(result);
    }

    private Car getCar() {
        return new Car(UUID.randomUUID(), "Golf");
    }

    private CarDto getCarDto() {
        return new CarDto(UUID.randomUUID(), "Golf");
    }
}
