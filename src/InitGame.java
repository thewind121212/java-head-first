import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class InitGame {
    private static final int GRID_LENGTH = 7; // Number of columns
    private static final int GRID_SIZE = 49; // Total grid size (7x7)
    private static final int MAX_ATTEMPTS = 200;
    private static int numOfShips = 0; // Tracks the number of ships placed
    private static final int HORIZONTAL_INCREMENT = 1; // Increment for horizontal ships
    private static final int VERTICAL_INCREMENT = GRID_LENGTH; // Increment for vertical ships
    private Random random = new Random();
    private int[] grid = new int[GRID_SIZE]; // Tracks occupied grid cells
    private static final String ALPHA = "abcdefgh"; // Letters for rows
    private static Scanner scanner = new Scanner(System.in); // User input

    // Get user input
    public String getUserInput() {
        System.out.print("What is your guess: ");
        return scanner.nextLine().toLowerCase();
    }

    // Place a ship on the grid
    public ArrayList<String> setShipPostions(int size) {
        int[] locations = new int[size];
        boolean success = false;
        int attempts = 0;

        numOfShips++; // Increment ship count to alternate between horizontal/vertical
        int increment = postionIncrement(); // Determine increment direction

        while (!success && ++attempts < MAX_ATTEMPTS) {
            int startPoint = random.nextInt(GRID_SIZE); // Random starting point

            // Ensure the ship fits within the grid boundaries
            if (startPoint + (increment * (size - 1)) >= GRID_SIZE) {
                continue; // Skip this attempt
            }

            for (int i = 0; i < size; i++) {
                locations[i] = startPoint;
                startPoint += increment;
            }

            boolean isOnGrid = checkLocationsAreInGridBoundaries(locations);
            boolean isAnyShipInNewLocations = false;

            if (isOnGrid) {
                isAnyShipInNewLocations = checkAvailableCoords(locations);
            }

            if (isOnGrid && isAnyShipInNewLocations) {
                populateGrid(locations);
                success = true;
            }
        }

        ArrayList<String> alphaNumLocations = getAlphaLoc(locations, increment);
        System.out.println(alphaNumLocations);
        return alphaNumLocations;
    }

    // Convert numeric locations to alphanumeric grid positions (e.g., "a1", "b2")
    private ArrayList<String> getAlphaLoc(int[] locations, int increment) {
        ArrayList<String> resultingList = new ArrayList<>();
        int row;
        String rowConverted;
        int column;
        for (int loc : locations) {
            row = checkRow(loc);
            rowConverted = ALPHA.substring(row, row + 1);
            column = loc % GRID_LENGTH + 1;
            resultingList.add(rowConverted + column);
        }
        return resultingList;
    }

    // Mark grid locations as occupied
    private void populateGrid(int[] locations) {
        for (int loc : locations) {
            grid[loc] = 1;
        }
    }

    // Check if the ship overlaps with any existing ship
    private boolean checkAvailableCoords(int[] locations) {
        for (int loc : locations) {
            if (grid[loc] != 0) {
                return false;
            }
        }
        return true;
    }

    // Ensure ship positions are within the grid boundaries
    private boolean checkLocationsAreInGridBoundaries(int[] locations) {
        for (int location : locations) {
            if (location < 0 || location >= GRID_SIZE) {
                return false;
            }
        }
        return true;
    }

    // Alternate ship orientation between horizontal and vertical
    private int postionIncrement() {
        if (numOfShips % 2 == 0) {
            return HORIZONTAL_INCREMENT;
        } else {
            return VERTICAL_INCREMENT;
        }
    }

    // Determine the row index from a grid location
    private int checkRow(int location) {
        return location / GRID_LENGTH;
    }

    // Main method to test the program
    public static void main(String[] args) {
        InitGame game = new InitGame();
        // Example: Place a ship of size 3
        game.setShipPostions(3);

        // Get user input as a guess
        String userGuess = game.getUserInput();
        System.out.println("User guessed: " + userGuess);
    }
}
