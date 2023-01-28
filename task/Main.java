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
                
                //Если ip уже встречался, увеличиваем значение и добавляем его
                if(logs.containsKey(ip)){
                    logs.put(ip, bytes + logs.get(ip));
                }
                //Если ip не встречался, добавляем его
                else{
                    logs.put(ip, bytes);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(logs);
    }
}