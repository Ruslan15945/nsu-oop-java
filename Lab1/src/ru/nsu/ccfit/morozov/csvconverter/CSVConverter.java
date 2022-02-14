package ru.nsu.ccfit.morozov.csvconverter;

import java.io.*;
import java.util.*;

public class CSVConverter {

    private final List<Map.Entry<String, Integer>> rank = new LinkedList<>();

    private int count = 0;

    public static void main(String[] args) {
        if (args.length < 2){
            System.err.println("use in.txt out.scv");
            return;
        }
        CSVConverter converter = new CSVConverter();
        converter.convert(args[0], args[1]);
    }

    private boolean read(String inFileName){

        Reader reader = null;
        try
        {
            reader = new InputStreamReader(new FileInputStream(inFileName));
            StringBuilder buffer = new StringBuilder();
            Map<String, Integer> dictionary = new HashMap<>();
            count = 0;
            int c;
            while ((c = reader.read()) != -1) {
                if (Character.isLetterOrDigit(c)) {
                    buffer.append((char) c);
                }
                else if (!buffer.isEmpty()){
                    String word = buffer.toString();
                    if (dictionary.containsKey(word)){
                        dictionary.put(word, dictionary.get(word) + 1);
                    }
                    else {
                        dictionary.put(word, 1);
                    }
                    ++count;
                    buffer.setLength(0);
                }
            }

            rank.clear();
            rank.addAll(dictionary.entrySet());
            rank.sort((a, b) -> b.getValue() - a.getValue());

        }
        catch (IOException e)
        {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
            return false;
        }
        finally
        {
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.err);
                }
            }
        }

        return true;
    }


    private void write(String outFileName){
        if (count == 0){
            System.err.println("no data found");
            return;
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(outFileName));
            for (Map.Entry<String, Integer> record : rank) {
                writer.write("\"" + record.getKey() + "\";" + record.getValue() + ";" + String.format("%.2f", (double) 100 * record.getValue() / count) + System.lineSeparator());
                //System.out.println(record.getKey() + ";" + record.getValue() + ";" + String.format("%.2f", (double) 100 * record.getValue() / count));
            }
        }
        catch (IOException e)
        {
            System.err.println("Error while writing file: " + e.getLocalizedMessage());
        }
        finally
        {
            if (null != writer)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    public void convert(String inFileName, String outFileName){

        if (read(inFileName)){
            write(outFileName);
        }

    }
}
