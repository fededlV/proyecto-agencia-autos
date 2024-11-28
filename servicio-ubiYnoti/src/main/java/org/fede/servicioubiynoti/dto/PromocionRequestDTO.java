package org.fede.servicioubiynoti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionRequestDTO {
    private List<Integer> telefonos; // Lista de números de teléfono
    private String mensaje;          // Mensaje de la promoción
}
