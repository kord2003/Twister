package com.example.twister.executor;

import rx.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}