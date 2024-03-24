package bodydodi.study.serialize.service;

import bodydodi.study.serialize.domain.SerialMember;

public interface SerialService {
    SerialMember deserialize(String base64Member);
    String serialize(SerialMember member);
}
