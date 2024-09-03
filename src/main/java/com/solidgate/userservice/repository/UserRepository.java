package com.solidgate.userservice.repository;

import com.solidgate.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(u) = COUNT(CASE WHEN u.balance = :balance THEN 1 ELSE NULL END) " +
            "FROM User u WHERE u.id BETWEEN :startId AND :endId")
    boolean checkAllUsersInRangeHaveBalance(int startId, int endId, int balance);
}
