package com.triple.weather.controller;

import com.triple.weather.entity.Diary;
import com.triple.weather.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;


    @ApiOperation(value="일기 텍스트와 날씨를 이용해서 DB에 일기 저장", notes = "세부 설명")
    @PostMapping("/create/diary")
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(value="날짜 형식 : yyyy-MM-dd", example="2023-10-23") LocalDate date,
                     @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

    @ApiOperation(value="선택한 날짜의 모든 일기 데이터를 가져옴", notes = "세부 설명")
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value="날짜 형식 : yyyy-MM-dd", example="2023-10-23")LocalDate date) {
//        if (date.isAfter(LocalDate.ofYearDay(3050, 1))) {
//            throw new InvalidDate();
//        }
        return diaryService.readDiary(date);
    }

    @ApiOperation(value="선택한 기간 중의 모든 일기 데이터를 가져옴", notes = "세부 설명")
    @GetMapping("/read/diaries")
    List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value="조회할 기간의 첫번째 날", example="2023-05-20")LocalDate startDate,
                            @RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value="조회할 기간의 마지막 날", example="2023-10-23")LocalDate endDate){
        return diaryService.readDiaries(startDate, endDate);
    }

    @ApiOperation(value="선택한 날짜의 첫번째 일기의 내용을 변경", notes = "세부 설명")
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value="날짜 형식 : yyyy-MM-dd", example="2023-10-23") LocalDate date,
                     @RequestBody String text) {
        diaryService.updateDiary(date, text);
    }

    @ApiOperation(value="선택한 날짜의 일기를 모두 삭제", notes = "세부 설명")
    @DeleteMapping("/delete/diary" )
    void deleteDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE)@ApiParam(value="날짜 형식 : yyyy-MM-dd", example="2023-10-23") LocalDate date) {
        diaryService.deleteDiary(date);
    }

    /**
     * 예외처리
     */

}
