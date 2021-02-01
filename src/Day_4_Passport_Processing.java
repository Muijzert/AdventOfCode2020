import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day_4_Passport_Processing {

    public static void main(String args[]) throws IOException {
        List<HashMap<String,String>> passports = passportReader("Day4.txt");
        String[] requirments = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};

        System.out.println("Part 1 -> Total valid passports = " + findValidPassportsPart1(passports, requirments));
        System.out.println("Part 2 -> Total valid passports = " + findValidPassportsPart2(passports));
    }

    public static int findValidPassportsPart1(List<HashMap<String,String>> passports, String[] requirments){
        int valid = passports.size();
        for (HashMap<String,String> passport : passports) {
            for (String requirment : requirments) {
                if (!passport.containsKey(requirment)){
                    valid--;
                    break;
                }
            }
        }
        return valid;
    }


    public static List<HashMap<String,String>> passportReader(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        String [] keyValue;
        List<HashMap<String,String>> passports = new ArrayList<>();
        HashMap<String, String> passport = new HashMap<>();

        // Reads text file line by line
        while ((line = reader.readLine()) != null) {
            // If new passport as shown by an empty line add previous passport to list and make blank passport
            if (line.equals("")){
                passports.add(passport);
                passport = new HashMap<>();
            }else{
                // Adds key and values from current line to passport
                String[] splitLine = line.split(" ");
                for (String aSplitLine : splitLine) {
                    keyValue = aSplitLine.split(":");
                    passport.put(keyValue[0], keyValue[1]);
                }
            }
        }
        reader.close();
        // Add last passport
        passports.add(passport);
        return passports;
    }

    public static int findValidPassportsPart2(List<HashMap<String,String>> passports){
        int valid = passports.size();
        for (HashMap<String,String> passport : passports) {
            if (!passport.containsKey("byr")){
                valid--;
            }else if (Integer.parseInt(passport.get("byr")) < 1920 || Integer.parseInt(passport.get("byr")) > 2002){
                valid--;
            }

            else if (!passport.containsKey("iyr")){
                valid--;
            }else if (Integer.parseInt(passport.get("iyr")) < 2010 || Integer.parseInt(passport.get("iyr")) > 2020) {
                valid--;
            }

            else if (!passport.containsKey("eyr")){
                valid--;
            }else if (Integer.parseInt(passport.get("eyr")) < 2020 || Integer.parseInt(passport.get("eyr")) > 2030){
                valid--;
            }

            else if (!passport.containsKey("hgt")){
                valid--;
            }else if (!hgtValid(passport.get("hgt"))){
                valid--;
            }

            else if (!passport.containsKey("hcl")){
                valid--;
            }else if (!hclValid(passport.get("hcl"))){
                valid--;
            }

            else if (!passport.containsKey("ecl")){
                valid--;
            }else if (!eclValid(passport.get("ecl"))){
                valid--;
            }

            else if (!passport.containsKey("pid")){
                valid--;
            }else if (!passport.get("pid").matches("[0-9]{9}")){
                valid--;
            }else{
            }
        }
        return valid;
    }

    public static boolean hgtValid(String hgt) {
        int height;
        if (hgt.contains("cm")) {
            height = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
            return height >= 150 && height <= 193;
        } else if (hgt.contains("in")) {
            height = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
            return height >= 59 && height <= 76;
        }
        return false;
    }
    public static boolean hclValid(String hcl) {
        return hcl.matches("#([a-f]|[0-9]){6}");
    }

    public static boolean eclValid(String ecl) {
        if(ecl.length() == 3) {
            return ecl.matches("(amb)|(blu)|(brn)|(gry)|(grn)|(hzl)|(oth)");
        }else{
            return false;
        }
    }
}