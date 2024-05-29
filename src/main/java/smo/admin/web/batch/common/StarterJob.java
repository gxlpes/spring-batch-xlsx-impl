package smo.admin.web.batch.common;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@Component
public class StarterJob {
    JobLauncher jobLauncher;

    public StarterJob(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public void runJob(
            Job job,
            MultipartFile multipartFile
    ) throws IOException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "batch_upload_temp");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        Path tempFile = Files.createTempFile(tempDir.toPath(), "temp_", multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        String pathToFile = "file:" + tempFile.toString();

        JobParameters jobParameters =
                new JobParametersBuilder().addDate("currentTime", new Date())
                        .addString("pathToFile", pathToFile).toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
