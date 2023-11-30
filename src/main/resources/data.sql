--DELETE FROM Tags;
--DELETE FROM Measures;
--DELETE FROM Users;

INSERT INTO users (name)
VALUES ('Vasil');

INSERT INTO tags (name)
VALUES ('Перша страва'), ('Друга страва'), ('Десерт'), ('М''ясо'),
       ('Випічка'), ('Паста'), ('Каша'), ('Салат'), ('Консервація'),
       ('Риба'), ('Запіканка');

INSERT INTO ingredient_types (title)
VALUES ('liquid'), ('thick liquid'), ('weightable'), ('pieces'),
       ('crumbly'), ('spice'), ('bunches'), ('small pieces'),
       ('cloves'), ('weightable pieces'), ('slices');

INSERT INTO measures (title)
VALUES ('л'), ('мл'), ('ст. ложки'), ('ч. ложки'), ('г'), ('кг'),
       ('склянки'), ('штуки'), ('зубчики'), ('пучки'), ('скибки'),
       ('на смак');

INSERT INTO types_measuresjt (type_id, meas_id)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 7),
       (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7),
       (3, 5), (3, 6), (4, 8), (5, 3), (5, 4), (5, 5), (5, 6),
       (5, 7), (6, 4), (6, 5), (6, 12), (7, 5), (7, 10),
       (8, 5), (8, 8), (9, 5), (9, 9), (10, 5), (10, 6), (10, 8),
       (11, 5), (11, 11);

INSERT INTO ingredients (name, type)
VALUES ('Вода', 1), ('Молоко', 1), ('Морква', 3), ('Картопля', 3), ('Цибуля', 3),
       ('Часник', 9), ('Яловичина', 3), ('Свинина', 3), ('Курятина', 3), ('Яйце', 4),
       ('Борошно', 5), ('Сіль', 6), ('Цукор', 6), ('Мелений чорний перець', 6),
       ('Мелений червоний перець', 6), ('Капуста', 3), ('Сметана', 2), ('Оцет', 1),
       ('Буряк', 3), ('Петрушка', 7), ('Томатна паста', 2), ('Корінь петрушки', 10),
       ('Кріп', 7), ('Шпик', 3), ('Лавровий лист', 4), ('Чорний перець', 8),
       ('Духмяний перець', 8), ('М''ясний бульйон', 1), ('Бульйон з городини', 1), ('Бульйон', 1),
       ('Чорнослив', 8), ('Печериці', 3), ('Глива', 3), ('Помідор', 3),
       ('Огірок', 3), ('Солоний огірок', 3), ('Каперси', 8), ('Маслини', 8),
       ('Оливки', 8), ('Масло вершкове', 3), ('Олія соняшникова', 1), ('Олія оливкова', 1),
       ('Рослинна олія', 1), ('Солодкий перець', 3), ('Паприка', 6), ('Сир твердий', 3),
       ('Локшина', 3), ('Баранина', 3), ('Телятина', 3), ('Зелений горошок', 3), ('Зелена цибуля', 7),
       ('Імбир', 3), ('Риба прісноводна', 3), ('Риба морська', 3), ('Лимон', 4),
       ('Плавлений сир', 3), ('Гречка', 5), ('Рис', 5), ('Вівсянка', 5),
       ('Кінза', 6), ('Мелений перець чилі', 6), ('Пшоно', 5),
       ('Куряче філе', 3), ('Курячі серця', 3), ('Корінь селери', 3),
       ('Грибний бульйон', 1), ('Фарш яловичий', 3), ('Фарш асорті', 3), ('Фарш курячий', 3),
       ('Фарш свинячий', 3), ('Білий хліб', 11), ('Сухарі панірувальні', 3), ('Вершки', 1),
       ('Суміш італійських трав', 6), ('Суміш прованських трав', 6), ('Житній хліб', 11),
       ('Помідори у власному соку', 3), ('Хмелі-сунелі', 6), ('Рис довгозернистий', 5),
       ('Рис круглозернистий', 5), ('Гарбуз', 3), ('Кабачок', 3), ('Зіра', 6),
       ('Червона квасоля консервована', 3), ('Біла квасоля консервована', 3), ('Кукурудза консервована', 3),
       ('Перець чилі', 8), ('Хек', 10);

/*INSERT INTO ingredients (name, type)
VALUES ('Water', 1), ('Milk', 1), ('Carrot', 3), ('Potato', 3), ('Onion', 3),
       ('Garlic', 9), ('Beef', 3), ('Pork', 3), ('Chicken', 3), ('Egg', 4),
       ('Flour', 5), ('Salt', 6), ('Sugar', 6), ('Ground Black Pepper', 6),
       ('Ground Red Pepper', 6), ('Cabbage', 3), ('Sour Cream', 2), ('Vinegar', 1),
       ('Beet', 3), ('Parsley', 7), ('Tomato Paste', 2), ('Parsley Root', 10),
       ('Dill', 7), ('Fatback', 3), ('Bay Leaf', 4), ('Black Pepper', 8),
       ('Allspice', 8), ('Meat Broth', 1), ('Vegetable Broth', 1), ('Broth', 1),
       ('Prunes', 8), ('Common Mushroom', 3), ('Oyster Mushroom', 3), ('Tomato', 3),
       ('Cucumber', 3), ('Salted Cucumbers', 3), ('Capers', 8), ('Black Olives', 8),
       ('Green Olives', 8), ('Butter', 3), ('Sun Oil', 1), ('Olive Oil', 1),
       ('Vegetable Oil', 1), ('Sweet Pepper', 3), ('Ground Paprika', 6), ('Cheese', 3),
       ('Noodles', 3), ('Mutton', 3), ('Veal', 3), ('Green Peas', 3), ('Green Onion', 7),
       ('Ginger', 3), ('Freshwater Fish', 3), ('Sea Fish', 3), ('Lemon', 4),
       ('Processed Cheese', 3), ('Buckwheat', 5), ('Rice', 5), ('Oats', 5),
       ('Cilantro', 6), ('Grounded Chilli Pepper', 6), ('Millet', 5),
       ('Chicken Fillet', 3), ('Chicken Hearts', 3), ('Celery Root', 3),
       ('Mushroom Broth', 1), ('Beef Mince', 3), ('Mixed Mince', 3), ('Chicken Mince', 3),
       ('Pork Mince', 3), ('White Bread', 11), ('Breadcrumbs', 3), ('Cream', 1),
       ('Italian Herbs Mixture', 6), ('Provencal Herbs Mixture', 6), ('Rye bread', 11),
       ('Tomatoes in Their Own Juice', 3), ('Khmeli Suneli', 6), ('Long Grain Rice', 5),
       ('Round Grain Rice', 5), ('Pumpkin', 3), ('Marrow', 3), ('Cumin', 6),
       ('Canned Red Beans', 3), ('Canned White Beans', 3), ('Canned Corn', 3),
       ('Chilli Pepper', 8), ('Hake', 10);*/

