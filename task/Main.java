package task;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

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
        catch (IOException e) {
            e.printStackTrace();
        }

        //Создаем новую коллекцию, отсортированную по количеству байт и состоящую из 10 ip 
        Map<String,Integer> sortedLogs =
                logs.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(10)
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                            )
                        );

                        
        //Создаем файл с ip, отсортированными по количеству байт
        new File("output.txt");
        try {
            FileWriter writer = new FileWriter("output.txt");
            for (Map.Entry<String, Integer> entry: sortedLogs.entrySet()) {
                writer.write("Ip-address: " + entry.getKey() + " bytes: " + entry.getValue() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}