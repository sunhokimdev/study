package bodydodi.study.serialize.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SerializeMemberDto {
    private String name;
    private String mobile;
    private double age;
}
