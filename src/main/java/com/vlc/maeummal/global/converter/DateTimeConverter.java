package com.vlc.maeummal.global.converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    // 메서드에 static 키워드를 올바르게 사용
    public static String convertStringFromLocalDateTime(LocalDateTime dateTime) {
        // 원하는 형식으로 포맷팅
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = dateTime.format(outputFormatter);
        return formattedDate;
    }

}
