import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_11_Seating_System {

    public static void main(String args[]) throws IOException {

        List<char[]> seatsList = fileReaderCharArray("Day11.txt");
        Seats seats = new Seats(seatsList);
        System.out.println("Part 1 : " + seats.simulateSeatRounds());
        seats = new Seats(seatsList);
        System.out.println("Part 2 : " + seats.simulateSeatRoundsPartTwo());
    }

    public static class Seats{
        private List<char[]> seats;

        private int rows;
        private int columns;
        private boolean seatsChange;

        public Seats(List<char[]> seats){
            this.seats = seats;
            rows = seats.size();
            columns = seats.get(0).length;
            seatsChange = true;

        }

        // Function : iterates through seats till no changes

        private int simulateSeatRounds(){

            while(seatsChange){
                seats = round();
            }
            return countOccupied();
        }

        private int simulateSeatRoundsPartTwo(){

            while(seatsChange){
                seats = roundPartTwo();
            }
            return countOccupied();
        }

        // Function : counts total occupied seats
        private int countOccupied(){
            int occupied = 0;
            for (char[] seatRow : seats) {
                for (char tile : seatRow) {
                    if(tile == '#'){
                        occupied++;
                    }
                }
            }
            return occupied;
        }
        // Function : iterates a stage of seats
        private List<char[]> round(){
            seatsChange = false;
            List<char[]> newSeats = new ArrayList<>();
            char[] charArray;

            for (int x = 0; x < rows; x++) {
                charArray = new char[seats.get(0).length];

                for (int y = 0; y < columns; y++) {
                    if(changeSeat(x,y)){
                        charArray[y] = flipSeat(x, y);
                        seatsChange = true;
                    }else{
                        charArray[y] = seats.get(x)[y];
                    }
                }

                newSeats.add(charArray);
            }

            return newSeats;
        }

        // Function that checks if seat should change
        private boolean changeSeat(int xInp, int yInp){
            int occupied = 0;
            for (int x = xInp - 1; x <= xInp + 1; x++) {
                for (int y = yInp - 1; y <= yInp + 1; y++) {

                    if(y > -1 && x > -1 && y < seats.size() && x < seats.get(0).length) {
                        if(seats.get(x)[y] == '#') {

                            if (!(x == xInp && y == yInp)) {
                                occupied++;
                            }
                        }
                    }else{
                    }
                }
            }
            if(seats.get(xInp)[yInp] == '#'){
                if(occupied >= 4){
                    return true;
                }
            }else if(seats.get(xInp)[yInp] == 'L'){
                if(occupied == 0){
                    return true;
                }
            }
            return false;
        }

        private List<char[]> roundPartTwo(){
            seatsChange = false;
            List<char[]> newSeats = new ArrayList<>();
            char[] charArray;

            for (int x = 0; x < rows; x++) {
                charArray = new char[seats.get(0).length];

                for (int y = 0; y < columns; y++) {
                    if(changeSeatPartTwo(x,y)){
                        charArray[y] = flipSeat(x, y);
                        seatsChange = true;
                    }else{
                        charArray[y] = seats.get(x)[y];
                    }
                }

                newSeats.add(charArray);
            }

            return newSeats;
        }

        private boolean changeSeatPartTwo(int xInp, int yInp){
            int occupied = 0;
            int x;
            int y;
            for (int yIncrements = -1; yIncrements <= 1; yIncrements++) {
                for (int xIncrements = -1; xIncrements <= 1; xIncrements++) {
                    x = xInp;
                    y = yInp;
                    if(!(xIncrements == 0 && yIncrements == 0)) {
                        while ((y + yIncrements != columns && x + xIncrements != rows && x + xIncrements > -1 && y + yIncrements > -1)) {
                            x += xIncrements;
                            y += yIncrements;

                            if (seats.get(x)[y] == '#') {
                                if (!(x == xInp && y == yInp)) {
                                    occupied++;
                                    break;
                                }
                            } else if (seats.get(x)[y] == 'L') {
                                break;
                            }
                        }
                    }
                }
            }

            if(seats.get(xInp)[yInp] == '#'){
                if(occupied >= 5){
                    return true;
                }
            }else if(seats.get(xInp)[yInp] == 'L'){
                if(occupied == 0){
                    return true;
                }
            }
            return false;
        }


        private char flipSeat(int x, int y){
            switch (seats.get(x)[y]){
                case 'L':
                    return '#';

                case '#':
                    return 'L';

                default:
                    return '.';
            }
        }

        // Function : Checks if seats == previous seats
//        private boolean seatsChange(){
//            return !seats.containsAll(previousSeats);
//        }

        private void print(List<char[]> seatMap){
            for (char[] seatRow : seatMap){
                for (char tile : seatRow) {
                    System.out.print(tile);
                }
                System.out.println();
            }
        }
    }


    private static List<char[]> fileReaderCharArray(String fileName) throws IOException {
        List<char[]> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line.toCharArray());
        }
        reader.close();
        return lines;
    }

}
