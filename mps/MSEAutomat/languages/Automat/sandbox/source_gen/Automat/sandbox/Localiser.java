package Automat.sandbox;

/*Generated by MPS */

import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Localiser {
  private static final String FILE_NAME = AutomatView.RESPATH + "locales/" + "de" + ".txt";

  private static final Map<String, String> VALUES = new HashMap<String, String>();


  private Localiser() {
  }


  static {
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader(FILE_NAME));

      try {
        String line = null;
        while ((line = reader.readLine()) != null) {
          String[] s = line.split("=");
          if (s.length == 2) {
            VALUES.put(s[0], s[1]);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static String getString(String key) {
    String v = VALUES.get(key);
    if (v == null) {
      v = "!" + key + "!";
    } else {
      v = v.replace("\\n", "\n");
    }
    return v;
  }


}
