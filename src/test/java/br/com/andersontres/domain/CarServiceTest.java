package br.com.andersontres.domain;

import br.com.andersontres.domain.exception.NotFoundException;
import br.com.andersontres.driverport.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    private static final String CAR_NOT_FOUND_MSG = "Car not found";

    private CarService service;

    @Mock
    private CarRepository repository;

    @Mock
    private CarMapper mapper;

    @BeforeEach
    void setup() {
        service = new CarService(repository, mapper);
    }

    @Test
    void givenAnyCarExists_whenGetAllCars_thenReturnCarList() {
        var car = getCar(null);
        when(repository.listAll()).thenReturn(List.of(car));
        when(mapper.carToCarDto(any(List.class))).thenReturn(List.of(getCarDto(car.getId())));

        var response = service.getAllCars();

        assertTrue(response.isPresent());
        assertEquals(car.getId(), response.get().get(0).getId());
        assertEquals(car.getName(), response.get().get(0).getName());
    }

    @Test
    void givenNoCarExists_whenGetAllCars_thenReturnEmpty() {
        when(repository.listAll()).thenReturn(Collections.emptyList());

        var response = service.getAllCars();

        assertTrue(response.isEmpty());
    }

    @Test
    void givenCarIdExists_whenGetCar_thenReturnCar() {
        var id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(getCar(id)));
        when(mapper.carToCarDto(any(Car.class))).thenReturn(getCarDto(id));

        var response = service.getCar(id.toString());

        assertTrue(response.isPresent());
        assertEquals(id, response.get().getId());
    }

    @Test
    void givenName_whenCreateCar_thenReturnUUID() {
        doNothing().when(repository).persist(any(Car.class));

        var response = service.createCar("Golf");

        // should return null once the repository is mocked and the UUID is created by the EntityManager
        assertNull(response);
    }

    @Test
    void givenCarExists_whenUpdateCar_thenReturnCarUpdated() throws NotFoundException {
        var id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(getCar(id)));
        doNothing().when(repository).persist(any(Car.class));
        when(mapper.carToCarDto(any(Car.class))).thenReturn(getCarDto(id));

        var response = service.updateCar(id.toString(), "Golf");

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
    }

    @Test
    void givenCarDoesNotExists_whenUpdateCar_thenThrowsNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> { service.updateCar(UUID.randomUUID().toString(), "Golf"); },
                CAR_NOT_FOUND_MSG);
    }

    @Test
    void givenCarExists_whenDeleteCar_thenDoesNotThrowException() {
        var id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(getCar(id)));
        doNothing().when(repository).delete(any(Car.class));

        assertDoesNotThrow(() -> service.deleteCar(id.toString()));
    }

    @Test
    void givenCarDoesNotExists_whenDeleteCar_thenThrowsNotFoundException() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> { service.deleteCar(UUID.randomUUID().toString()); },
                CAR_NOT_FOUND_MSG);
    }

    private Car getCar(UUID id) {
        var car = new Car();
        car.setId(Objects.nonNull(id) ? id : UUID.randomUUID());
        car.setName("Golf");

        return car;
    }

    private CarDto getCarDto(UUID id) {
        var carDto = new CarDto();
        carDto.setId(Objects.nonNull(id) ? id : UUID.randomUUID());
        carDto.setName("Golf");

        return carDto;
    }
}
