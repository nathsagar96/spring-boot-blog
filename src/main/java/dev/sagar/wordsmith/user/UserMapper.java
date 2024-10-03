package dev.sagar.wordsmith.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDto toDto(UserEntity userEntity) {
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getPhoneNumber(),
                userEntity.getBirthDate(),
                userEntity.getAddress(),
                userEntity.getCity(),
                userEntity.getState(),
                userEntity.getZipCode(),
                userEntity.getCountry(),
                userEntity.getRole(),
                userEntity.getBio());
    }

    public UserEntity toEntity(UserRequestDto userRequestDto) {
        return new UserEntity(
                userRequestDto.username(),
                userRequestDto.email(),
                passwordEncoder.encode(userRequestDto.password()),
                userRequestDto.firstName(),
                userRequestDto.lastName(),
                userRequestDto.phoneNumber(),
                userRequestDto.birthDate(),
                userRequestDto.address(),
                userRequestDto.city(),
                userRequestDto.state(),
                userRequestDto.zipCode(),
                userRequestDto.country(),
                userRequestDto.role(),
                userRequestDto.bio()
        );
    }
}
