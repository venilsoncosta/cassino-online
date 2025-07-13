package cassino.com.cassino_online.service;

import org.springframework.stereotype.Service;

import cassino.com.cassino_online.entities.User;
import cassino.com.cassino_online.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.findByCpf(user.getCpf()).isPresent()){
            throw new IllegalArgumentException("CPF already exists");
        }

        return userRepository.save(user);
    }
}
