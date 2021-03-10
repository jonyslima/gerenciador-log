package br.com.jonylima.leitorlog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import br.com.jonylima.leitorlog.dto.HourlyRequestDto;
import br.com.jonylima.leitorlog.dto.LogSearchDto;
import br.com.jonylima.leitorlog.entities.Log;

public interface LogService {
	public Page<Log> list(LogSearchDto search, Pageable pageable);

	public Log save(Log log);

	public Log update(Long id, Log log);
	public void delete(Long id);

	public Optional<Log> findById(Long id);

	public void processFile(MultipartFile file);
	
	public List<HourlyRequestDto> dashboard(String ip);
}
