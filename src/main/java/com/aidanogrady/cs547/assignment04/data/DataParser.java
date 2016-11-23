package com.aidanogrady.cs547.assignment04.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for handling the parsing of .arff files.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class DataParser {
    private static final String RELATION = "@relation ";
    private static final String ATTRIBUTE = "@attribute ";
    private static final String DATA = "@data";

    public static DataSet parseFile(String fileName, List<String> attributes) {
        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            String name = "";
            List<String> attr = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();
            // Extract
            while (line != null && !line.equals("@data")) {
                if (line.startsWith(RELATION)) {
                    name = line.replace(RELATION, "");
                } else if (line.startsWith(ATTRIBUTE)) {
                    String[] split = line.replace(ATTRIBUTE, "").split(" ");
                    if (split.length == 2) {
                        attr.add(split[0]);
                    }
                }
                line = br.readLine();
            }

            for (String attribute : attributes) {
                indices.add(attr.indexOf(attribute));
            }
            DataSet dataSet = new DataSet(name, attributes);

            line = br.readLine();
            while (line != null) {
                if (!line.startsWith("%")) {
                    String[] split = line.split(",");
                    Double effort = Double.parseDouble(split[split.length - 1]);
                    DataRecord record = new DataRecord(effort);

                    for (Integer index : indices) {
                        String key = attr.get(index);
                        double value = Double.parseDouble(split[index]);
                        record.put(key, value);
                    }
                    dataSet.addRecord(record);
                }
                line = br.readLine();
            }
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}