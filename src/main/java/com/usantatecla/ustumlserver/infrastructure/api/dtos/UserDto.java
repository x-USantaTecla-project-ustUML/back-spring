package com.usantatecla.ustumlserver.infrastructure.api.dtos;

import com.usantatecla.ustumlserver.domain.model.Role;
import com.usantatecla.ustumlserver.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;
    private Role role;

    private void doDefault() {
        if (Objects.isNull(role)) {
            this.role = Role.AUTHENTICATED;
        }
    }

    public User toUser() {
        this.doDefault();
        this.password = new BCryptPasswordEncoder().encode(this.password);
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
