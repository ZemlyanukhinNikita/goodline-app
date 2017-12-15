package service;

import dao.AaaDao;
import domain.Accounting;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

@Log4j2
public class AccountingService {
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
            log.error("Invalid dates or volume");
            return 5;
        } else {
            Accounting account = new Accounting(dateStart, dateEnd, volume);
            accounting.add(account);
            aaaDao.setDataToTableAccounting(account);
            log.debug("Accounting is successful");
            return 0;
        }
    }
}