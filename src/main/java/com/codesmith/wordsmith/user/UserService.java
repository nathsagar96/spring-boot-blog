package com.codesmith.wordsmith.user;

import com.codesmith.wordsmith.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user-related operations.
 *
 * <p>This class provides methods for retrieving, updating, and deleting user information
 * by interacting with the UserRepository and mapping user entities to DTOs using UserMapper.</p>
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * Retrieves all users from the database.
     *
     * @return a list of {@link UserResponseDto} containing the information of all users.
     */
    @Cacheable(value = "users")
    List<UserResponseDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to be retrieved.
     * @return a {@link UserResponseDto} containing the user's information.
     * @throws ResourceNotFoundException if a user with the specified ID does not exist.
     */
    @Cacheable(value = "users", key = "#userId")
    UserResponseDto getUserById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userMapper.toDto(user);
    }

    /**
     * Updates the information of an existing user.
     *
     * @param userId         the ID of the user to be updated.
     * @param userRequestDto the DTO containing the updated user information.
     * @return a {@link UserResponseDto} containing the updated user's information.
     * @throws ResourceNotFoundException if a user with the specified ID does not exist.
     */
    @CachePut(value = "users", key = "#userId")
    UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setEmail(userRequestDto.email());
        user.setFirstName(userRequestDto.firstName());
        user.setLastName(userRequestDto.lastName());
        user.setPhoneNumber(userRequestDto.phoneNumber());
        user.setAddress(userRequestDto.address());
        user.setCity(userRequestDto.city());
        user.setState(userRequestDto.state());
        user.setZipCode(userRequestDto.zipCode());
        user.setCountry(userRequestDto.country());

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to be deleted.
     */
    @CacheEvict(value = "users", key = "#userId")
    void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

