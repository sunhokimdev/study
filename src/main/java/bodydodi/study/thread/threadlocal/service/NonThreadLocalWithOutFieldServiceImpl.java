package bodydodi.study.thread.threadlocal.service;

import bodydodi.study.thread.threadlocal.domain.ThreadLocalMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NonThreadLocalWithOutFieldServiceImpl implements ThreadLocalService{
    @Override
    public void addMember(ThreadLocalMember request) {

        sleep(1000);

        log.info("Member ADD -> ID: {}, Name: {}, Age: {}",
                request.getId(),
                request.getName(),
                request.getAge());
    }

    @Override
    public void removeMember() {

    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
