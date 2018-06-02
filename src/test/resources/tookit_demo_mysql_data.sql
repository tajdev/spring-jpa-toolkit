insert into course(id, name, created_date, modified_date, is_deleted) values (10001,'JPA in 50 Steps', sysdate(), sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10002,'JPA in 75 Steps',sysdate(),sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10003,'JPA in 100 Steps',sysdate(),sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10004,'JPA in 150 Steps', sysdate(), sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10005,'JPA in 175 Steps',sysdate(),sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10006,'JPA in 200 Steps',sysdate(),sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10007,'JPA in 250 Steps', sysdate(), sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10008,'JPA in 275 Steps',sysdate(),sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10009,'JPA in 300 Steps',sysdate(),sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10010,'JPA in 350 Steps', sysdate(), sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted) values (10011,'JPA in 375 Steps',sysdate(),sysdate(),false);
insert into course(id, name, created_date, modified_date, is_deleted)  values (10012,'JPA in 400 Steps',sysdate(),sysdate(),false);

insert into passport(id, number) values (40001,'E123456');
insert into passport(id, number) values (40002,'J73456');
insert into passport(id, number) values (40003,'k23450');

insert into student(id, name, passport_id) values (20001,'Todd',40001);
insert into student(id, name, passport_id) values (20002,'Robert',40002);
insert into student(id, name, passport_id) values (20003,'Mike',40003);

insert into review(id,rating,description,course_id) values (50001,'FIVE','Great Course!',10001);
insert into review(id,rating,description,course_id) values (50002,'FIVE','Great Course!',10001);
insert into review(id,rating,description,course_id) values (50003,'FIVE','Great Course!',10002);
insert into review(id,rating,description,course_id) values (50004,'FIVE','Great Course!',10003);


insert into student_course(student_id,course_id) values (20001,10001);
insert into student_course(student_id,course_id) values (20002,10001);
insert into student_course(student_id,course_id) values (20003,10001);
insert into student_course(student_id,course_id) values (20001,10003);