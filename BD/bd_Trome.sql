drop database if exists  DB_Bingo;

create database DB_Bingo;

use DB_Bingo;

create table tb_Usuario(
	id_usu int auto_increment primary key ,
    nombre varchar(255)
);


create table tb_Cab_Bingo(
	id_bingo char(6) primary key ,
    id_usu int ,
    estado bit, 
	foreign key (id_usu) references tb_Usuario(id_usu)
);


create table tb_Detalle_Bingo(
	id int auto_increment primary key,
	id_bingo char(6),
	B char(3) ,
    I char(3) ,
    N char(3) ,
    G char(3) ,
    O char(3) ,
    foreign key (id_bingo) references tb_Cab_Bingo(id_bingo)
);
create table tb_dias_Semana(
id int auto_increment primary key,
dia_semana VARCHAR(255)
);
insert into tb_dias_Semana values (null , 'Lunes');
insert into tb_dias_Semana values (null , 'Martes');
insert into tb_dias_Semana values (null , 'Miercoles');
insert into tb_dias_Semana values (null , 'Jueves');
insert into tb_dias_Semana values (null , 'Viernes');
insert into tb_dias_Semana values (null , 'Sabado');
insert into tb_dias_Semana values (null , 'Domingo');

create table numerosobtenidos(
id int auto_increment primary key ,
numero int unique 
);

ALTER TABLE numerosobtenidos
MODIFY  COLUMN dia_semana int , ADD foreign key  DiaSemanaRelacion (dia_semana) references tb_dias_Semana(id) ;



select * from tb_Usuario ;
select * from tb_Cab_Bingo;
select * from tb_Detalle_Bingo;
select * from numerosobtenidos;
insert into tb_Usuario values (1 , 'admin');
insert into tb_Detalle_Bingo values (null , '672518' , '1','1','1','1','1');