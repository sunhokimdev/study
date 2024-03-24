package bodydodi.study.serialize.controller;

import bodydodi.study.serialize.domain.SerialMember;
import bodydodi.study.serialize.dto.DeserializeMemberDto;
import bodydodi.study.serialize.dto.SerializeMemberDto;
import bodydodi.study.serialize.service.SerialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serial")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class SerialController {
    private final SerialService service;

    @GetMapping("/deserialize")
    public SerializeMemberDto deserialize(@RequestBody DeserializeMemberDto base64Member) {
        return createSerialMemberDto(service.deserialize(base64Member.getBase64Member()));
    }

    @GetMapping("/serialize")
    public String serialize(@RequestBody SerializeMemberDto requestDto) {
        return service.serialize(createSerialMember(requestDto));
    }

    private SerialMember createSerialMember(SerializeMemberDto requestDto) {
        return SerialMember.builder()
                .name(requestDto.getName())
                .age(requestDto.getAge())
                .mobile(requestDto.getMobile())
                .build();
    }

    private SerializeMemberDto createSerialMemberDto(SerialMember serialMember) {
        return SerializeMemberDto.builder()
                .name(serialMember.getName())
                .mobile(serialMember.getMobile())
                .age(serialMember.getAge())
                .build();
    }
}
