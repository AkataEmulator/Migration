package com.migration.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobTimer {

    @Scheduled(fixedRate = 5000L)
    public void doSomething(){
        log.info("-----------------");
    }
}
