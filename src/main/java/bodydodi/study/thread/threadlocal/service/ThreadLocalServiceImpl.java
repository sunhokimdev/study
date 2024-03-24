package bodydodi.study.thread.threadlocal.service;

import bodydodi.study.thread.threadlocal.domain.ThreadLocalMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreadLocalServiceImpl implements ThreadLocalService {

    private ThreadLocal<ThreadLocalMember> member = new ThreadLocal();

    @Override
    public void addMember(ThreadLocalMember request) {
        this.member.set(ThreadLocalMember.builder()
                .id(request.getId())
                .name(request.getName())
                .age(request.getAge())
                .build());

        ThreadLocalMember threadLocalMember = member.get();

        sleep(1000);

        log.info("Member ADD -> ID: {}, Name: {}, Age: {}",
                threadLocalMember.getId(),
                threadLocalMember.getName(),
                threadLocalMember.getAge());
    }

    @Override
    public void removeMember() {
        member.remove();
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
