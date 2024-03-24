package bodydodi.study.serialize.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SerialException extends Exception{
    private static final long serialVersionUID = 100L;

    private int seqMember;
    private String name;
    private String mobile;
    private int age;
}
