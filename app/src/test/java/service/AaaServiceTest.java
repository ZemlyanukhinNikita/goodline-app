package service;

import dao.AccountingDao;
import dao.AuthenticationDao;
import dao.AuthorizationDao;
import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.User;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AaaServiceTest {
    private AuthenticationDao authenticationDao;
    private AuthorizationDao authorizationDao;
    private AccountingDao accountingDao;
    private AuthenticationService authenticationService;
    private AuthorizationService authorizationService;
    private AccountingService accountingService;
    private int systemExitCode;
    private ArrayList<Accounting> accounting;

    @Before
    public void setUp() throws Exception {
        ValidationService validationService = new ValidationService();
        HashService hashService = new HashService();
        authenticationDao = mock(AuthenticationDao.class);
        authorizationDao = mock(AuthorizationDao.class);
        accountingDao = mock(AccountingDao.class);
        authenticationService = new AuthenticationService(hashService, authenticationDao);
        authorizationService = new AuthorizationService(authorizationDao, authenticationDao);
        accountingService = new AccountingService(accountingDao, validationService);
        accounting = new ArrayList<>();
    }

    private int authenticationSystemExitCode(String login,
                                             String password) throws MyException, IOException {
        return authenticationService.authenticate(login, password);
    }

    private int authorizationSystemExitCode(String role,
                                            String resource) throws MyException, IOException {
        return authorizationService.authorize("Vasya", role, resource);
    }

    private int accountingSystemExitCode(String login, String role, String path, String startDate,
                                         String endDate, String volume) throws MyException, IOException {
        return accountingService.account(login, role, path, startDate, endDate, volume, accounting);
    }

    @Test(expected = MyException.class)
    public void authenticationEmptyLogin() throws MyException, IOException {
        when(authenticationDao.getDataFromTableUser("")).thenThrow(new MyException("Empty login"));
        authenticationSystemExitCode("", "");
        verify(authenticationDao).getDataFromTableUser("");
    }

    @Test
    public void authenticationInvalidLogin() throws Exception {
        systemExitCode = authenticationSystemExitCode("xxx", "qwerty");
        assertEquals(1, systemExitCode);
        verify(authenticationDao, atLeast(1)).getDataFromTableUser("xxx");
    }

    @Test
    public void authenticationInvalidPassword() throws Exception {
        User user = new User(1L, "Vasya", "82d8b0e268ddc216d347bcb95b158d92",
                "8169f7411b8f74150ad38d7d6b0435d");
        when(authenticationDao.getDataFromTableUser("Vasya")).thenReturn(user);
        systemExitCode = authenticationSystemExitCode("Vasya", "111");
        assertEquals(2, systemExitCode);
        verify(authenticationDao, atLeast(1)).getDataFromTableUser("Vasya");
    }

    @Test
    public void authenticationSuccess() throws Exception {
        User user = new User(1L, "Vasya", "82d8b0e268ddc216d347bcb95b158d92",
                "8169f7411b8f74150ad38d7d6b0435d");
        when(authenticationDao.getDataFromTableUser("Vasya")).thenReturn(user);
        systemExitCode = authenticationSystemExitCode("Vasya", "qwerty");
        assertEquals(0, systemExitCode);
        verify(authenticationDao, atLeast(1)).getDataFromTableUser("Vasya");
    }

////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = MyException.class)
    public void authorizationEmptyResource() throws MyException, IOException {
        when(authorizationDao.getResourceFromTableResourceUsersRoles(null, "READ", "")).
                thenThrow(new MyException("Empty resource"));
        authorizationSystemExitCode("READ", "");
        verify(authorizationDao).getResourceFromTableResourceUsersRoles(null, "", "");
    }

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
        User user = new User(1L, "Vasya",
                "82d8b0e268ddc216d347bcb95b158d92", "8169f7411b8f74150ad38d7d6b0435d");
        when(authenticationDao.getDataFromTableUser("Vasya")).thenReturn(user);
        ResourceUsersRoles res = new ResourceUsersRoles(1L, 1L, "READ", "A.B");
        when(authorizationDao.getResourceFromTableResourceUsersRoles(user,
                "READ", "A.B")).thenReturn(res);
        systemExitCode = authorizationSystemExitCode("READ", "A.B");
        assertEquals(0, systemExitCode);
        verify(authorizationDao).getResourceFromTableResourceUsersRoles(user, "READ", "A.B");
    }

///////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void accountingInvalidStartDate() throws Exception {
        systemExitCode = accountingSystemExitCode("Vasya",
                "READ", "A.B", "1111111111", "2017-10-08", "100");
        assertEquals(5, systemExitCode);
    }

    @Test
    public void accountingInvalidEndDate() throws Exception {
        systemExitCode = accountingSystemExitCode("Vasya",
                "READ", "A.B", "2017-10-08", "1111111111", "100");
        assertEquals(5, systemExitCode);
    }

    @Test
    public void accountingInvalidVolume() throws Exception {
        systemExitCode = accountingSystemExitCode("Vasya",
                "READ", "A.B", "2017-10-08", "2017-10-08", "str");
        assertEquals(5, systemExitCode);
    }

    @Test
    public void accountingSuccess() throws Exception {
        systemExitCode = accountingSystemExitCode("Vasya",
                "READ", "A.B", "2017-10-08", "2017-10-08", "100");
        assertEquals(0, systemExitCode);
        verify(accountingDao).setDataToTableAccounting(accounting.get(0));
    }
}