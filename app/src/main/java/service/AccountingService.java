package service;

import dao.AccountingDao;
import domain.Accounting;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;

@Log4j2
public class AccountingService {
    private AccountingDao accountingDao;
    private ValidationService validationService;

    public AccountingService(AccountingDao accountingDao, ValidationService validationService) {
        this.accountingDao = accountingDao;
        this.validationService = validationService;
    }

    public int account(String login, String role, String path, String dateStart, String dateEnd, String volume,
                       ArrayList<Accounting> accounting) throws IOException, MyException {
        //Проверка валидности объема и дат.
        if ((!validationService.isValidVolume(volume)) || (!validationService.isValidDate(dateStart))
                || (!validationService.isValidDate(dateEnd))) {
            log.error("Invalid dates or volume");
            return 5;
        } else {
            Accounting account = new Accounting(login, role, path, dateStart, dateEnd, volume);
            accounting.add(account);
            accountingDao.setDataToTableAccounting(account);
            log.debug("Accounting is successful");
            return 0;
        }
    }
}