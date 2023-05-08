package com.cathay.demo;

import com.cathay.demo.controller.CurrController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private CurrController currController;

    @Test
    void contextLoads() {
        assertThat(currController).isNotNull();
    }

}
