package bodydodi.study.thread.threadlocal.service;

import bodydodi.study.thread.threadlocal.domain.ThreadLocalMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NonThreadLocalServiceImpl implements ThreadLocalService{
    private ThreadLocalMember member = new ThreadLocalMember();

    @Override
    public void addMember(ThreadLocalMember request) {
        this.member = ThreadLocalMember.builder()
                .id(request.getId())
                .name(request.getName())
                .age(request.getAge())
                .build();

        sleep(1000);

        log.info("Member ADD -> ID: {}, Name: {}, Age: {}",
                member.getId(),
                member.getName(),
                member.getAge());
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
