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
import smo.admin.web.batch.mappers.UnidadeInstituicaoRowMapper;
import smo.admin.web.domain.UnidadeInstituicao;

import java.io.IOException;

@Configuration
public class UnidadeInstituicaoJobConfig extends AbstractJobConfig<UnidadeInstituicao> {

    private static final String WILL_BE_INJECTED = null;

    @Bean
    @StepScope
    public PoiItemReader<UnidadeInstituicao> unidadeInstituicaoItemReader(@Value("#{jobParameters['pathToFile']}") String pathToFile) throws IOException {
        PoiItemReader<UnidadeInstituicao> reader = new PoiItemReader<>();
        reader.setLinesToSkip(2);
        assert pathToFile != null;
        reader.setResource(new UrlResource(pathToFile));
        reader.setRowMapper(unidadeInstituicaoRowMapper());
        return reader;
    }

    private RowMapper<UnidadeInstituicao> unidadeInstituicaoRowMapper() {
        return new UnidadeInstituicaoRowMapper();
    }

    @Override
    protected String setDefaultTopic() {
        return "unidade-instituicao-topic";
    }

    @Override
    protected Converter<UnidadeInstituicao, String> getKeyMapper() {
        return unidadeInstituicao -> String.valueOf(unidadeInstituicao.getIdUnidadeInstituicao());
    }

    @Bean
    public Job unidadeInstituicaoJob(
            JobCompletionListener listener, Step unidadeInstituicaoStep, JobRepository jobRepository
    ) {
        return new JobBuilder("unidadeInstituicaoJob", jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(unidadeInstituicaoStep).end().build();
    }

    @Bean
    public Step unidadeInstituicaoStep(
            JobRepository jobRepository, JpaTransactionManager transactionManager, TaskExecutor taskExecutor
    ) throws Exception {
        return new StepBuilder("unidadeInstituicaoStep", jobRepository).<UnidadeInstituicao, UnidadeInstituicao>chunk(
                50,
                transactionManager
        ).reader(unidadeInstituicaoItemReader(WILL_BE_INJECTED)).writer(kafkaItemWriter()).build();
    }
}

