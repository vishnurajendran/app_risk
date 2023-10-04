package common;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class verifies the correctness of the
 * Command class. the class test various types of
 * commands that can be provided by user and checks if
 * parsing of said commands happen correctly.
 */
class CommandTest {

    /**
     * this test is aimed at testing if
     * an empty or null strings are parsed correctly.
     */
    @org.junit.jupiter.api.Test
    public void testCmdStringEmpty() {
        assertNull(Command.parseString(""));
        assertNull(Command.parseString(" "));
        assertNull(Command.parseString(null));
    }

    /**
     * This test aims at verifying whether commands
     * without any args or options are parsed correctly
     * eg: editname
     */
    @org.junit.jupiter.api.Test
    public void testNoOptionNoArgs() {
        var l_cmd = Command.parseString("cmd");
        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdAttributes().isEmpty());
    }

    /**
     * This test aims at verifying whether commands with args
     * without any options are parsed correctly
     * eg: copy file.txt file2.txt
     */
    @org.junit.jupiter.api.Test
    public void testNoOptionArgs() {
        var l_cmd = Command.parseString("cmd arg1 arg2 arg3");
        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdAttributes().size() == 1);
        assertTrue(l_cmd.getCmdAttributes().get(0).getOption().isBlank());
        String[] l_argsRef = {"arg1", "arg2", "arg3"};
        assertArrayEquals(l_cmd.getCmdAttributes().get(0).getArguments().toArray(), l_argsRef);
    }

    /**
     * This test aims at verifying whether commands with options
     * without any args are parsed correctly
     * eg: boot -cold
     */
    @org.junit.jupiter.api.Test
    public void testOneOptionNoArgs() {
        var l_cmd = Command.parseString("cmd -option");
        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdAttributes().size() == 1);
        assertEquals(l_cmd.getCmdAttributes().get(0).getOption(), "option");
        assertTrue(l_cmd.getCmdAttributes().get(0).getArguments().isEmpty());
    }

    /**
     * This test aims at verifying whether commands
     * with option and args are parsed correctly
     * eg: player -add godofnoobs
     */
    @org.junit.jupiter.api.Test
    public void testOneOptionArgs() {
        var l_cmd = Command.parseString("cmd -option arg1 arg2 arg3");
        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdAttributes().size() == 1);
        assertEquals(l_cmd.getCmdAttributes().get(0).getOption(), "option");
        String[] l_argsRef = {"arg1", "arg2", "arg3"};
        assertArrayEquals(l_cmd.getCmdAttributes().get(0).getArguments().toArray(), l_argsRef);
    }

    /**
     * This test aims at verifying whether commands with multiple options
     * without any args are parsed correctly
     * eg: boot -cold -safe
     */
    @org.junit.jupiter.api.Test
    public void testMultipleOptionNoArgs() {
        var l_cmd = Command.parseString("cmd -option1 -option2");
        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdAttributes().size() == 2);

        assertEquals(l_cmd.getCmdAttributes().get(0).getOption(), "option1");
        assertTrue(l_cmd.getCmdAttributes().get(0).getArguments().isEmpty());

        assertEquals(l_cmd.getCmdAttributes().get(1).getOption(), "option2");
        assertTrue(l_cmd.getCmdAttributes().get(1).getArguments().isEmpty());
    }

    /**
     * This test aims at verifying whether commands
     * with multiple option all with args are parsed correctly
     * eg: player -add godofnoobs
     */
    @org.junit.jupiter.api.Test
    public void testMutipleOptionOptionAllArgs() {
        Command l_cmd = Command.parseString("cmd -option1 arg1 arg2 -option2 arg3");
        String[] l_arg1Ref = {"arg1", "arg2"};
        String[] l_arg2Ref = {"arg3"};

        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdAttributes().size() == 2);

        assertEquals(l_cmd.getCmdAttributes().get(0).getOption(), "option1");
        assertArrayEquals(l_cmd.getCmdAttributes().get(0).getArguments().toArray(), l_arg1Ref);

        assertEquals(l_cmd.getCmdAttributes().get(1).getOption(), "option2");
        assertArrayEquals(l_cmd.getCmdAttributes().get(1).getArguments().toArray(), l_arg2Ref);
    }

    /**
     * This test aims at verifying whether commands
     * with multiple option with some having args are parsed correctly
     * eg: player -add godofnoobs
     */
    @org.junit.jupiter.api.Test
    public void testMutipleOptionOptionSomeArgs() {
        Command l_cmd = Command.parseString("cmd -option1 -option2 arg1 arg2 -option3 arg3");
        String[] l_arg1Ref = {"arg1", "arg2"};
        String[] l_arg2Ref = {"arg3"};

        assertEquals("cmd", l_cmd.getCmdName());
        assertTrue(l_cmd.getCmdAttributes().size() == 3);

        assertEquals(l_cmd.getCmdAttributes().get(0).getOption(), "option1");
        assertTrue(l_cmd.getCmdAttributes().get(0).getArguments().isEmpty());

        assertEquals(l_cmd.getCmdAttributes().get(1).getOption(), "option2");
        assertArrayEquals(l_cmd.getCmdAttributes().get(1).getArguments().toArray(), l_arg1Ref);

        assertEquals(l_cmd.getCmdAttributes().get(2).getOption(), "option3");
        assertArrayEquals(l_cmd.getCmdAttributes().get(2).getArguments().toArray(), l_arg2Ref);
    }
}