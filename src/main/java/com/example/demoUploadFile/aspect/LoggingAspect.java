package com.example.demoUploadFile.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Chọn method theo method cụ thể của một class
//    @Before("execution(* com.example.demoUploadFile.service.FileCloudService.getAll())")
//    public void beforeGetImage() {
//        System.out.println("Bắt đầu lấy dữ liệu");
//    }

    // Chọn method theo method cụ thể của một class
//    @After("execution(* com.example.demoUploadFile.service.FileCloudService.getAll())")
//    public void afterGetImage() {
//        System.out.println("Lấy xong image");
//    }

//    @Around("execution(* com.example.demoUploadFile.service.FileCloudService.getAll())")
//    public Object aroundGetImage(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("Lấy xong image");
//        Object result = joinPoint.proceed();
//
//        // Lấy tên method
//        System.out.println("Method: " + joinPoint.getSignature().getName());
//        return result;
//    }

//    @AfterReturning("execution(* com.example.demoUploadFile.service.FileCloudService.getAll())")
//    public void afterReturningImage() {
//        System.out.println("Đây là AfterReturning");
//    }

    // Chọn các method trong package service và các package con, với tham số bất kỳ.
//    @AfterReturning("execution(* com.example.demoUploadFile.service..*(..))")
//    public void afterReturningAnyParameter() {
//        System.out.println("Bắt đầu với tham số bất kỳ");
//    }

    // Chọn tất cả method của một class
    @AfterReturning("execution(* com.example.demoUploadFile.service.FileCloudService.*(..))")
    public void afterReturningAnyMethod() {
        System.out.println("Chọn tất cả method của class FileCloudService");
    }

    // Chọn method theo tham số
    @After("execution(* com.example.demoUploadFile.service.FileCloudService.getFileById(..))")
    public void afterWithParameter() {
        System.out.println("Lấy tên file theo tham số");
    }

    // Đo thời gian thực thi (getAll())
    @Around("execution(* com.example.demoUploadFile.service.FileCloudService.getFileById(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println(
                "Method " + joinPoint.getSignature().getName() + " chạy mất " + (end - start) + " ms"
        );
        return result;
    }

    // Chỉ chạy khi có exception, lấy được exception
    @AfterThrowing("execution(* com.example.demoUploadFile.service.FileCloudService.*(..))")
    public void afterThrowing() {
        System.out.println("Đây là AfterThrowing");
    }
}
