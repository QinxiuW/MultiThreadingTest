package com.example.multithreadingtest;

import com.example.multithreadingtest.model.User;
import com.example.multithreadingtest.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * UserServiceTest.
 *
 * @Description TODO
 * @Date 13/05/2022 22:59
 * @Created by Qinxiu Wang
 */
@SpringBootTest
public class UserServiceTest {

  @Autowired
  private UserService userService;


  @BeforeEach
  void save_users() {
    userService.saveUsers();
  }


  /**
   * Worked
   */
  @Test
  void dumb_async_testing() throws ExecutionException, InterruptedException {
    long startTime = System.currentTimeMillis();
    List<Future<String>> futureList = new ArrayList<>();
    List<String> finalResult = new ArrayList<>();

    for (int x = 1; x <= 5; x++) {
      Future<String> temp = userService.nativeMethod("" + 1);
      futureList.add(temp);
    }

    for (Future<String> future : futureList) {
      finalResult.add(future.get());
    }

    System.out.println(finalResult);
    System.out.println(
        "total time spent: " + (System.currentTimeMillis() - startTime) + " ms");
  }
}
