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
public class SaldoInicialJobConfig extends AbstractJobConfig<MovimentacaoPa> {

    private static final String WILL_BE_INJECTED = null;

    @Bean
    @StepScope
    public PoiItemReader<MovimentacaoPa> saldoInicialItemReader(@Value("#{jobParameters['pathToFile']}") String pathToFile) throws IOException {
        PoiItemReader<MovimentacaoPa> reader = new PoiItemReader<>();
        reader.setLinesToSkip(2);
        assert pathToFile != null;
        reader.setResource(new UrlResource(pathToFile));
        reader.setRowMapper(saldoInicialRowMapper());
        return reader;
    }

    private RowMapper<MovimentacaoPa> saldoInicialRowMapper() {
        return new MovimentacaoPaRowMapper();
    }

    @Override
    protected String setDefaultTopic() {
        return "saldo-inicial-topic";
    }

    @Override
    protected Converter<MovimentacaoPa, String> getKeyMapper() {
        return saldo -> String.valueOf(saldo.getValor());
    }

    @Bean
    public Job saldoInicialJob(
            JobCompletionListener listener, Step saldoInicialStep, JobRepository jobRepository
    ) {
        return new JobBuilder("saldoInicialJob", jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(saldoInicialStep).end().build();
    }

    @Bean
    public Step saldoInicialStep(
            JobRepository jobRepository, JpaTransactionManager transactionManager, TaskExecutor taskExecutor
    ) throws Exception {
        return new StepBuilder("saldoInicialStep", jobRepository).<MovimentacaoPa, MovimentacaoPa>chunk(
                50,
                transactionManager
        ).reader(saldoInicialItemReader(WILL_BE_INJECTED)).writer(kafkaItemWriter()).build();
    }
}

