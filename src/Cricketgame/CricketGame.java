/***
 * The Cricket Game consol allows users to create teams and players, view team information, and simulate matches with interactive ball outcome inputs. Users can manage up to 10 teams, each with 11 players, and conduct matches with specified overs. The application provides a simple interface using Java Swing for seamless interaction.
 * Owner: Mayank Aitan
 * Date of creation: 18/09/2024
 */

package Cricketgame;

import java.util.Scanner;

public class CricketGame {
    private static final int MAX_TEAMS = 10;
    private static final int MAX_MEMBERS = 20;

    private String[] teams;
    private Player[][] teamMembers;
    private int teamCount;

    public CricketGame() {
        teams = new String[MAX_TEAMS];
        teamMembers = new Player[MAX_TEAMS][MAX_MEMBERS];
        teamCount = 0;
    }

    // Create a team with team name
    @SuppressWarnings("resource")
    public void createTeam() {
        Scanner scanner = new Scanner(System.in);

        if (teamCount >= MAX_TEAMS) {
            System.out.println("Maximum number of teams reached.");
            return;
        }

        System.out.print("Enter the name of the team: ");
        String teamName = scanner.nextLine();

        for (int i = 0; i < teamCount; i++) {
            if (teams[i].equalsIgnoreCase(teamName)) {
                System.out.println("Team already exists.");
                createTeam();
            }
        }

        teams[teamCount] = teamName;
        teamCount++;
        System.out.println("Team '" + teamName + "' created successfully.");
        System.out.println("Create a new team (y/n)");
        String choice = scanner.nextLine();
        if (choice.equals("y")) {
            createTeam();
        } else if (choice.equals("n")) {
            System.out.println("Returning to Main Menu");
        }
        return;
    }

    // view team name
    public void viewTeams() {
        if (teamCount == 0) {
            System.out.println("No teams have been created yet.");
            return;
        }

        System.out.println("Teams:");
        for (int i = 0; i < teamCount; i++) {
            System.out.println((i + 1) + ". " + teams[i]);
        }
    }

    // add team members in team
    @SuppressWarnings("resource")
    public void addTeamMembers() {
        Scanner scanner = new Scanner(System.in);

        if (teamCount == 0) {
            System.out.println("No teams available to add members.");
            return;
        }

        viewTeams();
        System.out.print("Enter the number of the team to add members to: ");
        int teamNumber = scanner.nextInt();
        scanner.nextLine();

        if (teamNumber < 1 || teamNumber > teamCount) {
            System.out.println("Invalid team number.");
            return;
        }

        int teamIndex = teamNumber - 1;
        String teamName = teams[teamIndex];

        int memberCount = 0;
        while (memberCount < MAX_MEMBERS && teamMembers[teamIndex][memberCount] != null) {
            memberCount++;
        }

        if (memberCount >= MAX_MEMBERS) {
            System.out.println("Maximum number of members reached for this team.");
            return;
        }

        System.out.print("Enter the name of the new member: ");
        String memberName = scanner.nextLine();

        System.out.print("Enter the position of the new member (e.g., Batsman, Bowler, Keeper): ");
        String position = scanner.nextLine();

        for (int i = 0; i < memberCount; i++) {
            if (teamMembers[teamIndex][i].getName().equalsIgnoreCase(memberName)) {
                System.out.println("Member already exists in the team.");
                return;
            }
        }

        teamMembers[teamIndex][memberCount] = new Player(memberName, position);
        System.out.println(
                "Member '" + memberName + "' with position '" + position + "' added to team '" + teamName + "'.");

        System.out.println("Wanna Create a new member (y/n)");
        String choice = scanner.nextLine();
        if (choice.equals("y")) {
            addTeamMembers();
        } else if (choice.equals("n")) {
            System.out.println("Returning to Main Menu");
        }
        return;
    }

    // View Player in team
    @SuppressWarnings("resource")
    public void viewPlayers() {
        Scanner scanner = new Scanner(System.in);

        if (teamCount == 0) {
            System.out.println("No teams available.");
            return;
        }

        viewTeams();
        System.out.print("Enter the number of the team to view players: ");
        int teamNumber = scanner.nextInt();
        scanner.nextLine();

        if (teamNumber < 1 || teamNumber > teamCount) {
            System.out.println("Invalid team number.");
            return;
        }

        int teamIndex = teamNumber - 1;
        System.out.println("Players in team '" + teams[teamIndex] + "':");

        boolean hasMembers = false;
        for (int i = 0; i < MAX_MEMBERS; i++) {
            if (teamMembers[teamIndex][i] != null) {
                System.out.println((i + 1) + ". " + teamMembers[teamIndex][i]);
                hasMembers = true;
            }
        }

        if (!hasMembers) {
            System.out.println("No members in this team.");
        }

        System.out.println("Do you want view another team members(y/n):");
        String choice = scanner.nextLine();
        if (choice.equals("y")) {
            viewPlayers();
        } else if (choice.equals("n")) {
            System.out.println("Returning to Main Menu");
        }
        return;

    }

    // Create a match
    @SuppressWarnings("resource")
    static final int BALLS_PER_OVER = 6;

    int maxOvers;
    int wicketsLost;
    int extras;

    // Main method
    public static void main(String[] args) throws InterruptedException {
        CricketGame game = new CricketGame();
        // game.createTeams();
        game.createMatch();
    }

    @SuppressWarnings("resource")
    public void createMatch() throws InterruptedException {
        if (teamCount < 2) {
            System.out.println("At least two teams are required to create a match.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        viewTeams();
        System.out.print("Select Team 1: ");
        int team1Index = scanner.nextInt() - 1;
        if (team1Index < 0 || team1Index >= teamCount) {
            System.out.println("Invalid team selected.");
            return;
        }

        System.out.print("Select Team 2: ");
        int team2Index = scanner.nextInt() - 1;
        if (team2Index < 0 || team2Index >= teamCount || team2Index == team1Index) {
            System.out.println("Invalid team selected.");
            return;
        }

        System.out.print("How many players per team? (Max " + MAX_MEMBERS + "): ");
        int playersPerTeam = scanner.nextInt();
        scanner.nextLine();

        if (playersPerTeam < 1 || playersPerTeam > MAX_MEMBERS) {
            System.out.println("Invalid number of players.");
            return;
        }

        Player[] team1Players = selectPlayersForTeam(team1Index, playersPerTeam);
        Player[] team2Players = selectPlayersForTeam(team2Index, playersPerTeam);

        if (team1Players == null || team2Players == null) {
            System.out.println("Player selection failed.");
            return;
        }

        System.out.println("Conducting toss...");
        Thread.sleep(1000);
        String tossWinner = conductToss(teams[team1Index], teams[team2Index]);

        System.out.println(tossWinner + " won the toss and will bat first!");

        System.out.print("Enter the number of overs for the match: ");
        maxOvers = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Match between " + teams[team1Index] + " and " + teams[team2Index] + " is starting!");
        Thread.sleep(1000);

        Player[] firstBattingTeam, secondBattingTeam;
        if (tossWinner.equals(teams[team1Index])) {
            firstBattingTeam = team1Players;
            secondBattingTeam = team2Players;
        } else {
            firstBattingTeam = team2Players;
            secondBattingTeam = team1Players;
        }

        int target = simulateInnings(firstBattingTeam, scanner, Integer.MAX_VALUE) + 1;
        System.out.println("Target set: " + target + " runs.");

        System.out.println(teams[team1Index == 0 ? team2Index : team1Index] + " is now batting.");

        int secondTeamScore = simulateInnings(secondBattingTeam, scanner, target);

        if (secondTeamScore >= target) {
            System.out.println("Team 2 wins by " + (secondBattingTeam.length - wicketsLost) + " wickets!");
        } else if (secondTeamScore == target) {
            System.out.println("The match is a tie!");
        } else {
            System.out.println("Team 1 wins by " + (target - secondTeamScore) + " runs!");
        }
        pauseForResults();
    }

    @SuppressWarnings("resource")
    private Player[] selectPlayersForTeam(int teamIndex, int playerCount) {
        Scanner scanner = new Scanner(System.in);
        Player[] selectedPlayers = new Player[playerCount];

        System.out.println("Available players for " + teams[teamIndex] + ":");
        for (int i = 0; i < MAX_MEMBERS; i++) {
            Player player = teamMembers[teamIndex][i];
            if (player != null) {
                System.out.println((i + 1) + ". " + player.getName() + " (" + player.getPosition() + ")");
            }
        }

        for (int i = 0; i < playerCount; i++) {
            System.out.print("Select player " + (i + 1) + ": ");
            int playerIndex = scanner.nextInt() - 1;

            if (playerIndex < 0 || playerIndex >= MAX_MEMBERS || teamMembers[teamIndex][playerIndex] == null) {
                System.out.println("Invalid player selected.");
                return null;
            }

            selectedPlayers[i] = teamMembers[teamIndex][playerIndex];
        }

        return selectedPlayers;
    }

    private void pauseForResults() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 'exit' to exit or press Enter to continue viewing the results: ");
            String exit = scanner.nextLine();
            if (exit.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("You can continue viewing the results.");
            }
        }
    }

    private int simulateInnings(Player[] team, Scanner scanner, int target) throws InterruptedException {
        int score = 0;
        int wicketsLost = 0;
        int extras = 0;

        int striker = 0;
        int nonStriker = 1;

        for (int over = 0; over < maxOvers; over++) {
            System.out.println("\nOver " + (over + 1) + ":");
            System.out.println("Striker: " + team[striker].getName() + ", Non-striker: " + team[nonStriker].getName());

            for (int ball = 0; ball < BALLS_PER_OVER; ball++) {
                if (wicketsLost >= team.length) {
                    System.out.println("All wickets lost. Team is all-out.");
                    return score;
                }

                System.out.print(
                        "Enter outcome for ball " + (ball + 1) + " (0=Dot, 1-6=Runs, W=Wicket, N=No-ball, WD=Wide): ");
                String outcome = scanner.nextLine().trim().toUpperCase();

                switch (outcome) {
                    case "0":
                        System.out.println("Dot ball.");
                        break;
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "6":
                        int runs = Integer.parseInt(outcome);
                        System.out.println(runs + " runs.");
                        score += runs;

                        if (runs % 2 != 0) {
                            int temp = striker;
                            striker = nonStriker;
                            nonStriker = temp;
                        }
                        break;
                    case "W":
                        System.out.println("Wicket! " + team[striker].getName() + " is out.");
                        wicketsLost++;

                        striker = wicketsLost + 1;
                        if (striker >= team.length) {
                            System.out.println("All wickets lost. Team is all-out.");
                            return score;
                        }
                        break;
                    case "N":
                        System.out.println("No-ball.");
                        extras++;
                        score++;
                        ball--;
                        break;
                    case "WD":
                        System.out.println("Wide ball.");
                        extras++;
                        score++;
                        ball--;
                        break;
                    default:
                        System.out.println("Invalid input. Try again.");
                        ball--;
                        break;
                }

                System.out.println(
                        "Current striker: " + team[striker].getName() + ", Non-striker: " + team[nonStriker].getName());

                if (score >= target) {
                    System.out.println("Target achieved! Team wins.");
                    System.out.println("Final Score: " + score + " runs with " + wicketsLost + " wickets.");
                    System.out.println("Extras: " + extras);
                    return score;
                }
            }

            int temp = striker;
            striker = nonStriker;
            nonStriker = temp;

            if (wicketsLost >= team.length) {
                break;
            }
        }

        System.out.println("\nInnings complete. Total score: " + score + " runs with " + wicketsLost + " wickets.");
        System.out.println("Extras: " + extras);
        return score;
    }

    private String conductToss(String team1, String team2) {
        return Math.random() > 0.5 ? team1 : team2;
    }
}