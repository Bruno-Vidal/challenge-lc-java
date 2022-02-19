package br.com.cristal.moviegame.entrypoint.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequest {
    private String name;
    private String email;
    private String password;
}
