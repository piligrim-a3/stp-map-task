package com.company;

import java.io.*;
import java.util.*;
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


            //Сортируем данные по объему в порядке убывания.
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


            ArrayList<Integer> bytes = new ArrayList<>(); //Массив для хранения 10 значений указывающих на номера строк с наибольших количеством байт.
            for (int i = 0; i < 10; i++) {
                bytes.add((Integer) sortedLogs.keySet().toArray()[i]);
            }
            Collections.sort(bytes); //Отсортируем номера строк для по возрастанию, чтобы в дальнейшем упростить поиск.

            reader.close();

            reader = new BufferedReader(new FileReader("access.log"));
            lineNumber = 1;


            //Пройдем по файлу еще раз, сохраняя информацию о 10-ти строках, которые имеют наибольшее количество байт.
            while ((line = reader.readLine()) != null) {
                if (lineNumber == bytes.get(0)) {
                    System.out.println(line);
                    bytes.remove(0);
                }
                if (bytes.size() == 0) {
                    break;
                }
                lineNumber++;
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
