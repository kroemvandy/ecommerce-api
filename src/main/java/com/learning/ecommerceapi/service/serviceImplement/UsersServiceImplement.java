package com.learning.ecommerceapi.service.serviceImplement;

import com.learning.ecommerceapi.exception.ResourceNotFountException;
import com.learning.ecommerceapi.libs.CustomUserDetails;
import com.learning.ecommerceapi.mapper.UserMapper;
import com.learning.ecommerceapi.model.dto.request.UserRequest;
import com.learning.ecommerceapi.model.dto.response.UserResponse;
import com.learning.ecommerceapi.model.entity.Users;
import com.learning.ecommerceapi.repository.UsersRepository;
import com.learning.ecommerceapi.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersServiceImplement implements UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Users getUserById(UUID userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFountException("User with id " + userId + " not found"));
    }

    @Override
    public List<Users> getUserName(String userName) {
        return List.of();
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Users users = new Users();
        users.setUserName(userRequest.getUserName());
        users.setFirstName(userRequest.getFirstName());
        users.setLastName(userRequest.getLastName());
        users.setEmail(userRequest.getEmail());
        users.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        users.setRoleName(userRequest.getRoleName());
        users.setCreatedAt(LocalDateTime.now());
        users.setUpdatedAt(LocalDateTime.now());
        Users savedUser = usersRepository.save(users);
        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public List<Users> getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        if (pageNo < 1 || pageSize < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page number and page size must be greater than 0");
        }
        String defaultSortBy = "userName";
        String defaultSortByField = (sortBy == null || sortBy.isEmpty()) ? defaultSortBy : sortBy;
        Sort.Direction direction = "ASC".equals(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo - 1 , pageSize, Sort.by(direction, defaultSortByField));
        return usersRepository.findAll(pageable).getContent();
    }

    @Override
    public UserResponse updateUser(UUID userId, UserRequest userRequest) {
        Users users = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFountException("User with id " + userId + " not found"));
        users.setUserName(userRequest.getUserName());
        users.setFirstName(userRequest.getFirstName());
        users.setLastName(userRequest.getLastName());
        users.setEmail(userRequest.getEmail());
        users.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        users.setRoleName(userRequest.getRoleName());
        users.setUpdatedAt(LocalDateTime.now());
        Users savedUser = usersRepository.save(users);
        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public void deleteUser(UUID userId) {
        Users users = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFountException("User with id " + userId + " not found"));
        usersRepository.delete(users);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFountException("User with email " + email + " not found"));
        return new CustomUserDetails(user);
    }
}
