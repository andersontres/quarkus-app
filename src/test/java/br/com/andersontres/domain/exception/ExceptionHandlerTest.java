package br.com.andersontres.domain.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerTest {

    private ExceptionHandler handler = new ExceptionHandler();

    @Test
    void givenNotFoundException_whenToResponse_thenResponseStatus404() {
        var msg = "Error";
        var response = handler.toResponse(new NotFoundException(msg));

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
