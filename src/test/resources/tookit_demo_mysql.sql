    
    drop table if exists course;
    drop table if exists passport;
    drop table if exists review;
    drop table if exists student;
    drop table if exists student_course;
    
    create table course (
        id bigint not null AUTO_INCREMENT,
        created_date datetime DEFAULT CURRENT_TIMESTAMP,
        is_deleted boolean not null,
        modified_date datetime DEFAULT CURRENT_TIMESTAMP,
        name varchar(255) not null,
        primary key (id)
    );
    
    create table passport (
       id bigint not null AUTO_INCREMENT,
        number varchar(255) not null,
        primary key (id)
    );
    
    create table review (
       id bigint not null AUTO_INCREMENT,
        description varchar(255),
        rating varchar(255),
        course_id bigint,
        primary key (id)
    );
    
    create table student (
       id bigint not null AUTO_INCREMENT,
        city varchar(255),
        line1 varchar(255),
        line2 varchar(255),
        name varchar(255) not null,
        passport_id bigint,
        primary key (id)
    );
    
    create table student_course(
       student_id bigint not null,
       course_id bigint not null
	);
    
    
    drop table if exists full_time_employee;
	drop table if exists part_time_employee;
    
    create table full_time_employee (
       id bigint not null AUTO_INCREMENT,
        name varchar(255) not null,
        salary decimal(19,2),
        primary key (id)
    );
    

    
    create table part_time_employee (
       id bigint not null AUTO_INCREMENT,
        name varchar(255) not null,
        hourly_wage decimal(19,2),
        primary key (id)
    );
