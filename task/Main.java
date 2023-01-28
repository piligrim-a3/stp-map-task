package task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("access.log"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}