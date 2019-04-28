package me.kjgleh.spring.thread;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

        SampleTask sampleTask1 = (SampleTask) context.getBean("sampleTask");
        sampleTask1.setName("Thread 1");
        taskExecutor.execute(sampleTask1);

        SampleTask sampleTask2 = (SampleTask) context.getBean("sampleTask");
        sampleTask2.setName("Thread 2");
        taskExecutor.execute(sampleTask2);

        SampleTask sampleTask3 = (SampleTask) context.getBean("sampleTask");
        sampleTask3.setName("Thread 3");
        taskExecutor.execute(sampleTask3);

        for (;;) {
            int count = taskExecutor.getActiveCount();
            System.out.println("Active Threads : " + count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 0) {
                taskExecutor.shutdown();
                break;
            }
        }
    }
}
