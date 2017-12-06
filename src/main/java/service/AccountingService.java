package service;

import dao.AaaDao;
import domain.Accounting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AccountingService {
    private static final Logger logger = LogManager.getLogger(AccountingService.class.getName());
    private AaaDao aaaDao;
    private ValidationService validationService;

    public AccountingService(AaaDao aaaDao, ValidationService validationService) {
        this.aaaDao = aaaDao;
        this.validationService = validationService;
    }

    public int account(String dateStart, String dateEnd, String volume,
                       ArrayList<Accounting> accounting) {
        //Проверка валидности объема и дат.
        if ((!validationService.isValidVolume(volume)) || (!validationService.isValidDate(dateStart))
                || (!validationService.isValidDate(dateEnd))) {
            logger.error("Invalid dates or volume");
            return 5;
        } else {
            Accounting account = new Accounting(dateStart,dateEnd,volume);
            accounting.add(account);
            aaaDao.setDataToTableAccounting(account);
            logger.debug("Accounting is successful");
            return 0;
        }
    }
}