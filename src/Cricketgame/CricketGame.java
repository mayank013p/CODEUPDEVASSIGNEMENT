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
                return;
            }
        }

        teams[teamCount] = teamName;
        teamCount++;
        System.out.println("Team '" + teamName + "' created successfully.");
    }

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
        System.out.println("Member '" + memberName + "' with position '" + position + "' added to team '" + teamName + "'.");
    }

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
            return;
        }

        System.out.print("Enter the number of the player to view details (or 0 to exit): ");
        int playerNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (playerNumber < 1 || playerNumber > MAX_MEMBERS || teamMembers[teamIndex][playerNumber - 1] == null) {
            if (playerNumber != 0) {
                System.out.println("Invalid player number.");
            }
            return;
        }

        if (playerNumber == 0) {
            return;
        }

        Player selectedPlayer = teamMembers[teamIndex][playerNumber - 1];
        System.out.println("Details of player '" + selectedPlayer.getName() + "':");
        System.out.println("Position: " + selectedPlayer.getPosition());
    }
}
