package bodydodi.study.thread.threadlocal.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThreadLocalMember {
    private int id;
    private String name;
    private int age;
}