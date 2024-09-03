package com.solidgate.userservice.service.impl;

import com.solidgate.userservice.config.properties.BatchProperties;
import com.solidgate.userservice.repository.UserRepository;
import com.solidgate.userservice.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BatchProperties batchProperties;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void setUsersBalance(Map<Integer, Integer> userBalances) {
        var batchSize = batchProperties.getSize();
        var batchElements = new HashMap<Integer, Integer>();

        log.info("Start of [{}] user balances update", userBalances.size());
        userBalances.forEach((key, value) -> {
            batchElements.put(key, value);
            if (batchElements.size() == batchSize) {
                updateUserBatch(batchElements);
            }
        });
        if (!batchElements.isEmpty()) {
            updateUserBatch(batchElements);
        }
        log.info("End of [{}] user balances update", userBalances.size());
    }

    private void updateUserBatch(Map<Integer, Integer> batchElements) {
        log.debug("Start updating users with ids [{}]", batchElements.keySet());
        var users = userRepository.findAllById(batchElements.keySet()).stream()
                .map(user -> {
                    user.setBalance(batchElements.get(user.getId()));
                    return user;
                }).toList();
        userRepository.saveAllAndFlush(users);
        entityManager.clear();
        log.debug("Updated users [{}]", users);
        batchElements.clear();
    }
}
