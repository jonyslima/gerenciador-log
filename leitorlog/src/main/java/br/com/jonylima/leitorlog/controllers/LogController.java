package br.com.jonylima.leitorlog.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.jonylima.leitorlog.dto.LogDto;
import br.com.jonylima.leitorlog.dto.LogSearchDto;
import br.com.jonylima.leitorlog.entities.Log;
import br.com.jonylima.leitorlog.mappers.LogMapper;
import br.com.jonylima.leitorlog.services.LogService;

@RestController
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogMapper logMapper;

	@Autowired
	private LogService service;

	@GetMapping
	public ResponseEntity<?> list(LogSearchDto logSearchDto, Pageable pageable) {
		Page<Log> page = service.list(logSearchDto, pageable);
		return ResponseEntity.ok(page.map(logMapper::logToLogDto));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Optional<Log> log = service.findById(id);
		return ResponseEntity.of(log.map(logMapper::logToLogDto));
	}
	
	@GetMapping(path = "/dashboard")
	public ResponseEntity<?> dashboard(@RequestParam("ip") String ip) {
		return ResponseEntity.ok(service.dashboard(ip));
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody LogDto logDto) {
		Log log = logMapper.logDtoToLog(logDto);
		return ResponseEntity.ok(logMapper.logToLogDto(service.save(log)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody LogDto logDto) {
		Log log = logMapper.logDtoToLog(logDto);
		return ResponseEntity.ok(logMapper.logToLogDto(service.update(id, log)));
	}

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		service.processFile(file);
		return ResponseEntity.accepted().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}
