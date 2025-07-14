package cassino.com.cassino_online.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String cpf;
    private String phoneNumber;
    private BigDecimal balance;
}
