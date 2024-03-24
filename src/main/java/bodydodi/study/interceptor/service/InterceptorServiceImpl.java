package bodydodi.study.interceptor.service;

import bodydodi.study.interceptor.dto.InterceptorUserDto;
import bodydodi.study.interceptor.entity.InterceptorUser;
import bodydodi.study.interceptor.repository.InterceptorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterceptorServiceImpl implements InterceptorService{
    private final InterceptorRepository repository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(InterceptorUser user) {
        repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterceptorUserDto> findList() {
        List<InterceptorUserDto> userDtoList = new ArrayList<>();

        for(InterceptorUser user : repository.findAll()) {
            InterceptorUserDto userDto = createUserDto(user);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    private InterceptorUserDto createUserDto(InterceptorUser user) {
        return InterceptorUserDto.builder()
                .seqUser(user.getSeqUser())
                .email(user.getEmail())
                .name(user.getName())
                .mobile(user.getMobile())
                .memo(user.getMemo())
                .auth(user.getAuth())
                .token(user.getToken())
                .build();
    }
}

