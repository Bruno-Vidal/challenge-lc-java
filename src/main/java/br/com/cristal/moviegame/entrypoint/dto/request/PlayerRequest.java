package br.com.cristal.moviegame.entrypoint.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequest {

    @NotBlank(message = "nome é obrigatório")
    private String name;
    @NotBlank(message = "email é obrigatório")
    @Email
    private String email;
    @NotBlank(message = "senha é obrigatório")
    private String password;
}
