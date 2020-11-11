package com.springboot30day.component;

import java.text.SimpleDateFormat;

import com.springboot30day.service.BookService;
import com.springboot30day.service.TimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
//在application執行時執行
public class ApplicationLoader implements CommandLineRunner {
    @Autowired
    private BookService bookService;
    @Autowired
    private TimeService timeService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public void run(String... args) throws Exception {
        // try {
        //     bookService.getBook();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        for (int i = 0; i < 30; i++) {
            //cache 設定 5秒 -> 5秒後才會new新的time
            log.info(sdf.format(timeService.getTime()));
            Thread.sleep(1000);
        }
    }

}
