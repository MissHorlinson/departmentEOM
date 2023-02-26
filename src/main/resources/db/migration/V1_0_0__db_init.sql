create table dictionary_academic_rank
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_base
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_cipher
(
    id              int auto_increment
        primary key,
    name            varchar(50) not null,
    speciality_code int         null
);

create table dictionary_degree
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_department
(
    id           int auto_increment
        primary key,
    name         varchar(50)  not null,
    abbreviation varchar(100) null
);

create table dictionary_discipline_form
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_discipline_type
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_individual_task
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_position
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_qualification
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_reporting_form
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_step
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_studying_form
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table dictionary_studying_term
(
    id            int auto_increment
        primary key,
    name          varchar(50) not null,
    term_in_month int         not null,
    semester_num  int         null
);

create table dictionary_studying_type
(
    id          int auto_increment
        primary key,
    name        varchar(50)           not null,
    letter_mean varchar(5) default '' not null
);

create table dictionary_subject_name
(
    id   int auto_increment
        primary key,
    name varchar(150) not null
);

create table plan_info
(
    id               int auto_increment
        primary key,
    cipher_id        int                                       not null,
    qualification_id int                                       not null,
    base_id          int                                       not null,
    step_id          int                                       not null,
    studying_form_id int                                       not null,
    studying_term_id int                                       not null,
    admission_year   date                                      not null,
    group_num        int                                       null,
    student_num      int                                       null,
    rector           varchar(50)                               null,
    semester_num     int          default 0                    not null,
    created_time     timestamp(3) default CURRENT_TIMESTAMP(3) null,
    constraint plane_base_fk
        foreign key (base_id) references dictionary_base (id),
    constraint plane_cipher_fk
        foreign key (cipher_id) references dictionary_cipher (id),
    constraint plane_form_fk
        foreign key (studying_form_id) references dictionary_studying_form (id),
    constraint plane_qualification_fk
        foreign key (qualification_id) references dictionary_qualification (id),
    constraint plane_step_fk
        foreign key (step_id) references dictionary_step (id),
    constraint term_plane_fk
        foreign key (studying_term_id) references dictionary_studying_term (id)
);

create table discipline
(
    id                 int auto_increment
        primary key,
    name_id            int                                       not null,
    type_id            int                                       not null,
    department_id      int                                       not null,
    semester           int                                       not null,
    cipher             varchar(10)                               not null,
    discipline_num     int          default 0                    not null,
    plan_id            int                                       not null,
    discipline_sub_num int          default 0                    null,
    created_time       timestamp(3) default CURRENT_TIMESTAMP(3) null,
    constraint discipline_department_fk
        foreign key (department_id) references dictionary_department (id),
    constraint discipline_plan_fk
        foreign key (plan_id) references plan_info (id),
    constraint discipline_subject_fk
        foreign key (name_id) references dictionary_subject_name (id),
    constraint discipline_type_fk
        foreign key (type_id) references dictionary_discipline_type (id)
);

create table auditory_hours
(
    id            int auto_increment
        primary key,
    discipline_id int                                       not null,
    type_id       int                                       not null,
    hours_num     double                                    null,
    created_time  timestamp(3) default CURRENT_TIMESTAMP(3) null,
    constraint auditory_discipline_fk
        foreign key (discipline_id) references discipline (id),
    constraint auditory_form_fk
        foreign key (type_id) references dictionary_discipline_form (id)
);

create table consultation
(
    id            int auto_increment
        primary key,
    discipline_id int    not null,
    personal      double null,
    pre_exam      double null,
    course        double null,
    constraint consultation_discipline_fk
        foreign key (discipline_id) references discipline (id)
);

create table group_stream
(
    id           int auto_increment
        primary key,
    plan_id      int                                       null,
    created_time timestamp(3) default CURRENT_TIMESTAMP(3) null,
    constraint plan_stream_fk
        foreign key (plan_id) references plan_info (id)
);

create table group_info
(
    id             int auto_increment
        primary key,
    admission_year date                                      not null,
    group_index    int                                       not null,
    cipher_id      int                                       not null,
    curator        int                                       null,
    stream_id      int                                       null,
    created_time   timestamp(3) default CURRENT_TIMESTAMP(3) null,
    constraint group_info_dictionary_cipher_id_fk
        foreign key (cipher_id) references dictionary_cipher (id),
    constraint group_info_stream_id_fk
        foreign key (stream_id) references group_stream (id)
);

create table independent_hours
(
    id            int auto_increment
        primary key,
    discipline_id int                                       not null,
    hours_num     double                                    null,
    created_time  timestamp(3) default CURRENT_TIMESTAMP(3) null,
    constraint independent_hours_discipline_fk
        foreign key (discipline_id) references discipline (id)
);

create table individual_tasks
(
    id            int auto_increment
        primary key,
    discipline_id int not null,
    form_id       int not null,
    constraint individual_task_discipline_fk
        foreign key (discipline_id) references discipline (id),
    constraint individual_task_form_fk
        foreign key (form_id) references dictionary_individual_task (id)
);

create table reporting
(
    id            int auto_increment
        primary key,
    discipline_id int                                       not null,
    form_id       int                                       not null,
    created_time  timestamp(3) default CURRENT_TIMESTAMP(3) null,
    constraint reporting_discipline_fk
        foreign key (discipline_id) references discipline (id),
    constraint reporting_form_fk
        foreign key (form_id) references dictionary_reporting_form (id)
);

create table student
(
    id          int auto_increment
        primary key,
    group_id    int         not null,
    record_book varchar(20) not null,
    first_name  varchar(50) not null,
    second_name varchar(50) not null,
    last_name   varchar(50) not null,
    email       varchar(40) null,
    passport    varchar(10) null,
    phone       varchar(15) null,
    birthday    datetime    null,
    constraint student_group_fk
        foreign key (group_id) references group_info (id)
);

create table teacher
(
    id            int auto_increment
        primary key,
    first_name    varchar(50) not null,
    second_name   varchar(50) not null,
    last_name     varchar(50) not null,
    birthday      datetime    null,
    email         varchar(40) null,
    passport      varchar(10) null,
    phone         varchar(15) null,
    department_id int         not null,
    constraint teacher_cathedra_fk
        foreign key (department_id) references dictionary_department (id)
);

create table discipline_student_teacher
(
    id            int auto_increment
        primary key,
    teacher_id    int        not null,
    student_id    int        not null,
    discipline_id int        not null,
    isNeeded      tinyint(1) null,
    constraint full_discipline_fk
        foreign key (discipline_id) references discipline (id),
    constraint full_student_fk
        foreign key (student_id) references student (id),
    constraint full_teacher_fk
        foreign key (teacher_id) references teacher (id)
);

create table users
(
    id       int auto_increment
        primary key,
    username varchar(50)                  not null,
    password varchar(255)                 not null,
    role     varchar(20) default 'USER'   not null,
    status   varchar(20) default 'ACTIVE' null,
    constraint users_username_uindex
        unique (username)
);

create table week_plan
(
    id         int auto_increment
        primary key,
    plan_id    int not null,
    semester   int not null,
    type_id    int not null,
    start_week int not null,
    term       int not null,
    constraint week_plan_fk
        foreign key (plan_id) references plan_info (id),
    constraint week_plan_type_fk
        foreign key (type_id) references dictionary_studying_type (id)
);

create table work
(
    id             int auto_increment
        primary key,
    rank_id        int    not null,
    position_id    int    not null,
    degree_id      int    not null,
    salary_present double not null,
    constraint work_degree_fk
        foreign key (degree_id) references dictionary_degree (id),
    constraint work_position_fk
        foreign key (position_id) references dictionary_position (id),
    constraint work_rank_fk
        foreign key (rank_id) references dictionary_academic_rank (id)
);

create table teacher_work
(
    id         int auto_increment
        primary key,
    teacher_id int not null,
    work_id    int not null,
    constraint teacher_fk
        foreign key (teacher_id) references teacher (id),
    constraint work_fk
        foreign key (work_id) references work (id)
);

