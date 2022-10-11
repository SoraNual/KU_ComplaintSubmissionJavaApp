package ku.cs.form.services;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;


public class SetTheme {

    private String username;

    public SetTheme(String username) {
        this.username = username;
    }

    public void setting() {
        //find setting in setting.csv
        String filePath = "data" + File.separator + "setting.csv";
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;

        String textColor = "";
        String backgroundColor = "";
        String recColor = "";
        String buttonColor = "";
        int textSize = 0;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            writer = new FileWriter(file,true);
            bufferedWriter = new BufferedWriter(writer);

            String line = "";
            boolean isSetting = false;
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                if(data[0].trim().equals(username)) {
                    String[] themeProp = findThemeList(data[1].trim());
                    textColor = themeProp[0];
                    backgroundColor = themeProp[1];
                    recColor = themeProp[2];
                    buttonColor = themeProp[3];
                    textSize = Integer.parseInt(data[2].trim());
                    isSetting = true;
                }
            }

            if(!isSetting){
                line = username+",default,"+"18";
                bufferedWriter.newLine();
                bufferedWriter.write(line);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                reader.close();
                bufferedWriter.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //setting in css file
        setStylesCss(textColor,backgroundColor,recColor,buttonColor,textSize);
    }


    private void setStylesCss(String textColor,
                              String backgroundColor,
                              String recColor,
                              String buttonColor,
                              int textSize) {
        String url = "src/main/resources/ku/cs/styles/styles.css";
        File file = new File(url);
        File original_css = new File("src/main/resources/ku/cs/styles/original.css");

        FileWriter writer = null;
        BufferedWriter buffer = null;
        FileReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            String rootProperty = ".root {\n" +
                    "   -text-color: " + textColor + ";\n" +
                    "   -background-color: " + backgroundColor + ";\n" +
                    "   -rec-color: " + recColor + ";\n" +
                    "   -button-color: " + buttonColor + ";\n" +
                    "   -text-size: " + "" + textSize + ";\n" + "}";

            buffer.write(rootProperty);
            buffer.newLine();

            reader = new FileReader(original_css);
            bufferedReader = new BufferedReader(reader);

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                buffer.write(line);
                buffer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private String[] findThemeList(String theme) {

        //find theme in theme-list.csv
        String[] themeProp = new String[4];
        String filePath = "data" + File.separator + "theme-list.csv";
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                if(data[0].trim().equals(theme)) {
                    themeProp[0] = data[1].trim();
                    themeProp[1] = data[2].trim();
                    themeProp[2] = data[3].trim();
                    themeProp[3] = data[4].trim();
                    return themeProp;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void setNewTheme(String textColor,String backgroundColor,String recColor,String buttonColor) {

        ArrayList<String> themeList = new ArrayList<>();
        String filePath = "data" + File.separator + "theme-list.csv";
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line = "";
            boolean isEdit = false;
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                if(data[0].trim().equals(username)){
                    line = username + "," + textColor + "," + backgroundColor + "," + recColor + "," + buttonColor;
                    themeList.add(line);
                    isEdit = true;
                }
                else themeList.add(line);
            }

            if(!isEdit) {
                line = username + "," + textColor + "," + backgroundColor + "," + recColor + "," + buttonColor;
                themeList.add(line);
            }

            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);

            for(String s : themeList){
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                reader.close();
                bufferedWriter.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
