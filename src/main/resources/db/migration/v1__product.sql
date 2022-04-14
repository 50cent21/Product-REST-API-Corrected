DROP TABLE IF EXISTS product;

CREATE TABLE product(
    id SERIAL CONSTRAINT product_pk PRIMARY KEY,
    code BIGINT UNIQUE NOT NULL,
    name varchar(50) NOT NULL,
    price_hrk decimal NOT NULL,
    price_eur decimal DEFAULT NULL,
    description varchar(50) DEFAULT NULL,
    is_available boolean DEFAULT false
);
