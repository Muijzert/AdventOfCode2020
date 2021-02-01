import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day_12_Rain_Risk {

    public static void main(String args[]) throws IOException {
        List<String> navigation = fileReader("Day12.txt");

        Ferry ferry = new Ferry(navigation);
        FerryWaypoint ferry2 = new FerryWaypoint(navigation);

        System.out.println("Part 1 : " + ferry.partOne());
        System.out.println("Part 2 : " + ferry2.partTwo());
    }

    public static class Ferry{
        private List<String> navigation;
        private int degrees;
        private String[] directions = {"N", "E", "S", "W"};
        private String direction;
        private int x;
        private int y;

        public Ferry(List<String> navigation){
            this.navigation = navigation;
            x = 0;
            y = 0;
            direction = "E";
            degrees = 90;
        }

        private int partOne(){
            for (String instruction : navigation) {
                moveFerry(instruction);
            }
            return Math.abs(x) + Math.abs(y);
        }
        private void moveFerry(String instruction){
            String[] directionANDunits = extractInstruction(instruction);
            String nav = directionANDunits[0];
            int units = Integer.parseInt(directionANDunits[1]);

            if(nav.equals("L") || nav.equals("R")){
                rotateFerry(nav, units);
            }
            if(nav.equals("F")){
                nav = direction;
            }
            switch (nav){
                case "N":
                    y += units;
                    break;

                case "E":
                    x += units;
                    break;

                case "S":
                    y -= units;
                    break;

                case "W":
                    x -= units;
                    break;

            }
        }

        private void rotateFerry(String nav, int units){
            if(nav.equals("R")){
                degrees += units;

            }else{
                degrees -= units;
                if(degrees < 0){
                    degrees = 360 + degrees;
                }
            }
            degrees = Math.abs(degrees);

            degrees = degrees % 360;

            direction = directions[degrees / 90];
        }

        private String[] extractInstruction(String instruction){
            String[] directions = new String[2];

            directions[0] = instruction.substring(0 , 1);
            directions[1] = instruction.substring(1);
            return directions;
        }
    }

    public static class FerryWaypoint{
        private List<String> navigation;
        private int xWaypoint;
        private int yWaypoint;
        private int xFerry;
        private int yFerry;

        public FerryWaypoint(List<String> navigation){
            this.navigation = navigation;
            xWaypoint = 10;
            yWaypoint = 1;
            xFerry = 0;
            yFerry = 0;
        }

        private int partTwo(){
            for (String instruction : navigation) {
                move(instruction);
            }
            return Math.abs(xFerry) + Math.abs(yFerry);
        }
        private void move(String instruction){
            String[] directionANDunits = extractInstruction(instruction);
            String nav = directionANDunits[0];
            int units = Integer.parseInt(directionANDunits[1]);

            switch (nav) {
                case "L":
//                    units = units * -1;
                    turnWaypoint(units);
                    break;
                case "R":
                    units = units * -1;
                    turnWaypoint(units);
                    break;
                case "F":
                    xFerry += xWaypoint * units;
                    yFerry += yWaypoint * units;
                    break;
                default:
                    switch (nav) {
                        case "N":
                            yWaypoint += units;
                            break;

                        case "E":
                            xWaypoint += units;
                            break;

                        case "S":
                            yWaypoint -= units;
                            break;

                        case "W":
                            xWaypoint -= units;
                            break;

                    }
                    break;
            }
        }

        void turnWaypoint(int degrees) {
            int tmpX = cos(xWaypoint, degrees) - sin(yWaypoint, degrees);
            int tmpY = cos(yWaypoint, degrees) + sin(xWaypoint, degrees);
            xWaypoint = tmpX;
            yWaypoint = tmpY;
        }

        private int cos(int radius, int degrees) {
            return (int)Math.round(radius * Math.cos(Math.toRadians(degrees)));
        }
        private int sin(int radius, int degrees) {
            return (int)Math.round(radius * Math.sin(Math.toRadians(degrees)));
        }


        private String[] extractInstruction(String instruction){
            String[] directions = new String[2];

            directions[0] = instruction.substring(0 , 1);
            directions[1] = instruction.substring(1);
            return directions;
        }
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
