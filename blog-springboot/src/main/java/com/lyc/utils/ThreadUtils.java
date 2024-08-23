package com.lyc.utils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.concurrent.*;

import static com.lyc.constant.CommonConstant.LOG_OVERTIME;

/**
 * @author liuYichang
 * @description 线程工具类
 * @date 2023/5/2 10:19
 */
public class ThreadUtils {

    private static final Logger logger= LoggerFactory.getLogger(ThreadUtils.class);

    /**
     * 停止线程
     */
    public static void shutdownAndWaitTermination(ExecutorService pool){
        //可能该线程已经执行完毕，不需要操作
        if(pool!=null&& pool.isShutdown()){
            pool.shutdown();
            try {
                //如果超时了，调用shutdownNow，取消在workQueue中的pending的任务，并中断所有阻塞函数
                if(!pool.awaitTermination(LOG_OVERTIME, TimeUnit.SECONDS)){
                    pool.shutdownNow();
                    logger.info("Pool did not terminate");
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                //异常后强制退出
                Thread.currentThread().interrupt();
            }

        }
    }

    /**
     * 打印线程异常信息
     */
    public static void printException(Runnable r,Throwable t){
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            logger.error(t.getMessage(), t);
        }
    }

}
