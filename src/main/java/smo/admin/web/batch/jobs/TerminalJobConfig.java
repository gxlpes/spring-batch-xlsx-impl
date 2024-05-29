package smo.admin.web.batch.jobs;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.UrlResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import smo.admin.web.batch.common.AbstractJobConfig;
import smo.admin.web.batch.listeners.JobCompletionListener;
import smo.admin.web.batch.mappers.TerminalRowMapper;
import smo.admin.web.domain.Terminal;

import java.io.IOException;

@Configuration
public class TerminalJobConfig extends AbstractJobConfig<Terminal> {

    private static final String WILL_BE_INJECTED = null;

    @Bean
    @StepScope
    public PoiItemReader<Terminal> terminalItemReader(@Value("#{jobParameters['pathToFile']}") String pathToFile) throws IOException {
        PoiItemReader<Terminal> reader = new PoiItemReader<>();
        reader.setLinesToSkip(2);
        assert pathToFile != null;
        reader.setResource(new UrlResource(pathToFile));
        reader.setRowMapper(terminalRowMapper());
        return reader;
    }

    private RowMapper<Terminal> terminalRowMapper() {
        return new TerminalRowMapper();
    }

    @Override
    protected String setDefaultTopic() {
        return "terminal-topic";
    }

    @Override
    protected Converter<Terminal, String> getKeyMapper() {
        return terminal -> String.valueOf(terminal.getIdTerminal());
    }

    @Bean
    public Job terminalJob(
            JobCompletionListener listener, Step terminalStep, JobRepository jobRepository
    ) {
        return new JobBuilder("terminalJob", jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(terminalStep).end().build();
    }

    @Bean
    public Step terminalStep(
            JobRepository jobRepository, JpaTransactionManager transactionManager, TaskExecutor taskExecutor
    ) throws Exception {
        return new StepBuilder("terminalStep", jobRepository).<Terminal, Terminal>chunk(
                50,
                transactionManager
        ).reader(terminalItemReader(WILL_BE_INJECTED)).writer(kafkaItemWriter()).build();
    }
}

