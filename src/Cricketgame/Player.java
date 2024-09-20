/***
 * The Cricket Game GUI allows users to create teams and players, view team information, and simulate matches with interactive ball outcome inputs. Users can manage up to 10 teams, each with 11 players, and conduct matches with specified overs. The application provides a simple interface using Java Swing for seamless interaction.
 * Owner: Mayank Aitan
 * Date of creation: 18/09/2024
 */
package Cricketgame;

public class Player {
    private String name;
    private String position;

    public Player(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return name + " (" + position + ")";
    }
}
