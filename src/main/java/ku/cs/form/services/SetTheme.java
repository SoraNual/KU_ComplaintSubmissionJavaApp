package ku.cs.form.services;


import javafx.scene.paint.Color;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;


public class SetTheme {

    private String username;
    private HashMap<String, String[]> themeHashMap;
    private HashMap<String, String[]> settingHashMap;


    public SetTheme(String username) {
        this.username = username;
        setThemeHashMap();
        setSettingHashMap();
    }

    private void setSettingHashMap() {
        //for add in settingHashMap
        settingHashMap = new HashMap<>();
        String filePath = "data" + File.separator + "setting.csv";
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = "";
            boolean isSettingSection = false;
            while (!(line = buffer.readLine()).equals("-Theme-List-")) {

                if (line.equals("-Setting-")) {
                    isSettingSection = true;
                    continue;
                }

                if (isSettingSection) {
                    String[] data = line.split(",");
                    String[] settingArray = {data[1].trim(), data[2].trim(),data[3].trim()};
                    settingHashMap.put(data[0].trim(), settingArray);
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

    }

    private void setThemeHashMap() {
        //for add in themeHashMap
        themeHashMap = new HashMap<>();
        String filePath = "data" + File.separator + "setting.csv";
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = "";
            boolean isThemeListSection = false;

            while ((line = buffer.readLine()) != null) {
                if (line.equals("-Theme-List-")) {
                    isThemeListSection = true;
                    continue;
                }

                if (isThemeListSection) {
                    String[] data = line.split(",");
                    String[] themeColorArray = {data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim(),
                            data[5].trim(),
                            data[6].trim(),
                            data[7].trim()
                    };
                    themeHashMap.put(data[0].trim(), themeColorArray);
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

    }

    public void setting() {

        String[] setting = settingHashMap.get(username);

        //for new user setting (not found in -setting-
        if (setting == null) {
            String[] default_setting = {"default", "18","Kanit"};
            settingHashMap.put(username, default_setting);
            setting = settingHashMap.get(username);
        }

        String[] theme = themeHashMap.get(setting[0]);

        String menuBarColor = theme[0];
        String menuBarTextColor = theme[1];
        String textColor = theme[2];
        String backgroundColor = theme[3];
        String inputColor = theme[4];
        String listviewColor = theme[5];
        String buttonColor = theme[6];

        int textSize = Integer.parseInt(setting[1]);
        String fontFamily = setting[2];
        //setting in css file
        setStylesCss(menuBarColor,
                menuBarTextColor,
                textColor,
                backgroundColor,
                inputColor,
                listviewColor,
                buttonColor,
                textSize,
                fontFamily);
    }

    private void setStylesCss(String menuBarColor,
                              String menuBarTextColor,
                              String textColor,
                              String backgroundColor,
                              String inputColor,
                              String listviewColor,
                              String buttonColor,
                              int textSize, String fontFamily) {
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

            String fontFace = " @font-face { \n" +
                        "   font-family: '" + fontFamily + "';\n" +
                        "   src: url('fonts/" + fontFamily + ".ttf');}\n";

            String rootProperty = ".root {\n" +
                    "   -menu-bar-color: " + menuBarColor + ";\n" +
                    "   -menu-bar-text-color: " + menuBarTextColor + ";\n" +
                    "   -text-color: " + textColor + ";\n" +
                    "   -background-color: " + backgroundColor + ";\n" +
                    "   -input-color: " + inputColor + ";\n" +
                    "   -listview-color: " + listviewColor + ";\n" +
                    "   -button-color: " + buttonColor + ";\n" +
                    "   -hover-menu-bar-color: " + hoverColor(menuBarColor) + ";\n" +
                    "   -hover-button-color: " + hoverColor(buttonColor) + ";\n" +
                    "   -button-text-color: " + darkerColor(buttonColor) + ";\n }" +
                    "   * { -fx-font-family: " + "'" + fontFamily + "';}\n";

            double percent = textSize/100.0;
            String fontSize = ".root { -fx-font-size: " + (int) (16*percent) + "px;}\n"
            + ".h1 { -fx-font-size: " + (int) (28*percent) + "px;}\n"
            + ".h2 { -fx-font-size: " + (int) (20*percent) + "px;}\n"
            ;


            buffer.write(fontFace);
            buffer.newLine();
            buffer.write(rootProperty);
            buffer.newLine();
            buffer.write(fontSize);
            buffer.newLine();

            reader = new FileReader(original_css);
            bufferedReader = new BufferedReader(reader);

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
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

    private void writeThemeListFile() {
        String filePath = "data" + File.separator + "setting.csv";
        File file = new File(filePath);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            buffer.write("-Setting-");
            buffer.newLine();

            for (String k : settingHashMap.keySet()) {
                String[] setting = settingHashMap.get(k);
                String line = k + "," + setting[0] + "," + setting[1] +"," + setting[2];
                buffer.write(line);
                buffer.newLine();
            }

            buffer.write("-Theme-List-");
            buffer.newLine();

            for (String k : themeHashMap.keySet()) {
                String[] themeColor = themeHashMap.get(k);
                String line = k + "," + themeColor[0]
                        + "," + themeColor[1]
                        + "," + themeColor[2]
                        + "," + themeColor[3]
                        + "," + themeColor[4]
                        + "," + themeColor[5]
                        + "," + themeColor[6];

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

    public void changeCustomThemeColor(String[] colorArray) {
        if (!themeHashMap.containsKey(username)) themeHashMap.putIfAbsent(username, colorArray);
        else themeHashMap.replace(username, colorArray);
        writeThemeListFile();
        changeTheme(username);
    }

    public void changeTheme(String newTheme) {
        String[] setting = settingHashMap.get(username);
        setting[0] = newTheme;
        System.out.println(Arrays.toString(setting));
        settingHashMap.replace(username, setting);
        writeThemeListFile();
        setting();
    }

    public void changeTextSize(String newSize) {
        String[] setting = settingHashMap.get(username);
        setting[1] = newSize;
        settingHashMap.replace(username, setting);
        writeThemeListFile();
        setting();
    }

    public void changeFont(String font) {
        String[] setting = settingHashMap.get(username);
        setting[2] = font;
        settingHashMap.replace(username, setting);
        writeThemeListFile();
        setting();
    }

    private String hoverColor(String color) {
        color = ("#"+((Color.web(color)).brighter())).replace("0x","").toUpperCase();
        return color;
    }

    private String darkerColor(String color){
        color = ("#"+((Color.web(color)).darker().darker())).replace("0x","").toUpperCase();
        return color;
    }

    public boolean hasCustomTheme() {
        return themeHashMap.containsKey(username);
    }


}


