package com.edu.Institiute.repo;

import com.edu.Institiute.dto.ScheduleDto;
import com.edu.Institiute.entity.MedicalHistory;
import com.edu.Institiute.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepo extends JpaRepository<Schedule,Integer> {

    @Query(value = "SELECT * FROM schedule WHERE id=:scheduleId", nativeQuery = true)
    Schedule getallScheduleForProvidedId(@Param("scheduleId") String scheduleId);

    @Query(value = "SELECT * FROM schedule WHERE id=:scheduleId", nativeQuery = true)
    Optional<Schedule> scheduleById(@Param("scheduleId") String scheduleId);

    @Query(value = "SELECT * FROM schedule WHERE id=:scheduleId", nativeQuery = true)
    List<Schedule> getAllSchedule(@Param("scheduleId") String scheduleId);


}
