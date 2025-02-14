-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.

DROP TABLE IF EXISTS public.user_requests;
CREATE TABLE IF NOT EXISTS public.user_requests
(
    request_id uuid ,
    user_id uuid ,
    user_requests_id uuid DEFAULT gen_random_uuid()  NOT NULL ,
    CONSTRAINT user_requests_pkey PRIMARY KEY (user_requests_id)
);

DROP TABLE IF EXISTS public.requests;
CREATE TABLE IF NOT EXISTS public.requests
(
    date date NOT NULL,
    price_id uuid ,
    request_id uuid DEFAULT gen_random_uuid()  NOT NULL ,
    ticker_id uuid ,
    CONSTRAINT requests_pkey PRIMARY KEY (request_id),
    CONSTRAINT requests_price_id_key UNIQUE (price_id)
);

DROP TABLE IF EXISTS public.prices;
CREATE TABLE IF NOT EXISTS public.prices
(
    close numeric(38, 2) NOT NULL,
    high numeric(38, 2) NOT NULL,
    low numeric(38, 2) NOT NULL,
    open numeric(38, 2) NOT NULL,
    price_id uuid DEFAULT gen_random_uuid()  NOT NULL ,
    CONSTRAINT prices_pkey PRIMARY KEY (price_id)
);

DROP TABLE IF EXISTS public.tickers;
CREATE TABLE IF NOT EXISTS public.tickers
(
    ticker_id uuid DEFAULT gen_random_uuid()  NOT NULL ,
    ticker character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tickers_pkey PRIMARY KEY (ticker_id),
    CONSTRAINT tickers_ticker_key UNIQUE (ticker)
);

DROP TABLE IF EXISTS public.users;
CREATE TABLE IF NOT EXISTS public.users
(
    user_id uuid DEFAULT gen_random_uuid()  NOT NULL ,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_username_key UNIQUE (username)
);

ALTER TABLE IF EXISTS public.requests
    ADD CONSTRAINT fkr7wsfxuj6j7gaolh8buf9yy1l FOREIGN KEY (price_id)
    REFERENCES public.prices (price_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS requests_price_id_key
    ON public.requests(price_id);


ALTER TABLE IF EXISTS public.requests
    ADD CONSTRAINT fkst62axbpq3h7gjynxuncxwx75 FOREIGN KEY (ticker_id)
    REFERENCES public.tickers (ticker_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.user_requests
    ADD CONSTRAINT fkc5wdg4rgq9s2v8g3x7iro1rkh FOREIGN KEY (request_id)
    REFERENCES public.requests (request_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.user_requests
    ADD CONSTRAINT fkrykq90x6m2rwtong6on5sgs5x FOREIGN KEY (user_id)
    REFERENCES public.users (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


