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
import smo.admin.web.batch.mappers.MovimentacaoPaRowMapper;
import smo.admin.web.domain.MovimentacaoPa;

import java.io.IOException;

@Configuration
public class MovimentacaoPaJobConfig extends AbstractJobConfig<MovimentacaoPa> {

    private static final String WILL_BE_INJECTED = null;

    @Bean
    @StepScope
    public PoiItemReader<MovimentacaoPa> movimentacaoPaItemReader(@Value("#{jobParameters['pathToFile']}") String pathToFile) throws IOException {
        PoiItemReader<MovimentacaoPa> reader = new PoiItemReader<>();
        reader.setLinesToSkip(2);
        assert pathToFile != null;
        reader.setResource(new UrlResource(pathToFile));
        reader.setRowMapper(movimentacaoPaRowMapper());
        return reader;
    }

    private RowMapper<MovimentacaoPa> movimentacaoPaRowMapper() {
        return new MovimentacaoPaRowMapper();
    }

    @Override
    protected String setDefaultTopic() {
        return "movimentacao-pa-topic";
    }

    @Override
    protected Converter<MovimentacaoPa, String> getKeyMapper() {
        return movimentacaoPa -> String.valueOf(movimentacaoPa.getIdPontoAtendimento());
    }

    @Bean
    public Job movimentacaoPaJob(
            JobCompletionListener listener, Step movimentacaoPaStep, JobRepository jobRepository
    ) {
        return new JobBuilder("movimentacaoPaJob", jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(movimentacaoPaStep).end().build();
    }

    @Bean
    public Step movimentacaoPaStep(
            JobRepository jobRepository, JpaTransactionManager transactionManager, TaskExecutor taskExecutor
    ) throws Exception {
        return new StepBuilder("movimentacaoPaStep", jobRepository).<MovimentacaoPa, MovimentacaoPa>chunk(
                50,
                transactionManager
        ).reader(movimentacaoPaItemReader(WILL_BE_INJECTED)).writer(kafkaItemWriter()).build();
    }
}

