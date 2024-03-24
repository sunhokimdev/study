package bodydodi.study.serialize.service;

import bodydodi.study.serialize.domain.SerialMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Service
@Slf4j
public class SerialServiceImpl implements SerialService{
    @Override
    public SerialMember deserialize(String base64Member) {
        return deserializeStringToMember(base64Member);
    }

    @Override
    public String serialize(SerialMember member) {
        return serializeMemberToByteArray(member);
    }

    private String serializeMemberToByteArray(SerialMember member) {
        byte[] serializedMember = null;

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try(ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
                oos.writeObject(member);
                serializedMember = outputStream.toByteArray();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Base64Utils.encodeToString(serializedMember);
    }

    private SerialMember deserializeStringToMember(String base64Member) {
        byte[] serializedMember = Base64Utils.decodeFromString(base64Member);
        SerialMember member = null;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object objectMember = ois.readObject();
                member = (SerialMember) objectMember;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return member;
    }
}
