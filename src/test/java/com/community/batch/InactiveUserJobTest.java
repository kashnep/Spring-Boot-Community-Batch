package com.community.batch;

import com.community.batch.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

import static com.community.batch.domain.enums.UserStatus.ACTIVE;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InactiveUserJobTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void 휴면_회원_전환_테스트() throws Exception {
		Date nowDate = new Date();
		// launchJob() 메서드로 Job을 실행시킴. 반환값으로 실행 결과에 대한 정보를 담고 있는 JobExecution이 반환된다.
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParametersBuilder().addDate("nowDate", nowDate).toJobParameters());

		// getStatus() 값이 COMPLETED로 출력되면 Job의 실행 여부 테스트는 성공한다.
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		assertEquals(11, userRepository.findAll().size());
		// 업데이트 날짜가 1년 전이며 User 상태값이 ACTIVE인 사용자들이 없어야 휴면회원 배치 테스트 성공
		assertEquals(0, userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1), ACTIVE).size());
	}
}
