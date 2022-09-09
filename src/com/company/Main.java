package com.company;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> unsortedLogs = new HashMap<>(); //Первое значение ключ, второе значение объем данных.
        String filename = "out.txt"; //Выходной файл.
        int targetAmount = 10;
        String key;
        int value;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("access.log")); //Пытаемся открыть файл для считывания.
            String line;
            String regex = " +"; //Фильтр для разделения строки: любое кол-во пробелов будет являться разделителем.
            String[] split;
            while ((line = reader.readLine()) != null) {
                split = line.split(regex); //Разделяем строку используя фильтр.

                //Исходя из структуры log файла, 4 элемент будет являться необходимым для нас количеством байт.
                //Ключом будет являться IP-адрес - 2 элемент файла. Если IP-адрес уже встрачался, увеличим его количество байт на соответствующую величину.
                key = split[2];
                value = Integer.parseInt(split[4]);

                if (unsortedLogs.containsKey(key)) {
                    value += unsortedLogs.get(key);
                }
                unsortedLogs.put(key, value);
            }
            reader.close();

            //Сортируем данные по объему в порядке убывания.
            Map<String, Integer> sortedLogs = unsortedLogs.entrySet().stream()
                    .sorted(Comparator.comparingInt(e -> -e.getValue()))
                    .collect(Collectors.toMap
                            (
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (k, v) -> {
                                        throw new Error();
                                    },
                                    LinkedHashMap::new
                            )
                    );

            Map<String, Integer> highest = new HashMap<>();
            int count = 0;
            //Запишем 10 IP-адресов с наибольшим количеством байт в новую коллекцию.
            for (Map.Entry<String, Integer> entry: sortedLogs.entrySet()) {
                highest.put(entry.getKey(), entry.getValue());
                count++;
                if (count >= targetAmount) {
                    break;
                }
            }

            new File(filename); //Создаем выходной файл.
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            //Запишем все пары ключ-значение в выходной файл.
            for (Map.Entry<String, Integer> entry: highest.entrySet()) {
                writer.write("IP-address: " + entry.getKey() + " bytes: " + entry.getValue() + System.lineSeparator());
            }
            writer.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
