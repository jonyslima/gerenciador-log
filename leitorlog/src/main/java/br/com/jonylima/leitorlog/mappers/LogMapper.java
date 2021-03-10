package br.com.jonylima.leitorlog.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.jonylima.leitorlog.dto.LogDto;
import br.com.jonylima.leitorlog.entities.Log;

@Mapper(componentModel = "spring")
public interface LogMapper {
	LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

	Log logDtoToLog(LogDto log);

	LogDto logToLogDto(Log log);

}
