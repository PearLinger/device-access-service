package com.elegoo.cloud.test.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

/**
 * @author yangyi
 * @version v 1.0
 * @date 2025/8/21 18:11
 */
@Data
@Service
public class UserThreadLocal {
  private static final ThreadLocal<UserDTO> CONTEXT_HOLDER = new TransmittableThreadLocal<>();

  public static void set(UserDTO userDTO) {
    CONTEXT_HOLDER.set(userDTO);
  }

  public static UserDTO get() {
    return CONTEXT_HOLDER.get();
  }

  @Async
  public void test() {
    System.out.println(Thread.currentThread());
    System.out.println(get());
  }

  @Async
  public void test2() {
    System.out.println(Thread.currentThread());
    UserDTO userDTO = get();
    System.out.println(userDTO);
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));
    for (int i = 0; i < 5; i++) {
      threadPoolExecutor.execute(() -> {
        UserDTO userDTO1 = get();
        System.out.println(userDTO1);
      });
    }
  }
}

