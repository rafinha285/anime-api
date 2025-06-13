CREATE TABLE IF NOT EXISTS anime.anime(
    id UUID DEFAULT gen_random_uuid()
        PRIMARY KEY,
    averageeptime DOUBLE PRECISION,
    date_added TIMESTAMPTZ DEFAULT now(),
    description TEXT NOT NULL ,
    genre TEXT[],
    language VARCHAR(255),
    name VARCHAR(255),
    name2 VARCHAR(255),
    quality VARCHAR(255),
    rating DOUBLE PRECISION,
    visible BOOLEAN,
    weekday VARCHAR(30),
    releasedate DATE
);

CREATE TABLE IF NOT EXISTS anime.seasons(
    id UUID DEFAULT gen_random_uuid()
        PRIMARY KEY ,
    name VARCHAR(255) NOT NULL ,
    anime_id UUID NOT NULL
        REFERENCES anime.anime,
    index INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS anime.state(
    id SMALLSERIAL PRIMARY KEY ,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS anime.characters(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY ,
    description TEXT,
    name VARCHAR(255),
    role VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS anime.creators(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    description TEXT,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS anime.producers(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    description TEXT,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS anime.studios(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    description TEXT,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS anime.anime_producers(
    anime_id UUID NOT NULL
        REFERENCES anime.anime,
    producer_id UUID NOT NULL
        REFERENCES anime.producers,
    PRIMARY KEY (anime_id,producer_id)
);

CREATE TABLE IF NOT EXISTS anime.anime_creators(
    anime_id UUID NOT NULL REFERENCES anime.anime,
    creator_id UUID NOT NULL REFERENCES anime.creators,
    PRIMARY KEY (anime_id,creator_id)
);

CREATE TABLE IF NOT EXISTS anime.anime_studios(
    anime_id UUID NOT NULL REFERENCES anime.anime,
    studio_id UUID NOT NULL REFERENCES anime.studios,
    PRIMARY KEY (anime_id, studio_id)
);

CREATE TABLE IF NOT EXISTS anime.anime_characters(
    anime_id UUID NOT NULL REFERENCES anime.anime,
    character_id UUID NOT NULL REFERENCES anime.characters,
    PRIMARY KEY (anime_id,character_id)
);

CREATE TABLE IF NOT EXISTS anime.anime_state(
    anime_id UUID NOT NULL REFERENCES anime.anime,
    state_id smallint NOT NULL REFERENCES anime.state,
    primary key (anime_id,state_id)
);

CREATE SCHEMA if not exists users;

CREATE TABLE IF NOT EXISTS users.users(
    _id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name varchar(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    surname TEXT NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    birthdate DATE NOT NULL,
    password TEXT NOT NULL,
    salt TEXT NOT NULL,
    superuser BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS users.roles(
    id smallserial primary key ,
    name varchar(30) not null
);

CREATE TABLE IF NOT EXISTS users.user_roles(
    user_id UUID NOT NULL REFERENCES users.users,
    role_id smallint NOT NULL REFERENCES users.roles,
    PRIMARY KEY (user_id, role_id)
);

-- CREATE TYPE users.status_enum AS ENUM(
--     'watching',
--     'completed',
--     'on_hold',
--     'dropped',
--     'plan_to_watch'
-- );
--
-- CREATE TYPE users.priority_value AS ENUM(
--     'LOW',
--     'MEDIUM',
--     'HIGH'
-- );

CREATE TABLE IF NOT EXISTS users.user_anime_list(
    anime_id UUID NOT NULL REFERENCES anime.anime,
    user_id UUID NOT NULL REFERENCES users.users,
    status users.status_enum NOT NULL DEFAULT 'watching'::users.status_enum,
    start_date TIMESTAMPTZ DEFAULT NOW(),
    finish_date TIMESTAMPTZ DEFAULT NULL,
    rate DOUBLE PRECISION DEFAULT NULL,
    priority users.priority_value DEFAULT 'LOW'::users.priority_value,
    PRIMARY KEY (anime_id, user_id)
);

CREATE TABLE IF NOT EXISTS users.user_sessions(
    session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users.users,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    expires_at TIMESTAMPTZ NOT NULL DEFAULT (now() + INTERVAL '7 days'),
    user_agent TEXT NOT NULL,
    web_gl_vendor TEXT NOT NULL,
    web_gl_renderer TEXT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    time_zone TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users.comments(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    parent_id BIGINT REFERENCES users.comments,
    page_id UUID NOT NULL,
    user_id UUID NOT NULL REFERENCES users.users,
    content text NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE IF NOT EXISTS users.comments_likes(
    user_id UUID NOT NULL REFERENCES users.users,
    comment_id BIGINT NOT NULL REFERENCES users.comments,
    PRIMARY KEY (user_id, comment_id)
);