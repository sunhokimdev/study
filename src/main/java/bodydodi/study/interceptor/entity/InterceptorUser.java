package bodydodi.study.interceptor.entity;

import bodydodi.study.interceptor.domain.InterceptorUserAuth;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "INTERCEPTOR_USER")
@AllArgsConstructor
@NoArgsConstructor
public class InterceptorUser {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int seqUser;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private String memo;
    private String mobile;
    @Column(length = 1000)
    private String token;
    private InterceptorUserAuth auth;
}

