package br.com.andersontres.drivenport;

import br.com.andersontres.domain.Car;
import br.com.andersontres.domain.CarDto;
import br.com.andersontres.domain.CarService;
import br.com.andersontres.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.wildfly.common.Assert.assertFalse;
import static org.wildfly.common.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CarResourceTest {

    private CarResource resource;

    @Mock
    private CarService service;

    @BeforeEach
    void setup() {
        resource = new CarResource(service);
    }

    @Test
    void givenAnyCarExists_whenGetAllCars_thenReturnStatus200() {
        when(service.getAllCars()).thenReturn(Optional.of(List.of(getCarDto(null))));

        var response = resource.getAllCars();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.hasEntity());
    }

    @Test
    void givenNoCarExists_whenGetAllCars_thenReturnStatus204() {
        when(service.getAllCars()).thenReturn(Optional.empty());

        var response = resource.getAllCars();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertFalse(response.hasEntity());
    }

    @Test
    void givenCarExists_whenGetCar_thenReturnStatus200() {
        when(service.getCar(anyString())).thenReturn(Optional.of(getCarDto(null)));

        var response = resource.getCar(UUID.randomUUID().toString());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.hasEntity());
    }

    @Test
    void givenCarDoesNotExists_whenGetCar_thenReturnStatus404() {
        when(service.getCar(anyString())).thenReturn(Optional.empty());

        var response = resource.getCar(UUID.randomUUID().toString());

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertFalse(response.hasEntity());
    }

    @Test
    void givenName_whenCreateCar_thenReturnStatus201() {
        var request = new CarRequest();
        request.setName("Golf");
        var id = UUID.randomUUID();
        when(service.createCar(anyString())).thenReturn(id);

        var response = resource.createCar(request);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertTrue(response.getLocation().toString().contains(id.toString()));
    }

    @Test
    void givenCarExists_whenUpdateCar_thenReturnStatus200() throws NotFoundException {
        var request = new CarRequest();
        request.setName("Golf");
        var id = UUID.randomUUID();
        when(service.updateCar(anyString(), anyString())).thenReturn(getCarDto(id));

        var response = resource.updateCar(id.toString(), request);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.hasEntity());
    }

    @Test
    void givenCarExists_whenDeleteCar_thenReturnStatus200() throws NotFoundException {
        var id = UUID.randomUUID();
        doNothing().when(service).deleteCar(anyString());

        var response = resource.deleteCar(id.toString());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
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
