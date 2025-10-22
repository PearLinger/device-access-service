package com.banmenit.test;

import com.elegoo.cloud.DeviceAccessApplication;
import com.elegoo.cloud.test.threadlocal.UserDTO;
import com.elegoo.cloud.test.threadlocal.UserThreadLocal;
import jakarta.annotation.Resource;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author machao
 * @Desc: 类描述
 * date: 2025/8/7 20:51
 */

@SpringBootTest(classes = DeviceAccessApplication.class)
public class McTest {

    @Resource
    private UserThreadLocal userThreadLocal;


    @Test
    public void t1() throws IOException {
        System.out.println(Thread.currentThread());
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setName("admin");
        UserThreadLocal.set(userDTO2);
        userThreadLocal.test();
        userThreadLocal.test2();
    }




}
