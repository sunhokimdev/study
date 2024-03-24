package bodydodi.study.interceptor.dto;

import bodydodi.study.interceptor.domain.InterceptorUserAuth;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InterceptorUserDto {
    private int seqUser;
    private String email;
    private String name;
    private String password;
    private String memo;
    private String mobile;
    private String token;
    private InterceptorUserAuth auth;
}