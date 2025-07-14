package cassino.com.cassino_online.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cassino.com.cassino_online.dto.UserRequestDTO;
import cassino.com.cassino_online.dto.UserResponseDTO;
import cassino.com.cassino_online.entities.User;
import cassino.com.cassino_online.mapper.UserMapper;
import cassino.com.cassino_online.service.UserService;
import jakarta.validation.Valid; 

@RestController
@RequestMapping("/users")
public class UserController { 
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        User user = UserMapper.toEntity(requestDTO); 
        User createdUser = userService.createUser(user); 
        return new ResponseEntity<>(UserMapper.toResponseDTO(createdUser), HttpStatus.CREATED); 
    }

    
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() { 
        List<UserResponseDTO> users = userService.findAllUsers().stream()
            .map(UserMapper::toResponseDTO) 
            .collect(Collectors.toList());
        return ResponseEntity.ok(users); 
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id)); 
        return ResponseEntity.ok(UserMapper.toResponseDTO(user)); 
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO requestDTO) {
        User updatedUserEntity = UserMapper.toEntity(requestDTO); 
        User savedUser = userService.updateUser(id, updatedUserEntity);
        return ResponseEntity.ok(UserMapper.toResponseDTO(savedUser)); 
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @PostMapping("/{id}/deposit")
    public ResponseEntity<UserResponseDTO> deposit(@PathVariable Long id, @RequestBody BigDecimal amount) {
        
        if (amount == null) {
            throw new IllegalArgumentException("Deposit amount cannot be null");
        }
        User updatedUser = userService.deposit(id, amount);
        return ResponseEntity.ok(UserMapper.toResponseDTO(updatedUser)); 
    }


    @PostMapping("/{id}/bet")
    public ResponseEntity<UserResponseDTO> placeBet(@PathVariable Long id, @RequestBody BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Bet amount cannot be null");
        }
        User updatedUser = userService.placeBet(id, amount);
        return ResponseEntity.ok(UserMapper.toResponseDTO(updatedUser)); 
    }


    @PostMapping("/{id}/winnings")
    public ResponseEntity<UserResponseDTO> addWinning(@PathVariable Long id, @RequestBody BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Winning amount cannot be null");
        }
        User updatedUser = userService.addWinning(id, amount);
        return ResponseEntity.ok(UserMapper.toResponseDTO(updatedUser)); 
    }
}
