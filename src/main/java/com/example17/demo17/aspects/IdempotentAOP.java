//package com.example17.demo17.aspects;
//
//import jakarta.annotation.Resource;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * com.example17.demo17.aspects
// * ClassName: IdempotentAOP
// * Description:
// * Create by: wangjun
// * Date: 2024/2/6 18:07
// */
//@Slf4j
//@Aspect
//@Component
//public class IdempotentAOP {
//    /** Redis前缀 */
//    private String API_IDEMPOTENT_CHECK = "API_IDEMPOTENT_CHECK:";
//
//    @Resource
//    private HttpServletRequest request;
//    @Resource
//    private RedisUtils redisUtils;
//
//    /**
//     * 定义切面
//     */
//    @Pointcut("@annotation(com.example17.demo17.annotations.NotRepeat)")
//    public void notRepeat() {
//    }
//
//    /**
//     * 在接口原有的方法执行前，将会首先执行此处的代码
//     */
//    @Before("notRepeat()")
//    public void doBefore(JoinPoint joinPoint) {
//        String uri = request.getRequestURI();
//
//        // 登录后才做校验
//        UserInfo loginUser = AuthUtil.getLoginUser();
//        if (loginUser != null) {
//            assert uri != null;
//            String key = loginUser.getAccount() + "_" + uri;
//            log.info(">>>>>>>>>> 【IDEMPOTENT】开始幂等性校验，加锁，account: {}，uri: {}", loginUser.getAccount(), uri);
//
//            // 加分布式锁
//            boolean lockSuccess = redisUtils.setIfAbsent(API_IDEMPOTENT_CHECK + key, "1", 30, TimeUnit.MINUTES);
//            log.info(">>>>>>>>>> 【IDEMPOTENT】分布式锁是否加锁成功:{}", lockSuccess);
//            if (!lockSuccess) {
//                if (uri.contains("contract/saveDraftContract")) {
//                    log.error(">>>>>>>>>> 【IDEMPOTENT】文件保存中，请稍后");
//                    throw new IllegalArgumentException("文件保存中，请稍后");
//
//                } else if (uri.contains("contract/saveContract")) {
//                    log.error(">>>>>>>>>> 【IDEMPOTENT】文件发起中，请稍后");
//                    throw new IllegalArgumentException("文件发起中，请稍后");
//                }
//            }
//        }
//    }
//
//    /**
//     * 在接口原有的方法执行后，都会执行此处的代码（final）
//     */
//    @After("notRepeat()")
//    public void doAfter(JoinPoint joinPoint) {
//        // 释放锁
//        String uri = request.getRequestURI();
//        assert uri != null;
//        UserInfo loginUser = SysUserUtil.getloginUser();
//        if (loginUser != null) {
//            String key = loginUser.getAccount() + "_" + uri;
//            log.info(">>>>>>>>>> 【IDEMPOTENT】幂等性校验结束，释放锁，account: {}，uri: {}", loginUser.getAccount(), uri);
//            redisUtils.del(API_IDEMPOTENT_CHECK + key);
//        }
//    }
//}
