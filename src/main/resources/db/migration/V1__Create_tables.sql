CREATE TABLE IF NOT EXISTS users (

	id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	username varchar UNIQUE NOT NULL,
	password varchar NOT NULL,
	user_role varchar NOT NULL DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS bonus_cards (

	id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	card_num varchar UNIQUE NOT NULL,
	balance decimal(10, 2) DEFAULT 0.00 NOT NULL,
	user_id int NOT NULL,
	FOREIGN KEY(user_id) REFERENCES users(id)
);


CREATE TABLE IF NOT EXISTS operations (

	id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	type varchar NOT NULL,
	card_num varchar NOT NULL,
	amount decimal(10, 2) NOT NULL,
	operation_time timestamp DEFAULT current_timestamp,
	FOREIGN KEY(card_num) REFERENCES bonus_cards(card_num)
);
