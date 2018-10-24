DROP TABLE IF EXISTS quest;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS tag_history;

CREATE TABLE quest (
    id_quest serial NOT NULL,
    q_name text NOT NULL,
    price numeric(16,2),
    duration integer,
    date_of_creation date,
    date_of_modification date,
    max_number_of_people smallint,
    genre text,
    description text
);

CREATE TABLE tag (
    id_tag serial NOT NULL,
    t_name text NOT NULL
);

CREATE TABLE tag_history (
    id_quest bigint NOT NULL,
    id_tag bigint NOT NULL
);

ALTER TABLE ONLY quest
    ADD CONSTRAINT "Quest_pkey" PRIMARY KEY (id_quest);


ALTER TABLE ONLY tag_history
    ADD CONSTRAINT id_quest_tag_pk PRIMARY KEY (id_quest, id_tag);


ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_name_unique UNIQUE (t_name);


ALTER TABLE ONLY tag
    ADD CONSTRAINT tags_pkey PRIMARY KEY (id_tag);


ALTER TABLE ONLY quest
    ADD CONSTRAINT unique_name UNIQUE (q_name);


ALTER TABLE ONLY tag_history
    ADD CONSTRAINT id_quest_fk FOREIGN KEY (id_quest) REFERENCES public.quest(id_quest) ON DELETE CASCADE;


ALTER TABLE ONLY tag_history
    ADD CONSTRAINT id_tag_fk FOREIGN KEY (id_tag) REFERENCES public.tag(id_tag) ON DELETE CASCADE;





