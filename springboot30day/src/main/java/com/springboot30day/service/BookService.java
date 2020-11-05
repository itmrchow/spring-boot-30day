package com.springboot30day.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.NoResultException;

import com.springboot30day.entity.Book;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

//假的Service

@Slf4j // log
@Service
public class BookService {
    // AtomicInteger : 原子操作->執行緒安全的
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Retryable(
            // 收入的NoResultException
            include = { NoResultException.class },
            // 嘗試次數
            maxAttempts = 3,
            // 幾秒後重試
            backoff = @Backoff(value = 2000))
    public Book getBook() {
        // 以原子方式當前+1
        int count = atomicInteger.incrementAndGet();
        log.info("count=" + count);

        if (count < 5) {
            throw new NoResultException();
        } else {
            return new Book();
        }
    }

    //錯誤處理，當重試次數超過時跳入
    @Recover
    public Book recover(NoResultException e) {
        log.info("get NoResultException & return null");
        return null;
    }

}
