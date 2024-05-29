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
import smo.admin.web.batch.mappers.TipoTerminalRowMapper;
import smo.admin.web.domain.TipoTerminal;

import java.io.IOException;

@Configuration
public class TransportadoraValorJobConfig extends AbstractJobConfig<TipoTerminal> {

    private static final String WILL_BE_INJECTED = null;

    @Bean
    @StepScope
    public PoiItemReader<TipoTerminal> transportadoraValorItemReader(@Value("#{jobParameters['pathToFile']}") String pathToFile) throws IOException {
        PoiItemReader<TipoTerminal> reader = new PoiItemReader<>();
        reader.setLinesToSkip(2);
        assert pathToFile != null;
        reader.setResource(new UrlResource(pathToFile));
        reader.setRowMapper(transportadoraValorRowMapper());
        return reader;
    }

    private RowMapper<TipoTerminal> transportadoraValorRowMapper() {
        return new TipoTerminalRowMapper();
    }

    @Override
    protected String setDefaultTopic() {
        return "transportadora-topic";
    }

    @Override
    protected Converter<TipoTerminal, String> getKeyMapper() {
        return transportadoraValor -> String.valueOf(transportadoraValor.getIdTipoTerminal());
    }

    @Bean
    public Job transportadoraValorJob(
            JobCompletionListener listener, Step transportadoraValorStep, JobRepository jobRepository
    ) {
        return new JobBuilder("transportadoraValorJob", jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(transportadoraValorStep).end().build();
    }

    @Bean
    public Step transportadoraValorStep(
            JobRepository jobRepository, JpaTransactionManager transactionManager, TaskExecutor taskExecutor
    ) throws Exception {
        return new StepBuilder("transportadoraValorStep", jobRepository).<TipoTerminal, TipoTerminal>chunk(
                50,
                transactionManager
        ).reader(transportadoraValorItemReader(WILL_BE_INJECTED)).writer(kafkaItemWriter()).build();
    }
}

