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
import smo.admin.web.batch.mappers.TipoOperacaoRowMapper;
import smo.admin.web.domain.TipoOperacao;

import java.io.IOException;

@Configuration
public class TipoOperacaoJobConfig extends AbstractJobConfig<TipoOperacao> {

    private static final String WILL_BE_INJECTED = null;

    @Bean
    @StepScope
    public PoiItemReader<TipoOperacao> tipoOperacaoItemReader(@Value("#{jobParameters['pathToFile']}") String pathToFile) throws IOException {
        PoiItemReader<TipoOperacao> reader = new PoiItemReader<>();
        reader.setLinesToSkip(2);
        assert pathToFile != null;
        reader.setResource(new UrlResource(pathToFile));
        reader.setRowMapper(tipoOperacaoRowMapper());
        return reader;
    }

    private RowMapper<TipoOperacao> tipoOperacaoRowMapper() {
        return new TipoOperacaoRowMapper();
    }

    @Override
    protected String setDefaultTopic() {
        return "tipo-operacao-topic";
    }

    @Override
    protected Converter<TipoOperacao, String> getKeyMapper() {
        return tipoOperacao -> String.valueOf(tipoOperacao.getIdGrupoCaixa());
    }

    @Bean
    public Job tipoOperacaoJob(
            JobCompletionListener listener, Step tipoOperacaoStep, JobRepository jobRepository
    ) {
        return new JobBuilder("tipoOperacaoJob", jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(tipoOperacaoStep).end().build();
    }

    @Bean
    public Step tipoOperacaoStep(
            JobRepository jobRepository, JpaTransactionManager transactionManager, TaskExecutor taskExecutor
    ) throws Exception {
        return new StepBuilder("tipoOperacaoStep", jobRepository).<TipoOperacao, TipoOperacao>chunk(
                50,
                transactionManager
        ).reader(tipoOperacaoItemReader(WILL_BE_INJECTED)).writer(kafkaItemWriter()).build();
    }
}

