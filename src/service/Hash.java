package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static java.lang.Integer.toHexString;

public class Hash {
    private static String getHash(String source) {
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
            */
            for (byte aMessageDigest : messageDigest) {
                hexString.append(toHexString(0xFF & aMessageDigest));
            }
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return hexString.toString();
    }

    public static String getSalt() {
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
        */
        for (byte aSalt : salt) {
            s = String.valueOf(hexString.append(toHexString(0xFF & aSalt)));
        }
        return s;
    }

    static boolean isRightHashPassword(String pass, String userPass, String salt) {
        return getDoubleHash(pass, salt).equals(getDoubleHash(userPass, salt));
    }

    private static String getDoubleHash(String pass, String salt) {
        return getHash(getHash(pass) + salt);
    }
}
