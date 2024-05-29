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
import smo.admin.web.batch.mappers.UsuarioRowMapper;
import smo.admin.web.domain.Usuario;

import java.io.IOException;

@Configuration
public class UsuarioJobConfig extends AbstractJobConfig<Usuario> {

    private static final String WILL_BE_INJECTED = null;

    @Bean
    @StepScope
    public PoiItemReader<Usuario> usuarioItemReader(@Value("#{jobParameters['pathToFile']}") String pathToFile) throws IOException {
        PoiItemReader<Usuario> reader = new PoiItemReader<>();
        reader.setLinesToSkip(2);
        assert pathToFile != null;
        reader.setResource(new UrlResource(pathToFile));
        reader.setRowMapper(usuarioRowMapper());
        return reader;
    }

    private RowMapper<Usuario> usuarioRowMapper() {
        return new UsuarioRowMapper();
    }

    @Override
    protected String setDefaultTopic() {
        return "transportadora-topic";
    }

    @Override
    protected Converter<Usuario, String> getKeyMapper() {
        return usuario -> String.valueOf(usuario.getIdUsuario());
    }

    @Bean
    public Job usuarioJob(
            JobCompletionListener listener, Step usuarioStep, JobRepository jobRepository
    ) {
        return new JobBuilder("usuarioJob", jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(usuarioStep).end().build();
    }

    @Bean
    public Step usuarioStep(
            JobRepository jobRepository, JpaTransactionManager transactionManager, TaskExecutor taskExecutor
    ) throws Exception {
        return new StepBuilder("usuarioStep", jobRepository).<Usuario, Usuario>chunk(
                50,
                transactionManager
        ).reader(usuarioItemReader(WILL_BE_INJECTED)).writer(kafkaItemWriter()).build();
    }
}

