# План 8
### 1. Добавить в pom.xml guice-persist (2 мин)
### 2. Переименовать доменные классы: (1 мин)
  - ResourceUsersRoles в Authority (30 сек)
  - Accounting в Activity (30 сек)
### 3. Добавить аннотации @Entity и @Table(name = “*”) в доменные классы: (3 мин)
  - User @Table(name = “user”) (1 мин)
  - Authority @Table(name = “authority”) (2 мин)
  - Activity @Table(name = “activity”) (3 мин)
### 4. Добавить новое поле Long version с аннотацией @Version в доменные классы: (3 мин)
  - User (1 мин)
  - Authority (1 мин)
  - Activity (1 мин)
### 5. Добавить аннотации @Id @GeneratedValue перед Id полями доменных классов. (2 мин)
### 6. Добавить аннотации @Column перед каждыми полями в доменных классах. (2 мин)
### 7. Сделать связь между виртуальными таблицами: (2 мин)
  - в классе Authority добавить аннотации @OneToMany и @JoinColumn(“user_id”) перед полем user. (1 мин)
  - в классе Activity добавить аннотации  @OneToMany и @JoinColumn(“authority_id”) перед полем authority. (1 мин)
### 8. Удалить поле userId из класса Authority. (1 мин)
### 9. Переименовать DAO классы: (3 мин)
  - AuthenticationDao в UserDao (1 мин)
  - AuthorizationDao в AuthorityDao (1 мин)
  - AccountingDao в ActivityDao (1 мин)
### 10. Создать файл persistence.xml (5 мин)
### 11. Создать класс EntityManager, имплеменить от провайдера (5 мин)
  - переопределить метод get()
### 12. Удалить классы DbConnectionService и ConnectionProvider (5 мин)
  - миграции перенести в класс EntityManager
### 13. Сделать получение параметров коннекта к БД через переменные окружения. (20 мин)
### 14. Адаптировать  миграции к PostgreSQL. (20 мин)
### 15. Перевести H2 в режим совместимости с PostgreSQL (20 мин)
### 16. Настроить PostgreSQL на heroku.com (20 мин)
### 17. Добавить файл Roadmap8.md в README.md (1 мин)

| Пункт плана                         | Оценка времени                | Фактическое время        |
|-------------------------------------|-------------------------------|--------------------------|
| 1.Добавить в pom.xml guice-persist		      |  2 минуты                    | 	           |
| 2.Переименовать доменные классы                      |  1 минута                    |           |
| 3.Добавить аннотации @Entity и @Table(name = “*”)                |  3 минуты                      | 	 |
| 4.Добавить новое поле Long version      |  3 минуты                     |       |
| 5.Добавить аннотации @Id @GeneratedValue      |  2 минуты                     | 	            |
| 6.Создать сервлет слушающий echo  |  2 минуты                     |               |
| 7.Добавить аннотации @Column                |  2 минуты                     | 	        |
| 8.Удалить поле userId               |  1 минута                     | 	      |
| 9.Переименовать DAO классы             |  3 минуты                     | 	        |
| 10.Создать файл persistence.xml                |  5 минут                     | 	       |
| 11.Создать класс EntityManager              |  5 минут                     | 	          |
| 12.Удалить классы DbConnectionService и ConnectionProvider  |  5 минут                     | 	              |
| 13.Сделать получение параметров коннекта к БД           |  20 минут                     | 	             |
| 14.Адаптировать  миграции к PostgreSQL     |  20 минут                     | 	           |
| 15.Перевести H2 в режим совместимости с PostgreSQL   |  20 минут                     | 	        |
| 16.Настроить PostgreSQL на heroku.com              |  20 минут                     | 	        |
| 17.Добавить файл Roadmap8.md в README.md             |  1 минута                     | 	       |
| Итого                               |  95 минут		      |	 |
