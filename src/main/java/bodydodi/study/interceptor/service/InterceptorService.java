package bodydodi.study.interceptor.service;

import bodydodi.study.interceptor.dto.InterceptorUserDto;
import bodydodi.study.interceptor.entity.InterceptorUser;

import java.util.List;

public interface InterceptorService {
    void addUser(InterceptorUser user);
    List<InterceptorUserDto> findList();
}
