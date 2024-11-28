package org.fede.servicioubiynoti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private Integer legajo;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private boolean esIncidente = false;
}
