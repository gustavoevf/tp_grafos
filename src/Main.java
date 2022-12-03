import com.google.gson.Gson;

import java.io.FileReader;


public class Main {

    public static void main(String[] args) throws java.io.FileNotFoundException {
        Gson gson = new Gson();
        City[] cities = gson.fromJson(new FileReader("data/br.json"), City[].class);
        for (int i = 0; i < cities.length; i++) {
        System.out.println(cities[i].city);
        }
    }
}
