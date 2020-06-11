package com.spintech.testtask.dto;
import com.spintech.testtask.infrastructure.enums.TVShowStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTVShowDto {
	private Long tmdbId;
	private TVShowStatus tvShowStatus;
}
