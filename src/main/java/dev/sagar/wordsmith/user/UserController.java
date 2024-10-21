package dev.sagar.wordsmith.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing users.
 *
 * <p>This class provides endpoints for user management including retrieval, updating,
 * and deletion of user information.All endpoints require
 * Bearer Authentication.</p>
 *
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User API", description = "APIs for User management")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves a list of all users.
     *
     * @return a List of {@link UserResponseDto} representing all users.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve.
     * @return the {@link UserResponseDto} representing the found user.
     */
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserById(@PathVariable final Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * Updates an existing user.
     *
     * @param userId         the ID of the user to update.
     * @param userRequestDto the request DTO containing the updated user details.
     * @return the {@link UserResponseDto} representing the updated user.
     */
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUser(@PathVariable final Long userId,
                                      @RequestBody @Valid final UserRequestDto userRequestDto) {
        return userService.updateUser(userId, userRequestDto);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete.
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable final Long userId) {
        userService.deleteUser(userId);
    }
}
