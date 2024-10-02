package dev.sagar.wordsmith.user;

import dev.sagar.wordsmith.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    List<UserResponseDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::mapToUserResponseDto)
                .toList();
    }

    UserResponseDto getUserById(Long userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userMapper.mapToUserResponseDto(userEntity);
    }

    UserResponseDto createUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = userMapper.mapToUserEntity(userRequestDto);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.mapToUserResponseDto(savedUserEntity);
    }

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
        return userMapper.mapToUserResponseDto(savedUserEntity);
    }

    void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

