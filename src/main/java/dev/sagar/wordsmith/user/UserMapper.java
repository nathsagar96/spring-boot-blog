package dev.sagar.wordsmith.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A component that handles the mapping between UserEntity and UserRequestDto/UserResponseDto.
 *
 * <p>This class is responsible for converting {@link UserEntity} objects (representing users in the
 * database) to {@link UserResponseDto} objects (representing users in the API response) and vice
 * versa.
 *
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  /**
   * Converts a UserEntity to a UserResponseDto.
   *
   * @param userEntity the UserEntity to be converted.
   * @return a {@link UserResponseDto} representing the user information.
   */
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

  /**
   * Converts a UserRequestDto to a UserEntity.
   *
   * @param userRequestDto the UserRequestDto containing user information to be converted.
   * @return a {@link UserEntity} representing the user in the database.
   */
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
