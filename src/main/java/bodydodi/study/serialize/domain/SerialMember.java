package bodydodi.study.serialize.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class SerialMember implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String mobile;
    private double age;
    //private String memo;
}
