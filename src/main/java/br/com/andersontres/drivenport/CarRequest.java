package br.com.andersontres.drivenport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CarRequest {

    @NotNull(message = "name must not be null")
    @NotBlank(message = "name must not be blank")
    private String name;
}
