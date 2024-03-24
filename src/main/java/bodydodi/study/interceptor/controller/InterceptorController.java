package bodydodi.study.interceptor.controller;

import bodydodi.study.interceptor.domain.InterceptorJwtTokenProvider;
import bodydodi.study.interceptor.dto.InterceptorUserDto;
import bodydodi.study.interceptor.entity.InterceptorUser;
import bodydodi.study.interceptor.service.InterceptorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/interceptor")
@Slf4j
@RequiredArgsConstructor
public class InterceptorController {
    private final InterceptorService service;
    private final InterceptorJwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    public HttpEntity<HttpStatus> add(@RequestBody InterceptorUserDto requestDto) {
        String token = jwtTokenProvider.createJwtToken(requestDto);
        requestDto.setToken(token);

        service.addUser(createInterceptorUser(requestDto));

        return new HttpEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<InterceptorUserDto> list() {
        return service.findList();
    }

    private InterceptorUser createInterceptorUser(InterceptorUserDto userDto) {
        return InterceptorUser.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .memo(userDto.getMemo())
                .mobile(userDto.getMobile())
                .token(userDto.getToken())
                .auth(userDto.getAuth())
                .build();
    }
}
