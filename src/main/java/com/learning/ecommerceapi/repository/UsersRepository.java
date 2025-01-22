package com.learning.ecommerceapi.repository;

import com.learning.ecommerceapi.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    @Query(value = "SELECT * FROM users_tb WHERE email = :email", nativeQuery = true)
    Optional<Users> findUserByEmail(@Param("email") String email);
    Optional<Users> getRoleByUserId(UUID userId);
    Optional<Users> findUserByUserName(String userName);
}
