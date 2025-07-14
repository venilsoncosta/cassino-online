package cassino.com.cassino_online.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cassino.com.cassino_online.entities.User;
import cassino.com.cassino_online.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.findByCpf(user.getCpf()).isPresent()){
            throw new IllegalArgumentException("CPF already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User updateUser){
        return userRepository.findById(id).map(user -> {
            if(!user.getEmail().equals(updateUser.getEmail()) && userRepository.findByEmail(updateUser.getEmail()).isPresent()){
                throw new IllegalArgumentException("Email already exists");
            }
            if (!user.getCpf().equals(updateUser.getCpf()) && userRepository.findByCpf(updateUser.getCpf()).isPresent()){
                throw new IllegalArgumentException("CPF already exists");
            }
            user.setUsername(updateUser.getUsername());
            user.setEmail(updateUser.getEmail());
            user.setCpf(updateUser.getCpf());
            user.setPhoneNumber(updateUser.getPhoneNumber());
            
            return userRepository.save(user);
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    @Transactional
    public User deposit(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Deposit amount must be positive");
        }

        user.setBalance(user.getBalance().add(amount));
        return userRepository.save(user);
    }

    @Transactional
    public User placeBet(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Bet amount must be positive");
        }

        if (user.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance().subtract(amount));
        return userRepository.save(user);
    }

    @Transactional
    public User addWinning(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Winning amount must be positive");
        }

        user.setBalance(user.getBalance().add(amount));
        return userRepository.save(user);
    }


}
