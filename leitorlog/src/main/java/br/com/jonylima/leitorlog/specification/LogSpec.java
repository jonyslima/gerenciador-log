package br.com.jonylima.leitorlog.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.jonylima.leitorlog.dto.LogSearchDto;
import br.com.jonylima.leitorlog.entities.Log;

public class LogSpec {

	public static Specification<Log> getSpecification(LogSearchDto search) {
		List<Specification<Log>> specs = new ArrayList<>();

		if (StringUtils.isNotBlank(search.getUserAgent())) {
			specs.add(userAgentContains(search.getUserAgent()));
		}

		if (StringUtils.isNotBlank(search.getIp())) {
			specs.add(ipContains(search.getIp()));
		}

		if (Objects.nonNull(search.getStartDate())) {
			specs.add(dateGe(search.getStartDate()));
		}

		if (Objects.nonNull(search.getEndDate())) {
			specs.add(dateLe(search.getEndDate()));
		}

		if (Objects.nonNull(search.getStatus())) {
			specs.add(statusEqual(search.getStatus()));
		}

		return specs.stream().reduce((a, b) -> a.and(b)).orElseGet(LogSpec::listAll);
	}

	public static Specification<Log> userAgentContains(String userAgent) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("userAgent")),
				"%" + userAgent.toLowerCase() + "%");
	}

	public static Specification<Log> ipContains(String ip) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("ip"), "%" + ip + "%");
	}

	public static Specification<Log> dateGe(LocalDateTime date) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("data"), date);
	}

	public static Specification<Log> dateLe(LocalDateTime date) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("data"), date);
	}

	public static Specification<Log> statusEqual(Integer status) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
	}

	public static Specification<Log> listAll() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.and();
	}

}
