package cassino.com.cassino_online.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cassino.com.cassino_online.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findByCpf(String cpf);
    
    Optional<User> findByUsername(String username);
    
}
