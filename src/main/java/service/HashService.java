package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static java.lang.Integer.toHexString;

public class HashService {
    private static final Logger logger = LogManager.getLogger(HashService.class.getName());

    private String getHash(String source) {
        //Создаем экземпляр класса
        MessageDigest md5;
        //Создаем строку в которую запишем хешированный пароль
        StringBuilder hexString = new StringBuilder();
        try {
            //Возвращаем объект MessageDigest, который реализует указанный алгоритм md5
            md5 = MessageDigest.getInstance("md5");
            //Сбрасываем дайджест для дальнейшего использования
            md5.reset();
            //Обновляем дайджест, используя указанный массив байтов
            md5.update(source.getBytes());
            //Записываем в messageDigest[] массив байтов полученного хеш значения
            byte messageDigest[] = md5.digest();
            /*Конвертируем байты в 16-й формат и записываем в строку
            Сначала возвращаем строковое представление целочисленного аргумента,
            как целое число без знака в 16-й системе методом toHexString().
            Далее в нашу строчку hexString добавляем полученную методом append().
            Т.к в java byte принимает значения от -128 до 127,
            этой командой мы убираем знак (0xFF & aMessageDigest). Теперь byte принимает
            значения от 0 до 255.
            */
            for (byte aMessageDigest : messageDigest) {
                hexString.append(toHexString(0xFF & aMessageDigest));
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("HashService isn`t generate", e);
            return null;
        }
        logger.debug("return hash password");
        return hexString.toString();
    }

    public String getSalt() {
        StringBuffer hexString = new StringBuffer();
        SecureRandom random = new SecureRandom();
        //Создаем байтовый массив
        byte[] salt = new byte[16];
        //Заполняем рандомными байтами
        random.nextBytes(salt);
        String s = "";
        /*Конвертируем байты в 16-й формат и записываем в строку.
        Сначала возвращаем строковое представление целочисленного аргумента,
        как целое число без знака в 16-й системе методом toHexString().
        Далее в нашу строчку hexString добавляем полученную методом append().
        Т.к в java byte принимает значения от -128 до 127,
        этой командой мы убираем знак (0xFF & aMessageDigest). Теперь byte принимает
        значения от 0 до 255.
        */
        for (byte aSalt : salt) {
            s = String.valueOf(hexString.append(toHexString(0xFF & aSalt)));
        }
        logger.debug("return hash salt");
        return s;
    }

    boolean isRightHashPassword(String pass, String userPass, String salt) {
        return userPass.equals(getHash(getHash(pass) + salt));
    }
}
