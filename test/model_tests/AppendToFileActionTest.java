package model_tests;

import softeng_project_group08.model.AppendToFileAction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author group08
 */
public class AppendToFileActionTest {

    private static final String FILE_PATH = "testFile.txt";
    private static final String INITIAL_CONTENT = "Contenuto iniziale del file.";

    @Before
    public void setUp() {
        // Check if the file already exists before using it
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(INITIAL_CONTENT);
            System.out.println("Test file created with initial content.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
        // Tear down of the testing file
        try {
            Files.deleteIfExists(Paths.get(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAppendToFile() {
        AppendToFileAction action = new AppendToFileAction("String to add", FILE_PATH);
        action.execute();

        // Read the last line of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String lastLine = "";

            while ((line = reader.readLine()) != null) {
                lastLine = line; // Update lastLine with each line read
            }

            assertEquals("String not found in the last line of the file", "String to add", lastLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
