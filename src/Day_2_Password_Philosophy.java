import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_2_Password_Philosophy {

    public static void main(String[] args) throws IOException {
        // Inputs text file to string list
        List<String> stringList = fileReader("Day2.txt");

        System.out.println(partOne(stringList));
        System.out.println(partTwo(stringList));
    }
    public static int partOne(List<String> stringList) {
        int correctPasswords = 0;

        // Loops through every string in list
        for (String line : stringList) {
            // Splits line up into amount of letter, letter, password
            String[] parts = line.split(" ");
            // Split range into first value and second value
            String[] range = parts[0].split("-");
            // Extracts letter
            char letter = parts[1].charAt(0);


            if (numbChar(parts[2], letter) >= Integer.parseInt(range[0]) && numbChar(parts[2], letter) <= Integer.parseInt(range[1])) {
                correctPasswords++;
            }
        }

        return  correctPasswords;
    }

    public static int partTwo(List<String> stringList) {
        int correctPasswords = 0;

        // Loops through every string in list
        for (String line : stringList) {
            // Splits line up into amount of letter, letter, password
            String[] parts = line.split(" ");
            // Split range into first value and second value
            String[] range = parts[0].split("-");
            // Extracts letter
            char letter = parts[1].charAt(0);

            if (parts[2].charAt(Integer.parseInt(range[0]) - 1) == letter ^ parts[2].charAt(Integer.parseInt(range[1]) - 1) == letter){
                correctPasswords++;
            }
        }

        return correctPasswords;
    }



    // Counts amount of char inside string
    public static int numbChar(String str, char c)
    {
        int total = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            total++;
        }

        return total;
    }



    public static List<String> fileReader(String fileName) throws IOException {
        List<String> words = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        reader.close();
        return words;
    }



}
