package bodydodi.study.thread.threadlocal.service;

import bodydodi.study.thread.threadlocal.domain.ThreadLocalMember;

public interface ThreadLocalService {
    void addMember(ThreadLocalMember member);
    void removeMember();
}
