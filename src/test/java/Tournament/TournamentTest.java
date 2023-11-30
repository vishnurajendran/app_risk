package Tournament;

import common.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case for Tournament
 */
class TournamentTest {

    Tournament d_testTournament;

    ByteArrayOutputStream d_outputStream;

    @BeforeEach
    void setUp() {
        d_outputStream=new ByteArrayOutputStream();
        System.setOut(new PrintStream(d_outputStream));
        d_testTournament = new Tournament();
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    /**
     * Negative test case for invalid command arguments to tournament.
     */
    @Test
    void testIsCommandVaild() {
        Command l_command = Command.parseString("tournament -M testResources/ValidTestMap.map -P r");
        assertFalse(d_testTournament.IsCommandValid(l_command));
        assertEquals("Error!, Invalid arguments to tournament command",d_outputStream.toString().trim());
    }

    /**
     * Negative test case for invalid strategy.
     */
    @Test
    void testIsCommandInValidStrategy() {
        Command l_command = Command.parseString("tournament -M testResources/ValidTestMap.map -P r h -G 3 -D 15");
        assertFalse(d_testTournament.IsCommandValid(l_command));
        assertEquals("Error!, Strategy used for the tournament is invalid, please try again with valid strategy",d_outputStream.toString().trim());
    }

    /**
     * Negative test case for invalid map.
     */
    @Test
    void testIsCommandInValidMap() {
        Command l_command = Command.parseString("tournament -M testResources/1.map -P r h -G 3 -D 15");
        assertFalse(d_testTournament.IsCommandValid(l_command));
        assertEquals("Error!, Map used for the tournament is invalid, please try again with valid map files",d_outputStream.toString().trim());
    }

    /**
     * Negative test case for invalid game count.
     */
    @Test
    void testIsCommandInValidGameCount() {
        Command l_command = Command.parseString("tournament -M testResources/ValidTestMap.map -P r a -G -3 -D 10");
        assertFalse(d_testTournament.IsCommandValid(l_command));
        assertEquals("Error!, Invalid arguments to tournament command",d_outputStream.toString().trim());
    }

    /**
     * Negative test case for invalid turns.
     */
    @Test
    void testIsCommandInValidTurns() {
        Command l_command = Command.parseString("tournament -M testResources/ValidTestMap.map -P r a -G 3 -D 679");
        assertFalse(d_testTournament.IsCommandValid(l_command));
        assertEquals("Error!, Invalid arguments to tournament command",d_outputStream.toString().trim());
    }

    /**
     * Positive test case for Tournament
     */
    @Test
    void testStartTournament() {
        Command l_command = Command.parseString("tournament -M testResources/ValidTestMap.map -P r a -G 2 -D 10");
        d_testTournament.submitCommand(l_command);
        //assertEquals("Error!, Invalid arguments to tournament command",d_outputStream.toString().trim());
    }
}