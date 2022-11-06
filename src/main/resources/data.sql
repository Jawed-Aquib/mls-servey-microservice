insert into survey_question
(id,is_active,question)
values(10001,true,'Question 1');
insert into survey_question
(id,is_active,question)
values(10002,false,'Question 2');

insert into survey_option
(id,value, survey_question_id)
values(10001,'val 1', 10001);

insert into survey_option
(id,value, survey_question_id)
values(10002,'val 2', 10001);

insert into survey_option
(id,value, survey_question_id)
values(10003,'val 3', 10002);

insert into survey_option
(id,value, survey_question_id)
values(10004,'val 4', 10002);