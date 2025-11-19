package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.ScheduleDto;
import com.edu.Institiute.entity.Schedule;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule dtoToScheduleEntity(ScheduleDto scheduleDto);

    ScheduleDto toScheduleDto(Schedule schedule);

}
