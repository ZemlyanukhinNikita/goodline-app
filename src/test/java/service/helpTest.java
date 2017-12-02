package service;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class helpTest {
    private UserData userData;
    private int systemExitCode;
    private CmdParserService cmdParserService;

    @Before
    public void setUp() {
        userData = new UserData();
        cmdParserService = new CmdParserService();
    }

    @Test
    public void PrintHelp() {
        userData.setLogin("Vasya");
        systemExitCode = isAuth();
        assertEquals(1, systemExitCode);
    }

    @Test
    public void notPrintHelp() {
        userData.setLogin("Vasya");
        userData.setPassword("qwerty");
        systemExitCode = isAuth();
        assertEquals(0, systemExitCode);
    }

    private int isAuth() {
        if (userData.isAuthenticated()) {
            return 0;
        }
        cmdParserService.printHelp();
        return 1;
    }
}