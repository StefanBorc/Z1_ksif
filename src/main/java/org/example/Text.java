package org.example;

import lombok.Getter;

import java.io.*;
import java.util.ArrayList;

public class Text {
    public static String CESTA_SUBORU = "dictionary_5000.txt";
    public static String CESTA_ZT = "input.txt";
    @Getter
    private ArrayList<String> slovnik;
    @Getter
    private StringBuilder pridelenyText;

    public Text() throws IOException {
        slovnik = new ArrayList<>();
        pridelenyText = new StringBuilder();
        zapisZT();
    }

    private BufferedReader otvorSubor(String subor) throws FileNotFoundException {
        File slovaTxt = new File(subor);
        return new BufferedReader(new FileReader(slovaTxt));
    }

    protected void zapisDoSlovnika() throws IOException {
        BufferedReader br = otvorSubor(CESTA_SUBORU);
        String slovo;

        while ((slovo = br.readLine()) != null) {
            if (slovo.length() >= 5) {
                slovnik.add(slovo);
            }
        }
        br.close();
    }
    private void zapisZT() throws IOException {
        BufferedReader br = otvorSubor(CESTA_ZT);
        String slovo;

        while ((slovo = br.readLine()) != null) {
            pridelenyText.append(slovo);
        }
        br.close();
    }
}
