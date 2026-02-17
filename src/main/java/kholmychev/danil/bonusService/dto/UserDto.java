package kholmychev.danil.bonusService.dto;

import lombok.Data;
import lombok.Value;

/**
 * DTO for {@link kholmychev.danil.bonusService.models.User}
 */
@Value
@Data
public class UserDto {
    String username;
    String password;
}