CREATE TABLE IF NOT EXISTS recipes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    create_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    portions_number TINYINT
);

CREATE TABLE IF NOT EXISTS tags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS recipes_tagsjt (
    rec_id BIGINT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (rec_id, tag_id),
    FOREIGN KEY (rec_id) REFERENCES recipes (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cooking_stages (
    rec_id BIGINT NOT NULL,
    stage TINYINT NOT NULL,
    text VARCHAR(1000) NOT NULL,
    PRIMARY KEY (rec_id, stage),
    FOREIGN KEY (rec_id) REFERENCES recipes (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ingredient_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS measures (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS types_measuresjt (
    type_id INT NOT NULL,
    meas_id INT NOT NULL,
    PRIMARY KEY (type_id, meas_id),
    FOREIGN KEY (type_id) REFERENCES ingredient_types (id) ON DELETE CASCADE,
    FOREIGN KEY (meas_id) REFERENCES measures (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ingredients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(70) NOT NULL,
    type INT NOT NULL,
    FOREIGN KEY (type) REFERENCES ingredient_types (id)
);

CREATE TABLE IF NOT EXISTS recipes_ingredients (
    rec_id BIGINT NOT NULL,
    ing_id INT NOT NULL,
    meas_id INT NOT NULL,
    quantity DECIMAL(10,2),
    note VARCHAR(60),
    PRIMARY KEY (rec_id, ing_id),
    FOREIGN KEY (rec_id) REFERENCES recipes (id) ON DELETE CASCADE,
    FOREIGN KEY (ing_id) REFERENCES ingredients (id),
    FOREIGN KEY (meas_id) REFERENCES measures (id)
);

CREATE TABLE IF NOT EXISTS users (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS cooked_recipes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    rec_id BIGINT NOT NULL,
    date DATE NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (rec_id) REFERENCES recipes (id) ON DELETE CASCADE
);
