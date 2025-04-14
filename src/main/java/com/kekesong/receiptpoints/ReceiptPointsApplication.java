package com.kekesong.receiptpoints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ReceiptPointsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReceiptPointsApplication.class, args);
    }

}
