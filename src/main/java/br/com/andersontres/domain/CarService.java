package br.com.andersontres.domain;

import br.com.andersontres.domain.exception.NotFoundException;
import br.com.andersontres.driverport.CarRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CarService {

    private static final String CAR_NOT_FOUND_MSG = "Car not found";

    @Inject
    CarRepository repository;

    @Inject
    CarMapper mapper;

    public CarService(CarRepository repository, CarMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<List<CarDto>> getAllCars() {
        var cars = repository.listAll();
        return cars.isEmpty() ? Optional.empty() : Optional.of(mapper.carToCarDto(cars));
    }

    public Optional<CarDto> getCar(String id) {
        var car = repository.findById(UUID.fromString(id));
        return car.isEmpty() ? Optional.empty() : Optional.of(mapper.carToCarDto(car.get()));
    }

    @Transactional
    public UUID createCar(String name) {
        var car = new Car();
        car.setName(name);

        repository.persist(car);

        return car.getId();
    }

    @Transactional
    public CarDto updateCar(String id, String name) throws NotFoundException {
        var car = repository.findById(UUID.fromString(id));
        if (car.isEmpty()) {
            throw new NotFoundException(CAR_NOT_FOUND_MSG);
        }

        var entity = car.get();
        entity.setName(name);
        repository.persist(entity);

        return mapper.carToCarDto(entity);
    }

    @Transactional
    public void deleteCar(String id) throws NotFoundException {
        var car = repository.findById(UUID.fromString(id));
        if (car.isEmpty()) {
            throw new NotFoundException(CAR_NOT_FOUND_MSG);
        }

        repository.delete(car.get());
    }
}
