select user(), database();
select deptno,deptname,floor from department;
desc employee;

select empno,empname,title,manager,salary,dno from employee where dno = 1;
select e.empno,e.empname,e.title,
ifnull((select concat(empname,'(',e.manager,')') from employee where empno = e.manager),'직속상사없음') as 'manager_name',
salary,
(select concat(deptname,'(',deptno,')') from department where department.deptno = e.dno) as 'deptname' from employee e;
select * from department;
insert into department values(5,'마케팅',30);
update department set deptname = '마케팅2', floor = 30 where deptno = 5;
delete from department where deptno = 5;
select * from department;
from employee e where e.dno = 3;