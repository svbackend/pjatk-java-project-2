package puzzle.Scoreboard;

import puzzle.Game.SpentTime;

import java.io.*;
import java.util.ArrayList;

public class ScoreboardService {
    private final static String FILE = "./scoreboard.txt";
    private final static String DELIMITER = "\t";

    public static void addNewScore(String username, String difficulty, SpentTime spentTime) throws IOException {
        String newScore = username + DELIMITER + difficulty + DELIMITER + spentTime.toString();

        FileWriter fileWriter = new FileWriter(ScoreboardService.FILE, true);
        fileWriter.write(newScore);
        fileWriter.write("\r\n");
        fileWriter.close();
    }

    public static ArrayList<ScoreDTO> getAll() {
        ArrayList<ScoreDTO> scores = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(ScoreboardService.FILE));

            String line = reader.readLine();
            while (line != null && !line.equals("")) {
                scores.add(parseLine(line));
                line = reader.readLine();
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.err.println(ScoreboardService.FILE + " -- not found!");
            return scores;
        } catch (IOException e) {
            e.printStackTrace();
            return scores;
        }

        return scores;
    }

    private static ScoreDTO parseLine(String line) {
        String[] data = line.split(ScoreboardService.DELIMITER);
        return new ScoreDTO(
            data[0],
            data[1],
            data[2]
        );
    }
}
