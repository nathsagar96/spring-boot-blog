package dev.sagar.wordsmith.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

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
        return UserEntity.builder()
                .username(userRequestDto.username())
                .email(userRequestDto.email())
                .password(passwordEncoder.encode(userRequestDto.password()))
                .firstName(userRequestDto.firstName())
                .lastName(userRequestDto.lastName())
                .phoneNumber(userRequestDto.phoneNumber())
                .birthDate(userRequestDto.birthDate())
                .address(userRequestDto.address())
                .city(userRequestDto.city())
                .state(userRequestDto.state())
                .zipCode(userRequestDto.zipCode())
                .country(userRequestDto.country())
                .role(userRequestDto.role())
                .bio(userRequestDto.bio())
                .build();
    }
}
