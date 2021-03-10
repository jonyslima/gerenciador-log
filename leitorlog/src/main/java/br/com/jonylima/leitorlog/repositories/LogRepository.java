package br.com.jonylima.leitorlog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.jonylima.leitorlog.dto.HourlyRequestDto;
import br.com.jonylima.leitorlog.entities.Log;

public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor<Log> {

	@Query(value = "select count(to_char(data, 'HH24')) as quantity,to_char(data, 'HH24') || ':00' as hour "
			+ "from log "
			+ "where ip = :ip "
			+ "GROUP BY IP,to_char(data, 'HH24') order by 2", nativeQuery = true)
	List<HourlyRequestDto> searchRequestPerHour(String ip);
}
