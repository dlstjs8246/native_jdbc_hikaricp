desc department;
desc employee;

insert into department values 
(1, '영업', 8),
(2, '기획', 10),
(3, '개발', 9),
(4, '총무', 7);

insert into employee values
(4377,'이성래','사장',null,5000000,2,null),
(3426,'박영권','과장',4377,3000000,1,null),
(1003,'조민희','과장',4377,3000000,2,null),
(3011,'이수민','부장',4377,4000000,3,null),
(1365,'김상원','사원',3426,1500000,1,null),
(2106,'김창섭','대리',1003,2500000,2,null),
(3427,'최종철','사원',3011,1500000,3,null);

select * from employee;

drop procedure if exists procedure_01;

delimiter !!
create procedure procedure_01(in in_dno int)
begin
	select * from employee where dno = in_dno;
end !!
delimiter ;
call procedure_01(3);