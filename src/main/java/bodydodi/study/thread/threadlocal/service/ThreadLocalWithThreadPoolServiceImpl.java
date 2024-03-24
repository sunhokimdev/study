package bodydodi.study.thread.threadlocal.service;

import bodydodi.study.thread.threadlocal.domain.ThreadLocalMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreadLocalWithThreadPoolServiceImpl implements ThreadLocalService {

    private ThreadLocal<ThreadLocalMember> member = new ThreadLocal();

    @Override
    public void addMember(ThreadLocalMember request) {
        ThreadLocalMember threadLocalMember = member.get();

        if (threadLocalMember != null && request.getId() == threadLocalMember.getId()) {
            log.info("Exist Member -> ID: {}, Name: {}, Age: {}",
                    threadLocalMember.getId(),
                    threadLocalMember.getName(),
                    threadLocalMember.getAge());

            return;
        }

        this.member.set(ThreadLocalMember.builder()
                .id(request.getId())
                .name(request.getName())
                .age(request.getAge())
                .build());

        sleep(1000);

        threadLocalMember = member.get();

        log.info("New Member ADD -> ID: {}, Name: {}, Age: {}",
                threadLocalMember.getId(),
                threadLocalMember.getName(),
                threadLocalMember.getAge());

        // TLS를 해제하는 코드
        // member.remove();
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
