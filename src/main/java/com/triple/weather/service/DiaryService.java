package com.triple.weather.service;

import com.triple.weather.entity.Diary;
import com.triple.weather.repository.DiaryRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    private String apiKey = "754252797a69151c35f86f52161c1914";

    public void createDiary(LocalDate date, String text) {

        // open weather map에서 날씨 데이터 가져오기
        String weatherData = getWeatherString();

        //받아온 날씨 json 파싱하기
        Map<String, Object> parseWeather = parseWeather(weatherData);

        //파싱된 데이터 + 일기 값 우리 db에 넣기
        Diary nowDiary = new Diary();
        nowDiary.setWeather(parseWeather.get("main").toString());
        nowDiary.setIcon(parseWeather.get("icon").toString());
        nowDiary.setTemperature((Double)parseWeather.get("temp"));
        nowDiary.setText(text);
        nowDiary.setDate(date);
        diaryRepository.save(nowDiary);

    }

    public List<Diary> readDiary(LocalDate date) {
        //서비스에서 db를 조회하려면 레포지토리를 통해야 한다.
        return diaryRepository.findAllByDate(date);
    }

    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        //서비스에서 db를 조회하려면 레포지토리를 통해야 한다.
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    public void updateDiary(LocalDate date, String text) {
        Diary nowDiary = diaryRepository.getFirstByDate(date);
        nowDiary.setText(text);
        diaryRepository.save(nowDiary);
    }

    private String getWeatherString() {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            } else {
                br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null ){
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        }catch (Exception e) {
            return "failed to get response";
        }

    }

    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> resultMap = new HashMap<>();

        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }
}
