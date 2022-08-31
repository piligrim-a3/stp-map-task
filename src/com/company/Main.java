package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> logs = new HashMap<>(); //Первое значение ключ, второе значение объем данных.

        try {
            BufferedReader reader = new BufferedReader(new FileReader("access.log")); //Пытаемся открыть файл для считывания.
            String line;
            String regex = " +"; //Фильтр для разделения строки, любое кол-во пробелов будет являться разделителем.

            int key = 1;
            while ((line = reader.readLine()) != null) {
                key++;
                String[] split;
                split = line.split(regex);

                //Исходя из структуры log файла, 4 элемент будет являться необходимым для нас количеством байт.
                //Поэтому добавляем ключ - номер считываемой линии и само количество байт.
                logs.put(key, Integer.parseInt(split[4]));
            }
            logs.forEach((key1, value) -> System.out.println("key:" + key1 + " value: " + value)); //Для каждого элемента имеем его количество байт
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
