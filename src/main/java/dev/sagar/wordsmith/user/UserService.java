package dev.sagar.wordsmith.user;

import dev.sagar.wordsmith.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userMapper.toDto(userEntity);
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
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        userEntity.setEmail(userRequestDto.email());
        userEntity.setFirstName(userRequestDto.firstName());
        userEntity.setLastName(userRequestDto.lastName());
        userEntity.setPhoneNumber(userRequestDto.phoneNumber());
        userEntity.setAddress(userRequestDto.address());
        userEntity.setCity(userRequestDto.city());
        userEntity.setState(userRequestDto.state());
        userEntity.setZipCode(userRequestDto.zipCode());
        userEntity.setCountry(userRequestDto.country());

        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDto(savedUserEntity);
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

