package com.example.multithreadingtest.repository;

import com.example.multithreadingtest.model.IUserProjection;
import com.example.multithreadingtest.model.User;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 * userRepository.
 *
 * @Description TODO
 * @Date 11/01/2022 20:20
 * @Created by Qinxiu Wang
 */
public interface IUserRepository extends JpaRepository<User, Long> {

  @Async
  CompletableFuture<List<User>> findByEmail(String email);


  @Async
  default Future<List<User>> findByEmailAsync(String email) {
    System.out.println("process-" + Thread.currentThread().getName() + " sent");
    long currentTimeMillis = System.currentTimeMillis();
    Future<List<User>> response = new AsyncResult(findByEmail(email));
    System.out.println(
        "process-" + Thread.currentThread().getName() + " spent: " + (System.currentTimeMillis()
            - currentTimeMillis) + "ms");
    return response;
  }


  @Query(value = ""
      + "SELECT utb.id, utb.name, utb.email "
      + "FROM user_tb utb WHERE email = :email ", nativeQuery = true)
  @Async
  CompletableFuture<List<User>> findByEmailNative(String email);

//  default CompletableFuture<List<User>> findByEmailNativeAsync(String email) {
//    return findByEmail(email);
//  }
//  @Async
//  default Future<List<IUserProjection>> findByEmailNativeAsync(String email) {
//    System.out.println("process-" + Thread.currentThread().getName() + " sent");
//    long currentTimeMillis = System.currentTimeMillis();
//    Future<List<IUserProjection>> response = new AsyncResult(findByEmailNative(email));
//    System.out.println(
//        "process-" + Thread.currentThread().getName() + " spent: " + (System.currentTimeMillis()
//            - currentTimeMillis) + "ms");
//    return response;
//  }
}
