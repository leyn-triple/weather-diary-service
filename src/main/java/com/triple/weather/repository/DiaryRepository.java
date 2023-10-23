package com.triple.weather.repository;

import com.triple.weather.entity.Diary;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer>{
    List<Diary> findAllByDate(LocalDate date);

    List<Diary> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    Diary getFirstByDate(LocalDate date);

    @Transactional
    void deleteAllByDate(LocalDate date);
}
