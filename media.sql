CREATE TABLE persons (
    id serial PRIMARY KEY,
    name character varying,
    email character varying
);

CREATE TABLE communities (
    id serial PRIMARY KEY,
    name character varying,
    description character varying
);

CREATE TABLE communities_persons (
    id serial PRIMARY KEY,
    community_id integer REFERENCES communities(id),
    person_id integer REFERENCES persons(id)
);

CREATE TABLE monsters (
    id serial PRIMARY KEY,
    name character varying,
    personid integer REFERENCES persons(id),
    birthday timestamp without time zone,
    lastate timestamp without time zone,
    lastslept timestamp without time zone,
    lastplay timestamp without time zone,
    type character varying,
    lastwater timestamp without time zone,
    lastkindling timestamp without time zone
);


