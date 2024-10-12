CREATE DATABASE commerce;

\c commerce;

CREATE TABLE public.checkout (
	id uuid NOT NULL primary key,
	customer_name varchar NULL,
	card_number  varchar NULL,
	card_name varchar NULL,
	card_flag varchar NULL,
	card_due_date varchar NULL,
	card_cvv varchar NULL,
	amount decimal NULL,
	status int NULL
);
