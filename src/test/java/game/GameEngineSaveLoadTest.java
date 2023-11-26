package game;

import common.Command;
import common.FileIO.FileIO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * this class tests the basics of the game engine
 * class.
 * @author soham
 */
class GameEngineSaveLoadTest {


    /**
     * Tests game save functionality, for invalid condition
     */
    @Test
    public void testGameSaveInValid() {
        GameEngine l_gameEngineTest = new GameEngine();
        l_gameEngineTest.initialise();
        l_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        l_gameEngineTest.submitCommand(Command.parseString("gameplayer -add v1 v2 v3 v4"));
        l_gameEngineTest.submitCommand(Command.parseString("savegame testsave.save"));
        assertFalse(FileIO.fileExists("testsave.save"));
        l_gameEngineTest.shutdown();
    }

    /**
     * Tests game save functionality, for valid condition
     */
    @Test
    public void testGameSaveValid() {
        GameEngine l_gameEngineTest = new GameEngine();
        l_gameEngineTest.initialise();
        l_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        l_gameEngineTest.submitCommand(Command.parseString("gameplayer -add v1 v2 v3 v4"));
        l_gameEngineTest.submitCommand(Command.parseString("assigncountries"));
        l_gameEngineTest.submitCommand(Command.parseString("savegame testsave.save"));
        assertTrue(FileIO.fileExists("testsave.save"));
        FileIO.removeFile("testsave.save");
        l_gameEngineTest.shutdown();
    }

    /**
     * Tests game load functionality
     */
    @Test
    public void testGameLoad() {
        //create a save
        GameEngine l_gameEngineTest = new GameEngine();
        l_gameEngineTest.initialise();
        l_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        l_gameEngineTest.submitCommand(Command.parseString("gameplayer -add v1 v2 v3 v4"));
        l_gameEngineTest.submitCommand(Command.parseString("assigncountries"));
        l_gameEngineTest.submitCommand(Command.parseString("savegame testsave.save"));
        assertTrue(FileIO.fileExists("testsave.save"));
        l_gameEngineTest.shutdown();

        // new engine instance to load the game
        l_gameEngineTest = new GameEngine();
        l_gameEngineTest.initialise();
        l_gameEngineTest.submitCommand(Command.parseString("loadgame testsave.save"));
        assertTrue(l_gameEngineTest.gameStarted());
        FileIO.removeFile("testsave.save");
        l_gameEngineTest.shutdown();
    }
}