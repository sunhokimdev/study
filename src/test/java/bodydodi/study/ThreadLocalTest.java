package bodydodi.study;

import bodydodi.study.thread.threadlocal.domain.ThreadLocalMember;
import bodydodi.study.thread.threadlocal.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadLocalTest {

    private ThreadLocalService nonThreadLocalService = new NonThreadLocalServiceImpl();
    private ThreadLocalService threadLocalService = new ThreadLocalServiceImpl();
    private ThreadLocalService nonThreadLocalWithOutFieldService = new NonThreadLocalWithOutFieldServiceImpl();
    private ThreadLocalService threadLocalWithThreadPoolService = new ThreadLocalWithThreadPoolServiceImpl();

    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Test
    @DisplayName("필드 변수가 있는 서비스에서는 동시성 문제가 발생한다.")
    public void nonThreadLocalTest() {

        Runnable runnable01 = () -> {
            nonThreadLocalService.addMember(ThreadLocalMember.builder()
                            .id(1)
                            .name("sunhokim")
                            .age(32)
                            .build());
        };

        Runnable runnable02 = () -> {
            nonThreadLocalService.addMember(ThreadLocalMember.builder()
                    .id(2)
                    .name("bodycodi")
                    .age(4)
                    .build());
        };

        Thread thread01 = new Thread(runnable01);
        thread01.setName("Thread 01");
        Thread thread02 = new Thread(runnable02);
        thread02.setName("Thread 02");

        thread01.start();
        // sleep(3000); // 동시성 문제 발생하지 않는 코드
        sleep(100); // 동시성 문제 발생 코드
        thread02.start();

        sleep(2000);
    }

    @Test
    @DisplayName("필드 변수가 없는 서비스에서는 동시성문제가 발생되지 않는다.")
    public void nonThreadLocalWithOutFieldTest() {

        Runnable runnable01 = () -> {
            nonThreadLocalWithOutFieldService.addMember(ThreadLocalMember.builder()
                    .id(1)
                    .name("sunhokim")
                    .age(32)
                    .build());
        };

        Runnable runnable02 = () -> {
            nonThreadLocalWithOutFieldService.addMember(ThreadLocalMember.builder()
                    .id(2)
                    .name("bodycodi")
                    .age(4)
                    .build());
        };

        Thread thread01 = new Thread(runnable01);
        thread01.setName("Thread 01");
        Thread thread02 = new Thread(runnable02);
        thread02.setName("Thread 02");

        thread01.start();
        // sleep(3000); // 동시성 문제 발생하지 않는 코드
        sleep(100); // 동시성 문제 발생 코드
        thread02.start();

        sleep(2000);
    }

    @Test
    @DisplayName("필드 변수에 Thread Local 저장소 객체를 생성하면 동시성 문제가 발생되지 않는다.")
    public void threadLocalTest() {
        Runnable runnable01 = () -> {
            threadLocalService.addMember(ThreadLocalMember.builder()
                    .id(1)
                    .name("sunhokim")
                    .age(32)
                    .build());
        };

        Runnable runnable02 = () -> {
            threadLocalService.addMember(ThreadLocalMember.builder()
                    .id(2)
                    .name("bodycodi")
                    .age(4)
                    .build());
        };

        Thread thread01 = new Thread(runnable01);
        thread01.setName("Thread 01");
        Thread thread02 = new Thread(runnable02);
        thread02.setName("Thread 02");

        thread01.start();
        // sleep(3000); // 동시성 문제 발생하지 않는 코드
        sleep(100); // 동시성 문제 발생 코드
        thread02.start();

        threadLocalService.removeMember();

        sleep(2000);
    }

    @Test
    @DisplayName("필드 변수가 Thread Local 저장소를 가지고 있다고 하더라도 TLS 메모리 해제를 안해주면 문제가 발생된다.")
    public void threadLocalWithThreadPoolTest() {
        Runnable runnable01 = () -> {
            threadLocalWithThreadPoolService.addMember(ThreadLocalMember.builder()
                    .id(1)
                    .name("sunhokim")
                    .age(32)
                    .build());
        };

        for (int i = 0; i < 10; i++) {
            executorService.execute(runnable01);
            sleep(1000);
        }

        sleep(2000);
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
