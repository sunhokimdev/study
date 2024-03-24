package bodydodi.study.interceptor.repository;

import bodydodi.study.interceptor.entity.InterceptorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterceptorRepository extends JpaRepository<InterceptorUser, Long> {
}
