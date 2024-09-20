/***
 * The Cricket Game GUI allows users to create teams and players, view team information, and simulate matches with interactive ball outcome inputs. Users can manage up to 10 teams, each with 11 players, and conduct matches with specified overs. The application provides a simple interface using Java Swing for seamless interaction.
 * Owner: Mayank Aitan
 * Date of creation: 18/09/2024
 */
package Cricketgame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// Define the Player class for team members
class Player {
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

// a method that define GUI view
public class CricketGameGUI {
    private static final int MAX_TEAMS = 10;
    private static final int MAX_MEMBERS = 11; // Max members per team in a cricket match
    private static final int BALLS_PER_OVER = 6;

    private String[] teams;
    private Player[][] teamMembers;
    private int teamCount;

    private JFrame frame;
    private JTextArea textArea;
    private JTextField inputField;
    private JButton submitButton;
    private JButton viewTeamsButton;
    private JButton addMembersButton;
    private JButton viewPlayersButton;
    private JButton createMatchButton;
    private JPanel panel;
    private JButton createTeamButton;
    private int wicketsLost;

    public CricketGameGUI() {
        teams = new String[MAX_TEAMS];
        teamMembers = new Player[MAX_TEAMS][MAX_MEMBERS];
        teamCount = 0;

        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Cricket Game");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        frame.add(inputField, BorderLayout.SOUTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 6));
        frame.add(panel, BorderLayout.NORTH);

        submitButton = new JButton("Submit");
        panel.add(submitButton);

        viewTeamsButton = new JButton("View Teams");
        panel.add(viewTeamsButton);

        addMembersButton = new JButton("Add Members");
        panel.add(addMembersButton);

        viewPlayersButton = new JButton("View Players");
        panel.add(viewPlayersButton);

        createMatchButton = new JButton("Create Match");
        panel.add(createMatchButton);

        createTeamButton = new JButton("Create Team");
        panel.add(createTeamButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInput();
            }
        });

        viewTeamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTeams();
            }
        });

        addMembersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTeamMembers();
            }
        });

        viewPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPlayers();
            }
        });

        createMatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createMatch();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        createTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTeam();
            }
        });

        frame.setVisible(true);
    }

    private void handleInput() {
        String input = inputField.getText().trim();
        inputField.setText("");
        appendToTextArea("Input: " + input);
    }

    private void appendToTextArea(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    // To create a new team
    public void createTeam() {
        String teamName = JOptionPane.showInputDialog(frame, "Enter the name of the team:");

        if (teamName == null || teamName.trim().isEmpty()) {
            appendToTextArea("Team name cannot be empty.");
            return;
        }

        for (int i = 0; i < teamCount; i++) {
            if (teams[i].equalsIgnoreCase(teamName)) {
                appendToTextArea("Team already exists.");
                return;
            }
        }

        if (teamCount >= MAX_TEAMS) {
            appendToTextArea("Maximum number of teams reached.");
            return;
        }

        teams[teamCount] = teamName;
        teamCount++;
        appendToTextArea("Team '" + teamName + "' created successfully.");
    }

    // view teams
    public void viewTeams() {
        if (teamCount == 0) {
            appendToTextArea("No teams have been created yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("Teams:\n");
        for (int i = 0; i < teamCount; i++) {
            sb.append((i + 1)).append(". ").append(teams[i]).append("\n");
        }
        appendToTextArea(sb.toString());
    }

    // add team members
    public void addTeamMembers() {
        if (teamCount == 0) {
            appendToTextArea("No teams available to add members.");
            return;
        }

        viewTeams();
        String teamNumberStr = JOptionPane.showInputDialog(frame, "Enter the number of the team to add members to:");
        int teamNumber;
        try {
            teamNumber = Integer.parseInt(teamNumberStr);
        } catch (NumberFormatException e) {
            appendToTextArea("Invalid team number.");
            return;
        }

        if (teamNumber < 1 || teamNumber > teamCount) {
            appendToTextArea("Invalid team number.");
            return;
        }

        int teamIndex = teamNumber - 1;
        String teamName = teams[teamIndex];

        int memberCount = 0;
        while (memberCount < MAX_MEMBERS && teamMembers[teamIndex][memberCount] != null) {
            memberCount++;
        }

        if (memberCount >= MAX_MEMBERS) {
            appendToTextArea("Maximum number of members reached for this team.");
            return;
        }

        String memberName = JOptionPane.showInputDialog(frame, "Enter the name of the new member:");
        if (memberName == null || memberName.trim().isEmpty()) {
            appendToTextArea("Member name cannot be empty.");
            return;
        }

        String position = JOptionPane.showInputDialog(frame,
                "Enter the position of the new member (e.g., Batsman, Bowler, Keeper):");
        if (position == null || position.trim().isEmpty()) {
            appendToTextArea("Position cannot be empty.");
            return;
        }

        for (int i = 0; i < memberCount; i++) {
            if (teamMembers[teamIndex][i].getName().equalsIgnoreCase(memberName)) {
                appendToTextArea("Member already exists in the team.");
                return;
            }
        }

        teamMembers[teamIndex][memberCount] = new Player(memberName, position);
        appendToTextArea(
                "Member '" + memberName + "' with position '" + position + "' added to team '" + teamName + "'.");
    }

    // view players in each team
    public void viewPlayers() {
        if (teamCount == 0) {
            appendToTextArea("No teams available.");
            return;
        }

        viewTeams();
        String teamNumberStr = JOptionPane.showInputDialog(frame, "Enter the number of the team to view players:");
        int teamNumber;
        try {
            teamNumber = Integer.parseInt(teamNumberStr);
        } catch (NumberFormatException e) {
            appendToTextArea("Invalid team number.");
            return;
        }

        if (teamNumber < 1 || teamNumber > teamCount) {
            appendToTextArea("Invalid team number.");
            return;
        }

        int teamIndex = teamNumber - 1;
        StringBuilder sb = new StringBuilder("Players in team '" + teams[teamIndex] + "':\n");

        boolean hasMembers = false;
        for (int i = 0; i < MAX_MEMBERS; i++) {
            if (teamMembers[teamIndex][i] != null) {
                sb.append((i + 1)).append(". ").append(teamMembers[teamIndex][i]).append("\n");
                hasMembers = true;
            }
        }

        if (!hasMembers) {
            sb.append("No members in this team.");
        }
        appendToTextArea(sb.toString());
    }

    // create a new match
    public void createMatch() throws InterruptedException {
        if (teamCount < 2) {
            appendToTextArea("At least two teams are required to create a match.");
            return;
        }

        viewTeams();

        String team1Str = JOptionPane.showInputDialog(frame, "Enter the number of the first team:");
        int team1;
        try {
            team1 = Integer.parseInt(team1Str) - 1;
        } catch (NumberFormatException e) {
            appendToTextArea("Invalid team number.");
            return;
        }

        if (team1 < 0 || team1 >= teamCount) {
            appendToTextArea("Invalid team number.");
            return;
        }

        String team2Str = JOptionPane.showInputDialog(frame, "Enter the number of the second team:");
        int team2;
        try {
            team2 = Integer.parseInt(team2Str) - 1;
        } catch (NumberFormatException e) {
            appendToTextArea("Invalid team number.");
            return;
        }

        if (team2 < 0 || team2 >= teamCount || team2 == team1) {
            appendToTextArea("Invalid team number or same team selected.");
            return;
        }

        String oversStr = JOptionPane.showInputDialog(frame, "Enter the total number of overs for the match:");
        int totalOvers;
        try {
            totalOvers = Integer.parseInt(oversStr);
        } catch (NumberFormatException e) {
            appendToTextArea("Invalid number of overs.");
            return;
        }

        appendToTextArea("Match created between '" + teams[team1] + "' and '" + teams[team2] + "' with " + totalOvers
                + " overs.");

        appendToTextArea("Conducting toss...");
        String tossWinner = conductToss(teams[team1], teams[team2]);
        appendToTextArea(tossWinner + " won the toss and will bat first!");

        Player[] firstBattingTeam, secondBattingTeam;
        if (tossWinner.equals(teams[team1])) {
            firstBattingTeam = teamMembers[team1];
            secondBattingTeam = teamMembers[team2];
        } else {
            firstBattingTeam = teamMembers[team2];
            secondBattingTeam = teamMembers[team1];
        }

        int target = Integer.MAX_VALUE;
        appendToTextArea("Inning 1: " + (tossWinner.equals(teams[team1]) ? teams[team1] : teams[team2]) + " vs "
                + (tossWinner.equals(teams[team1]) ? teams[team2] : teams[team1]));
        target = simulateInnings(firstBattingTeam, totalOvers, target) + 1;

        appendToTextArea("Target set: " + target + " runs.");

        appendToTextArea("Inning 2: " + (tossWinner.equals(teams[team1]) ? teams[team2] : teams[team1]) + " vs "
                + (tossWinner.equals(teams[team1]) ? teams[team1] : teams[team2]));
        int secondTeamScore = simulateInnings(secondBattingTeam, totalOvers, target);

        if (secondTeamScore >= target) {
            appendToTextArea("Match Result: " + (tossWinner.equals(teams[team1]) ? teams[team2] : teams[team1])
                    + " wins by " + ( teams.length- getWicketsLost()) + " wickets!");
        } else if (secondTeamScore == target) {
            appendToTextArea("Match Result: It's a tie!");
        } else {
            appendToTextArea("Match Result: " + (tossWinner.equals(teams[team1]) ? teams[team1] : teams[team2])
                    + " wins by " + (target - secondTeamScore) + " runs.");
        }
    }

    private int simulateInnings(Player[] team, int totalOvers, int target) throws InterruptedException {
        int score = 0;
        wicketsLost = 0;

        if (team.length < 2) {
            appendToTextArea("Not enough players to start the innings.");
            return score;
        }

        Player striker = team[0];
        Player nonStriker = team[1];
        int nextPlayerIndex = 2;

        for (int over = 0; over < totalOvers; over++) {
            appendToTextArea("Over " + (over + 1) + ": Striker: " + striker.getName());

            for (int ball = 0; ball < BALLS_PER_OVER; ball++) {
                if (wicketsLost == team.length - 1) {
                    appendToTextArea("All out! Innings ends.");
                    return score;
                }

                String outcome = JOptionPane.showInputDialog(frame,
                        "Ball " + (ball + 1) + ": Enter the outcome (0-6, W, NB, WD):");

                switch (outcome.toUpperCase()) {
                    case "0":
                        appendToTextArea("Dot ball.");
                        break;
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "6":
                        int runs = Integer.parseInt(outcome);
                        score += runs;
                        appendToTextArea(runs + " runs scored.");
                        if (runs % 2 != 0) {
                            Player temp = striker;
                            striker = nonStriker;
                            nonStriker = temp;
                        }
                        break;
                    case "W":
                        wicketsLost++;

                        if (nextPlayerIndex >= team.length) {
                            striker = null;
                            appendToTextArea("Wicket!! ");
                            appendToTextArea("No more players available. Innings ends.");
                            return score;
                        } else {
                            striker = team[nextPlayerIndex];
                            nextPlayerIndex++;
                        }
                        break;
                    case "NB":
                    case "WD":
                        appendToTextArea("Extra ball for " + outcome.toUpperCase() + ".");
                        score++;
                        ball--;
                        break;
                    default:
                        appendToTextArea("Invalid outcome. Please enter 0-6, W, NB, or WD.");
                        ball--;
                }


                if (striker != null && nonStriker != null) {
                    appendToTextArea("Striker: " + striker.getName() + ", Non-Striker: " + nonStriker.getName());
                } else {
                    appendToTextArea("Innings ends due to no players left.");
                    break;
                }
                if (score >= target && target != Integer.MAX_VALUE) {
                    appendToTextArea("Target reached! Innings ends.");
                    return score;
                }

            }

            Player temp = striker;
            striker = nonStriker;
            nonStriker = temp;
            appendToTextArea("score: "+score);
        }

        appendToTextArea("Innings ends with a score of " + score + " runs.");
        return score;
    }

    private String conductToss(String team1, String team2) {
        return Math.random() > 0.5 ? team1 : team2;
    }

    private int getWicketsLost() {
        return wicketsLost;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CricketGameGUI());
    }
}
