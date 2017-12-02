package service;

import dao.AaaDao;
import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AaaServiceTest {
    private AaaService aaaService;
    private int systemExitCode;
    private ArrayList<Accounting> accounting;

    @Before
    public void setUp() throws Exception {
        ValidationService validationService = new ValidationService();
        HashService hashService = new HashService();
        AaaDao aaaDao = mock(AaaDao.class);
        aaaService = new AaaService(aaaDao, validationService, hashService);
        User user = new User(1L, "Vasya", "82d8b0e268ddc216d347bcb95b158d92", "8169f7411b8f74150ad38d7d6b0435d");
        when(aaaDao.getDataFromTableUser("Vasya")).thenReturn(user);
        when(aaaDao.getDataFromTableUser("xxx")).thenReturn(null);

        ResourceUsersRoles res = new ResourceUsersRoles(1L, 1L, "READ", "A.B");
        when(aaaDao.getResourceFromTableResourceUsersRoles(user, "READ", "A.B")).thenReturn(res);
        when(aaaDao.getResourceFromTableResourceUsersRoles(user, "ROAD", "A.B")).thenReturn(null);
        when(aaaDao.getResourceFromTableResourceUsersRoles(user, "READ", "xxx")).thenReturn(null);
        when(aaaDao.getResourceFromTableResourceUsersRoles(user, "WRITE", "A.B")).thenReturn(null);

        accounting = new ArrayList<>();
    }

    private int authenticationSystemExitCode(String login, String password) throws MyException {
        return aaaService.authenticate(login, password);
    }

    private int authorizationSystemExitCode(String role, String resource) throws MyException {
        return aaaService.authorize("Vasya", role, resource);
    }

    private int accountingSystemExitCode(String startDate, String endDate, String volume) throws MyException {
        return aaaService.account(startDate, endDate, volume, accounting);
    }

    @Test
    public void authenticationInvalidLogin() throws Exception {
        systemExitCode = authenticationSystemExitCode("xxx", "qwerty");
        assertEquals(1, systemExitCode);
    }

    @Test
    public void authenticationInvalidPassword() throws Exception {
        systemExitCode = authenticationSystemExitCode("Vasya", "111");
        assertEquals(2, systemExitCode);
    }

    @Test
    public void authenticationSuccess() throws Exception {
        systemExitCode = authenticationSystemExitCode("Vasya", "qwerty");
        assertEquals(0, systemExitCode);
    }

////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void authorizationInvalidRole() throws Exception {
        systemExitCode = authorizationSystemExitCode("ROAD", "A.B");
        assertEquals(3, systemExitCode);
    }

    @Test
    public void authorizationInvalidResource() throws Exception {
        systemExitCode = authorizationSystemExitCode("READ", "AAA");
        assertEquals(4, systemExitCode);
    }

    @Test
    public void authorizationInvalidRoleForTheResource() throws Exception {
        systemExitCode = authorizationSystemExitCode("WRITE", "A.B");
        assertEquals(4, systemExitCode);
    }

    @Test
    public void authorizationSuccess() throws Exception {
        systemExitCode = authorizationSystemExitCode("READ", "A.B");
        assertEquals(0, systemExitCode);
    }

///////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void accountingInvalidStartDate() throws Exception {
        systemExitCode = accountingSystemExitCode("1111111111", "2017-10-08", "100");
        assertEquals(5, systemExitCode);
    }

    @Test
    public void accountingInvalidEndDate() throws Exception {
        systemExitCode = accountingSystemExitCode("2017-10-08", "1111111111", "100");
        assertEquals(5, systemExitCode);
    }

    @Test
    public void accountingInvalidVolume() throws Exception {
        systemExitCode = accountingSystemExitCode("2017-10-08", "2017-10-08", "str");
        assertEquals(5, systemExitCode);
    }

    @Test
    public void accountingSuccess() throws Exception {
        systemExitCode = accountingSystemExitCode("2017-10-08", "2017-10-08", "100");
        assertEquals(0, systemExitCode);
    }

}