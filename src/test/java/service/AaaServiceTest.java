package service;

import dao.AaaDao;
import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AaaServiceTest {
    private AaaDao aaaDao;
    private AuthenticationService authenticationService;
    private AuthorizationService authorizationService;
    private AccountingService accountingService;
    private int systemExitCode;
    private ArrayList<Accounting> accounting;

    @Before
    public void setUp() throws Exception {
        ValidationService validationService = new ValidationService();
        HashService hashService = new HashService();
        aaaDao = mock(AaaDao.class);
        authenticationService = new AuthenticationService(hashService,aaaDao);
        authorizationService = new AuthorizationService(aaaDao);
        accountingService = new AccountingService(aaaDao,validationService);
        accounting = new ArrayList<>();
    }

    private int authenticationSystemExitCode(String login, String password) throws MyException {
        return authenticationService.authenticate(login, password);
    }

    private int authorizationSystemExitCode(String role, String resource) throws MyException {
        return authorizationService.authorize("Vasya", role, resource);
    }

    private int accountingSystemExitCode(String startDate, String endDate, String volume) throws MyException {
        return accountingService.account(startDate,endDate,volume, accounting);
    }

    @Test(expected = MyException.class)
    public void authenticationEmptyLogin() throws MyException {
        when(aaaDao.getDataFromTableUser("")).thenThrow(new MyException("Empty login"));
        authenticationSystemExitCode("","");
        verify(aaaDao).getDataFromTableUser("");
    }

    @Test
    public void authenticationInvalidLogin() throws Exception {
        systemExitCode = authenticationSystemExitCode("xxx", "qwerty");
        assertEquals(1, systemExitCode);
        verify(aaaDao,atLeast(1)).getDataFromTableUser("xxx");
    }

    @Test
    public void authenticationInvalidPassword() throws Exception {
        User user = new User(1L, "Vasya", "82d8b0e268ddc216d347bcb95b158d92",
                "8169f7411b8f74150ad38d7d6b0435d");
        when(aaaDao.getDataFromTableUser("Vasya")).thenReturn(user);
        systemExitCode = authenticationSystemExitCode("Vasya", "111");
        assertEquals(2, systemExitCode);
        verify(aaaDao,atLeast(1)).getDataFromTableUser("Vasya");
    }

    @Test
    public void authenticationSuccess() throws Exception {
        User user = new User(1L, "Vasya", "82d8b0e268ddc216d347bcb95b158d92",
                "8169f7411b8f74150ad38d7d6b0435d");
        when(aaaDao.getDataFromTableUser("Vasya")).thenReturn(user);
        systemExitCode = authenticationSystemExitCode("Vasya", "qwerty");
        assertEquals(0, systemExitCode);
        verify(aaaDao,atLeast(1)).getDataFromTableUser("Vasya");
    }

////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = MyException.class)
    public void authorizationEmptyResource() throws MyException {
        when(aaaDao.getResourceFromTableResourceUsersRoles(null, "READ", "")).thenThrow(new MyException("Empty resource"));
        authorizationSystemExitCode("READ","");
        verify(aaaDao).getResourceFromTableResourceUsersRoles(null,"","");
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
        User user = new User(1L, "Vasya", "82d8b0e268ddc216d347bcb95b158d92", "8169f7411b8f74150ad38d7d6b0435d");
        when(aaaDao.getDataFromTableUser("Vasya")).thenReturn(user);
        ResourceUsersRoles res = new ResourceUsersRoles(1L, 1L, "READ", "A.B");
        when(aaaDao.getResourceFromTableResourceUsersRoles(user, "READ", "A.B")).thenReturn(res);
        systemExitCode = authorizationSystemExitCode("READ", "A.B");
        assertEquals(0, systemExitCode);
        verify(aaaDao).getResourceFromTableResourceUsersRoles(user,"READ","A.B");
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
        verify(aaaDao).setDataToTableAccounting(accounting.get(0));
    }
}