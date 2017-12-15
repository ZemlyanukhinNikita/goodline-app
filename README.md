# goodline-app
**goodline-app** - модельное приложение для аутентификации, авторизации и аккаунтинга пользователей, написанное на языке Java.

Данное приложение разразбатывается студентами Кафедры ["Информационных технологий и управления проектами Good Line"](http://kafedra-goodline.info) по направлению ["Программная инженерия"](http://kafedra-goodline.info/software-engineering). 

**Преподователь:** [Константин Линник](https://github.com/theaspect)
#### Описание приложения: 
Каждый пользователь этого приложения имеет логин, пароль, роль (чтение, запись, исполнение), ресурсы (доступ к каталогам), время проведенное в приложении и объем данных который он потребил.
Аутентификация пользователя прохожит по логину и паролю.
Авторизация пользователя проходит, если пройдена аутентификация и указаны верные роль и ресурсы.
Аккаунтинг пользователя проходит, если все параметры переданы и верны.

##### На каждом этапе разработки выдаются требования к приложению, по которым составляется план приложения: 

[План работ №1](Roadmap1.md)

[План работ №2](Roadmap2.md)

[План работ №3](Roadmap3.md)

[План работ №4](Roadmap4.md)

[План работ №5](Roadmap5.md)

[План работ №6](Roadmap6.md)

Также обязательным требованием является написание тестов. На данный момент приложение протестировано в системе **travis-ci.org** [![Build Status](https://travis-ci.org/ZemlyanukhinNikita/goodline-app.svg?branch=master)](https://travis-ci.org/ZemlyanukhinNikita/goodline-app) 

### Инструкция по сборке, запуску и тестированию приложения:

1. За процесс сборки приложения отвечает команда `mvn package`
 
2. За процесс запуска тестов отвечает команда `mvn test`

3. За процесс генерации отчетов отвечает команда `mvn site`
  - отчет по покрытию тестами Coberture
  - отчет на соответствие codestyle Checkstyle
  - отчет по статическому анализу кода PMD
  - отчет по статическому анализу кода FindBugs

### Github Pages

Для репозитория включена опция Github Pages. Из этого файла README.md генерируется статический сайт 
[My Github Pages](https://zemlyanukhinnikita.github.io/goodline-app/)
