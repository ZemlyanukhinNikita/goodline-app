INSERT INTO USER (ID,LOGIN,PASSWORD,SALT) VALUES
  (1, 'Vasya', 'd8578edf8458ce6fbc5bb76a58c5ca4', '8169f7411b8f74150ad38d7d6b0435d'),
  (2, 'Vasya123', '202cb962ac5975b964b7152d234b70', '67e88bbe316e44638deecf475daff9f8'),
  (3, 'jdoe', 'e9c37b6b49be2d96f4664db72980321c', 'b1e26bb73d6fb25aae62b3fcc61cc7'),
  (4, 'jrow', 'e79913a0dbb45bcd7b6723523d68c146', '130aecb58f9d1f8326ce4a5b2'),
  (5, 'xxx', 'f0a458fd33489695d53df156b77c724', 'a989cbd96aaa7acdebdac36eb9cb6233');

INSERT INTO RESOURCE_USERS_ROLES VALUES
  (1,1,'READ','A.B'),
  (2,1,'READ','H.I.J'),
  (3,1,'WRITE','H.I.J'),
  (4,2,'EXECUTE','H.I.J'),
  (5,2,'EXECUTE','DDD'),
  (6,2,'READ','DDD'),
  (7,3,'READ','a'),
  (8,3,'WRITE','a.b'),
  (9,4,'EXECUTE','a.b.c'),
  (10,3,'EXECUTE','a.bc');