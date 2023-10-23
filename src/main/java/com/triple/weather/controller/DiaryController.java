package com.triple.weather.controller;

import com.triple.weather.entity.Diary;
import com.triple.weather.error.InvalidDate;
import com.triple.weather.service.DiaryService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;

    /**
     *날짜에 따른 일기 생성
     */
    @PostMapping("/create/diary")
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                     @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

    /**
     *특정 하루 일기 조회
     */
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
//        if (date.isAfter(LocalDate.ofYearDay(3050, 1))) {
//            throw new InvalidDate();
//        }
        return diaryService.readDiary(date);
    }

    /**
     *기간의 모든 일기 조회
     */
    @GetMapping("/read/diaries")
    List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
                            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate endDate){
        return diaryService.readDiaries(startDate, endDate);
    }

    /**
     *이미 쓴 일기 수정
     */
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
                     @RequestBody String text) {
        diaryService.updateDiary(date, text);
    }

    /**
     *이미 쓴 일기 삭제
     */
    @DeleteMapping("/delete/diary" )
    void deleteDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        diaryService.deleteDiary(date);
    }

    /**
     * 예외처리
     */

}
