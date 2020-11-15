package com.springboot30day.service;

import java.util.Date;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

//回覆目前時間,如果有被cache就不會拿到最新時間
@Service
public class TimeService {
    
    @Cacheable(cacheNames = "getTime")
    public Date getTime() {
        return new Date();
    }

    @Cacheable("currentTimeMillis")
    public Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


}
