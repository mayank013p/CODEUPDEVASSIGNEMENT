/***
 * The Cricket Game GUI allows users to create teams and players, view team information, and simulate matches with interactive ball outcome inputs. Users can manage up to 10 teams, each with 11 players, and conduct matches with specified overs. The application provides a simple interface using Java Swing for seamless interaction.
 * Owner: Mayank Aitan
 * Date of creation: 18/09/2024
 */
package Cricketgame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CricketGame cricketGame = new CricketGame();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            clearConsole();
            printBanner();
            System.out.println("\nWelcome to the Cricket Game!\n");
            Thread.sleep(1000);

            System.out.println("Choose an option:");
            System.out.println("1. Create Team");
            System.out.println("2. Add Team Members");
            System.out.println("3. Create Match");
            System.out.println("4. View Teams");
            System.out.println("5. View Players");
            System.out.println("6. Exit");
            System.out.print("Enter option number: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\n*** Option: Create Team ***");
                    Thread.sleep(500);
                    cricketGame.createTeam();
                    break;
                case 2:
                    System.out.println("\n*** Option: Add Team Members ***");
                    Thread.sleep(500);
                    cricketGame.addTeamMembers();
                    break;
                case 3:
                    System.out.println("\n*** Option: Create Match ***");
                    Thread.sleep(500);
                    cricketGame.createMatch();
                    break;
                case 4:
                    boolean returnToMenu = false;
                    while (!returnToMenu) {
                        System.out.println("\n*** Option: View Teams ***");
                        Thread.sleep(500);
                        cricketGame.viewTeams();

                        System.out.println("\n1. Return to Main Menu");
                        System.out.print("Enter option number: ");
                        int viewOption = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        if (viewOption == 1) {
                            returnToMenu = true;
                        } else {
                            System.out.println("\nInvalid option, please try again.");
                            Thread.sleep(1000);
                        }
                    }
                    break;
                case 6:
                    exit = true;
                    System.out.println("\nExiting the game...");
                    for (int i = 3; i > 0; i--) {
                        System.out.println("Closing in " + i + "...");
                        Thread.sleep(1000);
                    }
                    System.out.println("Goodbye!");
                    break;
                case 5:
                    System.out.println("\n*** Option: View Players ***");
                    Thread.sleep(500);
                    cricketGame.viewPlayers();
                    break;
                default:
                    System.out.println("\nInvalid option, please try again.");
                    Thread.sleep(1000);
            }
        }
        scanner.close();
    }

    public static void printBanner() throws InterruptedException {
        System.out.println("*******************************");
        System.out.println("*   Welcome to Cricket Game!   *");
        System.out.println("*******************************");
        Thread.sleep(500);
    }

    @SuppressWarnings("deprecation")
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception ex) {
            System.out.println("\n\n");
        }
    }

    public static void createTeam() throws InterruptedException {
        System.out.println("Creating team...");
        Thread.sleep(1000);
    }

    public static void addTeamMembers() throws InterruptedException {
        System.out.println("Adding team members...");
        Thread.sleep(1000);
    }

    public static void createMatch() throws InterruptedException {
        System.out.println("Creating match...");
        Thread.sleep(1000);
    }
}
