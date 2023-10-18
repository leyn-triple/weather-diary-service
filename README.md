# weather-diary-service

# API

**1) POST / create / diary**

    - date parameter 로 받기 (date 형식 : yyyy-MM-dd)
    - text parameter 로 일기 글 받기
    - 외부 API 에서 받아온 날씨 데이터와 함께 DB에 저장
    
**2) GET / read / diary**

    - date parameter 로 조회할 날짜를 받기
    - 해당 날짜의 일기를 List 형태로 반환
    
**3) GET / read / diaries**

    - startDate, ednDate parameter 로 조회할 날짜 기간의 시작일/종료일을 받기
    - 해당 기간의 일기를 List 형태로 반환
    
**4) PUT / update / diary**

    - date parameter 로 수정할 날짜를 받기
    - text parameter 로 수정할 새 일기 글을 받기
    - 해당 날짜의 첫번째 일기 글을 새로 받아온 일기글로 수정
