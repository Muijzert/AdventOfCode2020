import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_18_Operation_Order {
    public static void main(String args[]) throws IOException {
        List<String> lines = fileReader("Day18.txt");
        OperationOrder operationOrder = new OperationOrder(lines);
        System.out.println(operationOrder.partOne());
        System.out.println(operationOrder.partTwo());

    }
    public static List<String> fileReader(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        reader.close();
        return words;
    }

    public static class OperationOrder {
        List<String> homework;
        List<String> partTwoHomework;

        public OperationOrder(List<String> homework) {
            this.homework = homework;
            // Stores input file as array list
            partTwoHomework = new ArrayList<>();
        }

        private long partOne() {
            long sum = 0;
            // Loop through each equation and add each answer to sum
            for (String equation : homework) {
                sum = sum + calcSum(equation);
            }
            return sum;
        }

        // Calculates value of equation with bracket having the correct priority
        private long calcSum(String equation) {
            equation = equation.replaceAll("\\s+", "");
            int bracketCount;
            char[] splits = equation.toCharArray();
            boolean firstValue = true;
            String symbol = "";
            int bracketStart;
            long bracketValue;
            long sum = 0;

            // Loop through every character in the char[]
            for (int i = 0; i < splits.length; i++) {
//                if it's a number
                if (Character.isDigit(splits[i])) {
                    // If it's the first number set it as current sum
                    if (firstValue) {
                        sum = Integer.parseInt(String.valueOf(splits[i]));
                        firstValue = false;
                    } else {
                        // else if previous numbers exist

                        // if current symbol is + add current integer to sum
                        if (symbol.equals("+")) {
                            sum = sum + Integer.parseInt(String.valueOf(splits[i]));

                        // if current symbol is * multiple current integer with sum
                        } else if (symbol.equals("*")) {
                            sum = sum * Integer.parseInt(String.valueOf(splits[i]));
                        }
                    }

//                    if it's a current +
                } else if (splits[i] == '+') {
                    symbol = "+";
//                    if it's a current *
                } else if (splits[i] == '*') {
                    symbol = "*";
//                    if it's a current (
                } else if (splits[i] == '(') {
                    // Find where bracket ends
                    // Then extract the bracketed segment into the same function using recursion to find the value of that segment.
                    // Then depending on the current symbol calculate the segments value with sum.
                    bracketCount = 1;
                    i++;
                    bracketStart = i;
                    // Loop through till end segment is found
                    while (bracketCount != 0) {
                        if (splits[i] == '(') {
                            bracketCount++;
                        } else if (splits[i] == ')') {
                            bracketCount--;
                            if (bracketCount == 0) {
                                // Found end bracket
                                // Calculate bracket segment
                                bracketValue = calcSum(equation.substring(bracketStart, i));
                                // if first value
                                if (firstValue) {
                                    sum = bracketValue;
                                    firstValue = false;
                                } else {
                                    if (symbol.equals("+")) {
                                        sum = sum + bracketValue;
                                    } else if (symbol.equals("*")) {
                                        sum = sum * bracketValue;
                                    }
                                }
                            }
                        }
                        i++;
                    }
                    i--;
                }
            }
            return sum;
        }

        private static boolean isNumber(char input) {
            return Character.isDigit(input);
        }

        private long partTwo() {
            long sum = 0;
            String newEquation;
            // Loop through each equation
            for (String equation : homework) {
                // This function adds brackets in the equation to make addition be prioritised
                // For example it will change 5 * 3 + 2 -> 5 * (3 + 2)
                newEquation = prioritiseAddition(equation);
                // Then input new equation into calcSum which prioritises brackets
                sum = sum + calcSum(newEquation);
            }
            return sum;
        }

        // Adds brackets in the equation to make addition be prioritised
        private String prioritiseAddition(String equation) {
            equation = equation.replaceAll("\\s+", "");
            StringBuilder newEquation = new StringBuilder(equation);
            int additionalBracketOffset = 0;
            // Loop through equation to see if there are any +'s
            for (int i = 0; i < newEquation.length(); i++) {
                if (newEquation.charAt(i) == '+') {
                    // Left side of the +
                    if (isNumber(newEquation.charAt(i - 1))) {
                        newEquation.insert(i - 1, "(");
                        i++;
                    } else if (newEquation.charAt(i - 1) == ')') {
                        newEquation.insert(findStartBracketIndex(newEquation, i), "(");
                        i++;
//
                    }
                    // Right side of the +
                    if (isNumber(newEquation.charAt(i + 1))) {
                        newEquation.insert(i + 2 + additionalBracketOffset, ")");
                        i++;
                    } else if (newEquation.charAt(i + 1) == '(') {
                        newEquation.insert(findEndBracketIndex(newEquation, i) + i, ")");
                        i++;
                    }
                }
            }
            return String.valueOf(newEquation);
        }


        // Finds the ending bracket ")"index of the bracket segment on the right side of the given index of the equation
        private int findEndBracketIndex(StringBuilder equation, int addIndex) {
            String tmp = equation.substring(addIndex + 1, equation.length());
            equation = new StringBuilder(tmp);
            int bracketCount = 1;
            int i = 1;
            while (bracketCount != 0) {
                if (equation.charAt(i) == '(') {
                    bracketCount++;
                } else if (equation.charAt(i) == ')') {
                    bracketCount--;
                    if (bracketCount == 0) {
                        return i + 1;
                    }
                }
                i++;
            }

            return 0;
        }
        // Finds the starting bracket "(" index of the bracket segment on the left side of the given index of the equation
        private int findStartBracketIndex(StringBuilder equation, int addIndex) {
            String tmp = equation.substring(0, addIndex);
            equation = new StringBuilder(tmp);
            int bracketCount = 1;
            int i = addIndex - 2;
            while (bracketCount != 0) {
                if (equation.charAt(i) == ')') {
                    bracketCount++;
                } else if (equation.charAt(i) == '(') {
                    bracketCount--;
                    if (bracketCount == 0) {
                        return i;
                    }
                }
                i--;
            }
            return 0;
        }
    }
}