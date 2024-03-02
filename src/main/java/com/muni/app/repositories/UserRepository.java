package com.muni.app.repositories;

import com.muni.app.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

   UserEntity findFirstById(Long id);

   UserEntity findByEmail(String email);

   UserEntity findByEmailAndOtp(String email, int otp);
}
