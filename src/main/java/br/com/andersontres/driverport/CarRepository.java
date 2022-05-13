package br.com.andersontres.driverport;

import br.com.andersontres.domain.Car;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CarRepository implements PanacheRepository<Car> {

    public Optional<Car> findById(UUID id) {
        return Optional.ofNullable(find("id", id).firstResult());
    }

}
