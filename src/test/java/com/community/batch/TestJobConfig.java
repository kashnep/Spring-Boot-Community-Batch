package com.community.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1. @EnableBatchProcessing : 스프링 부트 배치 스타터에 미리 정의된 설정들을 실행시키는
 * 어노테이션으로 배치에 필요한 JobBuilder, StepBuilder, JobRepository, JobLauncher 등
 * 다양한 설정이 자동으로 주입된다.
 * 2. 20Line : Job 실행에 필요한 JobLauncher를 필드값으로 갖는 JobLauncherTestUtils를 빈으로 등록한다.
 */
@EnableBatchProcessing
@Configuration
public class TestJobConfig {

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }
}
