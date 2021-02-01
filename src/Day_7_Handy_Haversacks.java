import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day_7_Handy_Haversacks {
    public static void main(String args[]) throws IOException {
        Day_7_Handy_Haversacks day7 = new Day_7_Handy_Haversacks();
    }

    private HashMap<String, HashMap<String, Integer>> rules = new HashMap<>();
    private HashMap<String, HashMap<String, Integer>> rulesExpanded = new HashMap<>();

    public Day_7_Handy_Haversacks() throws IOException {
        List<String> lines = fileReader("Day7.txt");
        for (String line : lines) {
            addRule(line);
        }

        bagContains();

        System.out.println("Part 1 : " + countBag("shiny gold"));
        System.out.println("Part 2 : " + partTwo("shiny gold"));
    }

    private void bagContains(){
        Object[] rulesKeySet = rules.keySet().toArray();
        HashMap<String, Integer> ruleExpanded;
        for (Object aRule: rulesKeySet) {
            // Finds how many types of bags can be inside the main bag
            ruleExpanded = expandRule(aRule.toString(),rules.get( aRule.toString()));
            // Adds new rule to RulesExpanded
            rulesExpanded.put(aRule.toString(), ruleExpanded);
        }

    }
    private HashMap<String, Integer> expandRule(String mainBagName, HashMap<String, Integer> aRule){
        HashMap<String, Integer> tmpRule = new HashMap<>();
        Object[] ruleKeySet = aRule.keySet().toArray();


        for (Object bagName : ruleKeySet) {
            if(bagName.toString().contains("no other bags")){
                tmpRule.put(mainBagName, 0);
            }else{
                tmpRule.put(mainBagName, 0);
                tmpRule.putAll(expandRule(bagName.toString(), rules.get(bagName)));
            }
        }

        return tmpRule;
    }

    private void addRule(String line){
        // Split line
        String[] lineSplit = line.split(" bags contain");
        // Main bag
        String mainBag = lineSplit[0];
        HashMap<String, Integer> rule = new HashMap<>();

        if (lineSplit[1].contains("no other bags")){
            rule.put("no other bags", 0);
        }else {
            // Sub bags
            String[] subBags = lineSplit[1].split(", ");

            String subBagName;
            int subBagAmount;
            for (String subBag : subBags) {
                subBagName = subBag.substring(2, subBag.indexOf(" bag")).trim();
                subBagAmount = Integer.parseInt(subBag.substring(0, 2).trim());
                rule.put(subBagName, subBagAmount);
            }
        }
        rules.put(mainBag,rule);
    }

    private int countBag(String bag){
        Object[] keySet = rulesExpanded.keySet().toArray();
        int counter = 0;
        for (Object key : keySet) {
            HashMap<String, Integer> tmp = rulesExpanded.get(key);
            if(rulesExpanded.get(key).containsKey(bag))
                counter++;
        }
        return counter;
    }

    private long partTwo(String bag){
        return totalBags(bag) - 1;
    }

    private long totalBags(String bag){
        long total = 0;
        if(rules.get(bag).containsKey("no other bags")){
            return 1;
        }else{
            Object[] keySet = rules.get(bag).keySet().toArray();
            for (Object key : keySet) {
                total += rules.get(bag).get(key) * totalBags(key.toString());
            }
            return total + 1;
        }
    }


    private List<String> fileReader(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        reader.close();
        return words;
    }
}
