INSERT INTO USER (ID, LOGIN, PASSWORD, SALT) VALUES
  (1, 'Vasya', '82d8b0e268ddc216d347bcb95b158d92', '8169f7411b8f74150ad38d7d6b0435d'),
  (2, 'Vasya123', '27659c386f539b79b3344ff8351956', '67e88bbe316e44638deecf475daff9f8'),
  (3, 'jdoe', 'f9f9e08b5f19658e76cb5d61dcdfc3c4', 'b1e26bb73d6fb25aae62b3fcc61cc7'),
  (4, 'jrow', '11c3d296c2c971abc3510d385ce7c76', '130aecb58f9d1f8326ce4a5b2'),
  (5, 'xxx', '66327f90935047794391d476e3fb4dd', 'a989cbd96aaa7acdebdac36eb9cb6233');

INSERT INTO RESOURCE_USERS_ROLES (ID, USER_ID, ROLE, PATH) VALUES
  (1, 1, 'READ', 'A.B'),
  (2, 1, 'READ', 'H.I.J'),
  (3, 1, 'WRITE', 'H.I.J'),
  (4, 2, 'EXECUTE', 'H.I.J'),
  (5, 2, 'EXECUTE', 'DDD'),
  (6, 2, 'READ', 'DDD'),
  (7, 3, 'READ', 'a'),
  (8, 3, 'WRITE', 'a.b'),
  (9, 4, 'EXECUTE', 'a.b.c'),
  (10, 3, 'EXECUTE', 'a.bc');