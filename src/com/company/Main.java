package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> logs = new HashMap<>(); //Первое значение ключ, второе значение объем данных

        try {
            BufferedReader reader = new BufferedReader(new FileReader("access.log")); //Пытаемся открыть файл для считывания
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
