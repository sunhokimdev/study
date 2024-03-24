package bodydodi.study.interceptor.config;

import bodydodi.study.interceptor.domain.InterceptorCustomer;
import bodydodi.study.interceptor.domain.InterceptorJwtTokenProvider;
import bodydodi.study.interceptor.repository.InterceptorRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class InterceptorWebMvcConfig implements WebMvcConfigurer {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
        InterceptorRepository repository = jpaRepositoryFactory.getRepository(InterceptorRepository.class);

        registry.addInterceptor(new InterceptorCustomer(new InterceptorJwtTokenProvider(), repository));
    }
}
