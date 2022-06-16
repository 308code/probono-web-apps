package com.continuingdevelopment.probonorest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class ProbonoRestApplicationTests {
    @Autowired
    private ProbonoRestApplication probonoRestApplication;

    @Test
    void contextLoads() {
        assertThat(probonoRestApplication).isNotNull();
    }

}
