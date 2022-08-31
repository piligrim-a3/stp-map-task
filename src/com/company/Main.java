package com.company;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> unsortedLogs = new HashMap<>(); //Первое значение ключ, второе значение объем данных.

        try {
            BufferedReader reader = new BufferedReader(new FileReader("access.log")); //Пытаемся открыть файл для считывания.
            String line;
            String regex = " +"; //Фильтр для разделения строки: любое кол-во пробелов будет являться разделителем.

            int lineNumber = 1;
            String[] split;
            while ((line = reader.readLine()) != null) {
                split = line.split(regex); //Разделяем строку используя фильтр.

                //Исходя из структуры log файла, 4 элемент будет являться необходимым для нас количеством байт.
                //Поэтому добавляем ключ - номер считываемой линии и само количество байт.
                unsortedLogs.put(lineNumber, Integer.parseInt(split[4]));
                lineNumber++;
            }


            //Сортируем данные по объему в порядке убывания
            Map<Integer, Integer> sortedLogs = unsortedLogs.entrySet().stream()
                    .sorted(Comparator.comparingInt(e -> -e.getValue()))
                    .collect(Collectors.toMap
                            (
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (k, v) -> { throw new Error(); },
                                    LinkedHashMap::new
                            )
                    );

            //Проверка. Вывод 10 первых(наибольших) объемов данных.
            //Имеем ключ(номер строки) и значение - объем данных.
            for (int i = 0; i < 10; i++) {
                System.out.println(sortedLogs.entrySet().toArray()[i]);
            }


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
