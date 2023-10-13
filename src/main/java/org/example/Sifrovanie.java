package org.example;

import java.io.IOException;
import java.util.*;

public class Sifrovanie {
    private StringBuilder zt;
    private ArrayList<String> mozneSlova;
    private ArrayList<ArrayList<String>> vysledneKombinacie;
    private Text text;

    public Sifrovanie() throws IOException {
        text = new Text();
        vysledneKombinacie = new ArrayList<>();
        mozneSlova = new ArrayList<>();
        zt = text.getPridelenyText();
        otestujMozneSlova();
        najdiKombinacie(zt.length(), 0, new ArrayList<>(), vysledneKombinacie);
    }

    private Boolean skontrolujSlovo(String slovo) {
        StringBuilder pomocna = new StringBuilder();
        pomocna.append(zt);
        for (int i = 0; i < slovo.length(); i++) {
            int n = pomocna.indexOf(String.valueOf(slovo.charAt(i)));
            if (n != -1) {
                pomocna.deleteCharAt(n);
            } else {
                return false;
            }
        }
        return true;
    }

    private String zoradPodlaAbecedy(String slovo) {
        ArrayList<Character> zoradeneSlovo = new ArrayList<>();
        for (int i = 0; i < slovo.length(); i++) {
            zoradeneSlovo.add(slovo.charAt(i));
        }
        Collections.sort(zoradeneSlovo);
        String zSlovo = new String();
        for (int i = 0; i < slovo.length(); i++) {
            zSlovo += zoradeneSlovo.get(i);
        }
        return zSlovo;
    }

    private Boolean skontrolujKombinaciu(StringBuilder slovo) {
        String zoradeneSlovo = zoradPodlaAbecedy(slovo.toString());
        if (zoradeneSlovo.equals(zt.toString())) {
            return true;
        } else {
            return false;
        }
    }

    private void otestujMozneSlova() throws IOException {
        text.zapisDoSlovnika();
        for (String s : text.getSlovnik()) {
            String slovo = zoradPodlaAbecedy(s);
            if (skontrolujSlovo(slovo)) {
                mozneSlova.add(s);
            }
        }
    }

    private boolean jeDuplicitne(String testovaneSlovo, ArrayList<String> kombinacia) {
        return kombinacia.contains(testovaneSlovo);
    }

    private void najdiKombinacie(int ziadanaDlzka, int index, ArrayList<String> kombinacia, ArrayList<ArrayList<String>> vysledneKombinacie) {
        if (ziadanaDlzka == 0) {
            StringBuilder k = new StringBuilder();
            for (String slovo : kombinacia) {
                k.append(slovo);
            }
            boolean jeKombinacia = skontrolujKombinaciu(k);
            if (jeKombinacia) {
                vysledneKombinacie.add(new ArrayList<>(kombinacia));
            }
            return;
        }
        for (int i = index; i < mozneSlova.size(); i++) {
            String slovo = mozneSlova.get(i);
            if (slovo.length() <= ziadanaDlzka) {
                if (!jeDuplicitne(slovo, kombinacia)) {
                    kombinacia.add(slovo);
                    najdiKombinacie(ziadanaDlzka - slovo.length(), i, kombinacia, vysledneKombinacie);
                    kombinacia.remove(kombinacia.size() - 1);
                }
            }
        }
    }

    protected void vypisVysledok() {
        for (var kombinacia : vysledneKombinacie) {
            Collections.sort(kombinacia);
            System.out.println(kombinacia);
        }
    }
}