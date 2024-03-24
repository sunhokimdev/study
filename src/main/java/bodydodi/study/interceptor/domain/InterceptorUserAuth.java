package bodydodi.study.interceptor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum InterceptorUserAuth {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String title;

    public boolean isRoleUser() {
        return this == ROLE_USER;
    }

    public boolean isRoleAdmin() {
        return this == ROLE_ADMIN;
    }

    public static InterceptorUserAuth findUserAuth(String auth) {
        return Arrays.stream(InterceptorUserAuth.values())
                .filter(o -> o.name().equals(auth))
                .findFirst()
                .orElse(null);
    }
}
