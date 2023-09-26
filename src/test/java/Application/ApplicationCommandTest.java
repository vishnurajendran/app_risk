package Application;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationCommandTest {

    /**
     * this test is aimed at testing if
     * an empty or null strings are parsed correctly.
     */
    @org.junit.jupiter.api.Test
    public void testCmdStringEmpty(){
        assertNull(ApplicationCommand.parseString(""));
        assertNull(ApplicationCommand.parseString(" "));
        assertNull(ApplicationCommand.parseString(null));
    }

    /**
     * This test aims at verifying whether commands
     * without any args or options are parsed correctly
     * eg: editname
     */
    @org.junit.jupiter.api.Test
    public void testNoOptionNoArgs() {
        var l_cmd = ApplicationCommand.parseString("cmd");
        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdOption().isBlank());
        assertTrue(l_cmd.getCmdArgs().isEmpty());
    }

    /**
     * This test aims at verifying whether commands with args
     * without any options are parsed correctly
     * eg: copy file.txt file2.txt
     */
    @org.junit.jupiter.api.Test
    public void testNoOptionArgs() {
        var l_cmd = ApplicationCommand.parseString("cmd arg1 arg2 arg3");
        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdOption().isBlank());
        String[] l_argsRef = {"arg1", "arg2", "arg3"};
        assertArrayEquals(l_cmd.getCmdArgs().toArray(), l_argsRef);
    }

    /**
     * This test aims at verifying whether commands with options
     * without any args are parsed correctly
     * eg: boot -cold
     */
    @org.junit.jupiter.api.Test
    public void testOptionNoArgs() {
        var l_cmd = ApplicationCommand.parseString("cmd -option");
        assertEquals("cmd", l_cmd.getCmdName());
        assertEquals(l_cmd.getCmdOption(),"option");
        assertTrue(l_cmd.getCmdArgs().isEmpty());
    }

    /**
     * This test aims at verifying whether commands
     * with option and args are parsed correctly
     * eg: player -add godofnoobs
     */
    @org.junit.jupiter.api.Test
    public void testOptionArgs() {
        var l_cmd = ApplicationCommand.parseString("cmd -option arg1 arg2 arg3");
        assertEquals("cmd", l_cmd.getCmdName());
        assertEquals(l_cmd.getCmdOption(),"option");
        String[] l_argsRef = {"arg1", "arg2", "arg3"};
        assertArrayEquals(l_cmd.getCmdArgs().toArray(), l_argsRef);
    }
}