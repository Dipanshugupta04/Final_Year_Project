package com.example.MEDICINE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.MEDICINE.Model.User;

public interface UserRepository extends JpaRepository<User,Long> {

    

    User findByusername(String username);

    Optional<User> findByEmail(String email);

    



    

    
}
