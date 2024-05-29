package smo.admin.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import smo.admin.web.batch.common.StarterJob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StartController {

    private final Job usuarioJob;
    private final Job unidadeInstituicaoJob;
    private final Job transportadoraValorJob;
    private final Job tipoTerminalJob;
    private final Job terminalJob;
    private final Job movimentacaoPaJob;
    private final Job tipoOperacaoJob;
    private final Job saldoInicialJob;
    private final StarterJob starterJob;

    public StartController(
            @Qualifier("usuarioJob") Job usuarioJob,
            @Qualifier("unidadeInstituicaoJob") Job unidadeInstituicaoJob,
            @Qualifier("transportadoraValorJob") Job transportadoraValorJob,
            @Qualifier("tipoTerminalJob") Job tipoTerminalJob,
            @Qualifier("terminalJob") Job terminalJob,
            @Qualifier("movimentacaoPaJob") Job movimentacaoPaJob,
            @Qualifier("tipoOperacaoJob") Job tipoOperacaoJob,
            @Qualifier("saldoInicialJob") Job saldoInicialJob,
            StarterJob starterJob
    ) {
        this.usuarioJob = usuarioJob;
        this.unidadeInstituicaoJob = unidadeInstituicaoJob;
        this.transportadoraValorJob = transportadoraValorJob;
        this.tipoTerminalJob = tipoTerminalJob;
        this.terminalJob = terminalJob;
        this.movimentacaoPaJob = movimentacaoPaJob;
        this.tipoOperacaoJob = tipoOperacaoJob;
        this.saldoInicialJob = saldoInicialJob;
        this.starterJob = starterJob;
    }

    @PostMapping("/start-job")
    public ResponseEntity<?> startJobsInSequence(
            @RequestParam("unidade") MultipartFile unidadeFile,
            @RequestParam("tipo-terminal") MultipartFile tipoTerminalFile,
            @RequestParam("usuario") MultipartFile usuarioFile,
            @RequestParam("transportadora") MultipartFile transportadora,
            @RequestParam("terminal") MultipartFile terminalFile
    ) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, IOException, JobParametersInvalidException, JobRestartException {

        starterJob.runJob(unidadeInstituicaoJob, unidadeFile);
        starterJob.runJob(tipoTerminalJob, tipoTerminalFile);
        starterJob.runJob(transportadoraValorJob, usuarioFile);
        starterJob.runJob(usuarioJob, transportadora);
        starterJob.runJob(terminalJob, terminalFile);

        return ResponseEntity.ok("All jobs started successfully");
    }

    @PostMapping("/start-tipo-operacao")
    public ResponseEntity<?> startJobTipoOperacao(@RequestParam("tipo-operacao") MultipartFile tipoOperacaoFile, @RequestParam("saldo-inicial") MultipartFile saldoInicialFile) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, IOException, JobParametersInvalidException, JobRestartException {

        starterJob.runJob(tipoOperacaoJob, tipoOperacaoFile);
        starterJob.runJob(saldoInicialJob, saldoInicialFile);

        return ResponseEntity.ok("All jobs started successfully");
    }

}
