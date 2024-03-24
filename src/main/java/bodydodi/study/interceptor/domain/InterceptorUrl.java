package bodydodi.study.interceptor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum InterceptorUrl {
    LOGIN("/login"),
    LOGOUT("/logout"),
    ADD("/add"),
    REMOVE("/remove"),
    INFO("/info"),
    LIST("/list");

    private String title;

    public boolean isLoginUrl() {
        return this == InterceptorUrl.LOGIN;
    }

    public boolean isLogoutUrl() {
        return this == InterceptorUrl.LOGOUT;
    }

    public boolean isInfoUrl() {
        return this == InterceptorUrl.INFO;
    }

    public boolean isAddUrl() {
        return this == InterceptorUrl.ADD;
    }

    public boolean isRemoveUrl() {
        return this == InterceptorUrl.REMOVE;
    }

    public boolean isListUrl() {
        return this == InterceptorUrl.LIST;
    }

    public static InterceptorUrl findUrlList(String url) {
        return Arrays.stream(InterceptorUrl.values())
                .filter(o -> url.contains(o.getTitle()))
                .findFirst()
                .orElse(null);
    }
}
