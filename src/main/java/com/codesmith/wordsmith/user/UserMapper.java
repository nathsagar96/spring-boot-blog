package com.codesmith.wordsmith.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A component that handles the mapping between UserEntity and UserRequestDto/UserResponseDto.
 *
 * <p>This class is responsible for converting {@link User} objects (representing users in the
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
   * @param user the UserEntity to be converted.
   * @return a {@link UserResponseDto} representing the user information.
   */
  public UserResponseDto toDto(User user) {
    return new UserResponseDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getFirstName(),
        user.getLastName(),
        user.getPhoneNumber(),
        user.getBirthDate(),
        user.getAddress(),
        user.getCity(),
        user.getState(),
        user.getZipCode(),
        user.getCountry(),
        user.getRole(),
        user.getBio());
  }

  /**
   * Converts a UserRequestDto to a UserEntity.
   *
   * @param userRequestDto the UserRequestDto containing user information to be converted.
   * @return a {@link User} representing the user in the database.
   */
  public User toEntity(UserRequestDto userRequestDto) {
    return User.builder()
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
