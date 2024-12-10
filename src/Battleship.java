import java.util.ArrayList;

public class Battleship {
    private String name;
    private ArrayList<String> shipLocations = new ArrayList<String>();

    public void setName(String aName) {
        name = aName;
    }

    public String getName() {
        return name;
    }

    public void setShipLocations(ArrayList<String> locations) {
        shipLocations = locations;
    }

    public ArrayList<String> getShipLocations() {
        return shipLocations;
    }

    public String checkYourself(String userGuess) {
        String result = "miss";
        if (shipLocations.contains(userGuess)) {
            int index = shipLocations.indexOf(userGuess);
            shipLocations.remove(index);
            result = "hit";
        }
        if (shipLocations.isEmpty()) {
            result = "kill";
            System.out.println("You destroyed " + name + ".");
        }
        // System.out.println(result);
        return result;
    }
}