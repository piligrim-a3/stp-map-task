package task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        Map<String, Integer> logs = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("access.log"));
            String line;
            String ip;
            int bytes;
            while ((line = bufferedReader.readLine()) != null) {
                //Пробел разделяет элементы log-файла
                ip = line.split(" +")[2]; // ip - 3й элемент
                bytes = Integer.parseInt(line.split(" +")[4]); //Количество байт - 5й элемент
                logs.put(ip, bytes); // Добавляем ip и количество байт
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(logs);
    }
}