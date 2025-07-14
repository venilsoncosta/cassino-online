package cassino.com.cassino_online.mapper;

import java.math.BigDecimal;

import cassino.com.cassino_online.dto.UserRequestDTO;
import cassino.com.cassino_online.dto.UserResponseDTO;
import cassino.com.cassino_online.entities.User;

public class UserMapper {
    
    public static User toEntity(UserRequestDTO dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setCpf(dto.getCpf());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setBalance(BigDecimal.ZERO); 
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCpf(user.getCpf());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setBalance(user.getBalance());
        return dto;
    }
}
