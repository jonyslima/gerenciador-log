package br.com.jonylima.leitorlog.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.jonylima.leitorlog.dto.HourlyRequestDto;
import br.com.jonylima.leitorlog.dto.LogSearchDto;
import br.com.jonylima.leitorlog.entities.Log;
import br.com.jonylima.leitorlog.exceptions.ErroProcessarArquivoException;
import br.com.jonylima.leitorlog.exceptions.NotFoundException;
import br.com.jonylima.leitorlog.repositories.LogRepository;
import br.com.jonylima.leitorlog.services.LogService;
import br.com.jonylima.leitorlog.specification.LogSpec;

@Service
public class LogServiceImpl implements LogService {
	private static Logger LOG = LoggerFactory.getLogger(LogServiceImpl.class);

	@Autowired
	private LogRepository repository;

	@Value("${logs-folder}")
	private String logsFolder;

	private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public Page<Log> list(LogSearchDto search, Pageable pageable) {
		Specification<Log> specification = LogSpec.getSpecification(search);
		return repository.findAll(specification, pageable);
	}

	@Override
	public Log save(Log log) {
		return repository.save(log);
	}

	@Override
	public Log update(Long id, Log log) {
		findById(id).orElseThrow(NotFoundException::new);
		log.setId(id);
		return repository.save(log);
	}

	@Override
	public void delete(Long id) {
		findById(id).orElseThrow(NotFoundException::new);
		repository.deleteById(id);
	}

	@Override
	public Optional<Log> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void processFile(MultipartFile file) {
		try {
			String fileName = String.format("%s.log", UUID.randomUUID().toString());

			Path dest = Paths.get(logsFolder, fileName);

			if (Files.notExists(Paths.get(logsFolder))) {
				Files.createDirectory(Paths.get(logsFolder));
			}

			if (!Files.isWritable(Paths.get(logsFolder))) {
				LOG.error("A pasta {} não permite escrita",dest.toString());
				throw new ErroProcessarArquivoException("Erro ao transferir arquivo de log");
			}

			file.transferTo(dest);
			
			CompletableFuture.runAsync(() -> processFileAsync(dest));
			
		} catch (IllegalStateException | IOException ex) {
			LOG.error("Erro ao transferir arquivo de log", ex);
			throw new ErroProcessarArquivoException("Erro ao transferir arquivo de log", ex);
		}

		
	}

	private void processFileAsync(Path dest) {
		try (Stream<String> lines = Files.lines(dest)) {
			lines.parallel().map(this::toLog).forEach(this::save);
		} catch (IOException e) {
			LOG.error("Erro ao processar arquivo de log", e);
		}
	}

	private Log toLog(String logLine) {
		String arr[] = logLine.split("\\|");
		LocalDateTime data = LocalDateTime.parse(arr[0], FORMATTER);
		return new Log(data, arr[1], Integer.valueOf(arr[3]), arr[4]);
	}

	@Override
	public List<HourlyRequestDto> dashboard(String ip) {

		return repository.searchRequestPerHour(ip);
	}
	
	

}
