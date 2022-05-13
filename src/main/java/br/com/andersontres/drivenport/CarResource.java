package br.com.andersontres.drivenport;

import br.com.andersontres.domain.CarService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import br.com.andersontres.domain.exception.NotFoundException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/cars")
public class CarResource {

    @Inject
    private final CarService service;

    public CarResource(CarService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all cars")
    @APIResponse(responseCode = "200", description = "Cars found")
    @APIResponse(responseCode = "204", description = "No cars found")
    public Response getAllCars() {
        var cars = service.getAllCars();
        return cars.map(Response::ok).orElseGet(Response::noContent).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Find car by id")
    @APIResponse(responseCode = "200", description = "Car found")
    @APIResponse(responseCode = "404", description = "Car not found")
    public Response getCar(@PathParam("id") String id) {
        var car = service.getCar(id);
        return car.map(Response::ok).orElseGet(() -> Response.status(Response.Status.NOT_FOUND)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create new car")
    @APIResponse(responseCode = "200", description = "Car created")
    public Response createCar(@Valid CarRequest request) {
        var id = service.createCar(request.getName());
        return Response.created(URI.create("/cars/" + id.toString())).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update car")
    @APIResponse(responseCode = "200", description = "Car updated")
    @APIResponse(responseCode = "404", description = "Car not found")
    public Response updateCar(@PathParam("id") String id, @Valid CarRequest request) throws NotFoundException {
        var car = service.updateCar(id, request.getName());
        return Response.ok(car).build();
    }

    @DELETE
    @Path("/{id")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete car")
    @APIResponse(responseCode = "200", description = "Car deleted")
    @APIResponse(responseCode = "404", description = "Car not found")
    public Response deleteCar(@PathParam("id") String id) throws NotFoundException {
        service.deleteCar(id);
        return Response.ok().build();
    }
}
