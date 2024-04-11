package com.example.study.schedule.api;

import com.example.study.schedule.api.dto.ScheduleSaveDto;
import com.example.study.schedule.application.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/save")
    public void save(@RequestBody ScheduleSaveDto.Request request) {
        scheduleService.saveByIsolationReadUnCommitted(request.getSeqLesson());
    }

    @PostMapping("/save/distributedlock")
    public void saveByDistributedLock(@RequestBody ScheduleSaveDto.Request request) {
        scheduleService.saveByDistributedLock(request.getSeqLesson());
    }
}
