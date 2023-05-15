alter table dictionary_discipline_type
    add cipher varchar(10) not null;

alter table discipline
drop column cipher;