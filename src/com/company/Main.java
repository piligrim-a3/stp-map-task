package com.company;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> unsortedLogs = new HashMap<>(); //Первое значение ключ, второе значение объем данных.
        String filename = "out.txt"; //Выходной файл.
        int targetAmount = 10;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("access.log")); //Пытаемся открыть файл для считывания.
            Files.createFile(Paths.get(filename)); //Создаем файл для сохранения результата
            String line;
            String regex = " +"; //Фильтр для разделения строки: любое кол-во пробелов будет являться разделителем.

            int lineNumber = 1;
            String[] split;

            String key;
            int value;
            while ((line = reader.readLine()) != null) {
                split = line.split(regex); //Разделяем строку используя фильтр.

                //Исходя из структуры log файла, 4 элемент будет являться необходимым для нас количеством байт.
                //Ключом будет являться IP-адрес. Если IP-адрес повторяется, увеличим количество байт на соответствующую величину.
                key = split[2];
                value = Integer.parseInt(split[4]);

                if (unsortedLogs.containsKey(key)) {
                    value += unsortedLogs.get(key);
                }
                unsortedLogs.put(key, value);
                lineNumber++;
            }


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

            //Запишем все пары ключ-значение в выходной файо.
            for (Map.Entry<String, Integer> entry: highest.entrySet()) {
                Files.writeString(Paths.get(filename), "IP-address: " + entry.getKey() + " bytes: " + entry.getValue() + "\n", StandardOpenOption.APPEND);
            }

            reader.close();
        } catch (FileAlreadyExistsException e) {
            System.out.println("File with name " + filename + " already exists");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
