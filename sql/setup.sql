drop database if exists ordermanagement;
create database if not exists ordermanagement;
use ordermanagement;

DROP TABLE IF EXISTS `Customer`;
create table if not exists `Customer` 
(
	`id`		int primary key auto_increment,
    `name`		varchar(255) not null,
    `address`	varchar(255) not null,
    `email`		varchar(255) not null,
    `age`		int not null
);

DROP TABLE IF EXISTS `Product`;
create table if not exists `Product` 
(
	`id`	int primary key auto_increment,
    `name`	varchar(255) not null,
    `price`	double not null,
    `quantity` int not null
);

DROP TABLE IF EXISTS `Order`;
create table if not exists `Order` 
(
	`id`	int primary key auto_increment,
    `customerId`	int not null,
    `date`	varchar(255) not null
);

DROP TABLE IF EXISTS `OrderItem`;
create table if not exists `OrderItem` 
(
	`id`			int primary key auto_increment,
    `orderId`		int not null,
    `productId`		int not null,
    `quantity` 		int not null
);

delimiter //
create procedure add_fk(fromTbl varchar(255), fromCol varchar(255), toTbl varchar(255), toCol varchar(255))
begin
	set @q = concat("alter table `", fromTbl, "` add constraint `fk-", fromTbl, "-", toTbl, "-", toCol, "` foreign key (", fromCol,") references `", toTbl, "`(", toCol, ") on delete cascade");
    prepare stmt from @q;
    execute stmt;
    deallocate prepare stmt;
end//

call add_fk("OrderItem", "orderId", "Order", "id");
call add_fk("Order", "customerId", "Customer", "id");
call add_fk("OrderItem", "productId", "Product", "id");

insert into `Customer` values
(0, "Alex Kovacs", "Str. Dobra Petru", "alexkovacs@rocketmail.com", 20),
(0, "Marco Pop", "Cluj-Napoca", "marco@putere.com", 21),
(0, "Andrei Mariciuc", "Moldova", "andy@utcluj.ro", 20),
(0, "Marcel Dobrean", "Str. Libertatii, Nr. 31, Ap. 7", "marcel09@yahoo.com", 34);

insert into `Product` values
(0, "Banana", 19.99, 1347),
(0, "Portocala", 7.98, 524),
(0, "Lemn", 199.99, 9999),
(0, "Castana", 0.99, 5353),
(0, "Bicicleta", 259.99, 3);

insert into `Order` values
(0, 1, '04/17/2021 02:05 am'),
(0, 3, '04/01/2021 05:22 pm'),
(0, 4, '04/18/2021 01:31 am');

insert into `OrderItem` values
(0, 1, 5, 1),
(0, 1, 1, 1),
(0, 3, 1, 15),
(0, 3, 2, 8),
(0, 3, 4, 37),
(0, 3, 3, 2);

drop trigger if exists order_update_trigger;
delimiter //
create trigger order_update_trigger 
before insert on `OrderItem`
for each row
begin    
    update `Product` p set
    p.`quantity` = p.`quantity` - new.`quantity`
    where p.`id` = new.`productId`;
end//
delimiter ;

drop view if exists vw_orderViewModel;
delimiter //
create view vw_orderViewModel as
	select o.*, c.`name` as "customerName", convert(ifnull(sum(p.`price` * oi.`quantity`), '-'), char) as "price" from `Order` o
    left join `Customer` c on o.`customerId` = c.`id`
    left join `OrderItem` oi on o.`id` = oi.`orderId`
    left join `Product` p on oi.`productId` = p.`id`
    group by o.`id`;
//

select * from vw_orderViewModel;

drop view if exists vw_orderItemViewModel;
delimiter //
create view vw_orderItemViewModel as
	select oi.*, p.`name` as "productName", p.`price` as "productPrice", oi.`quantity` * p.`price` as "totalPrice" from `OrderItem` oi
    inner join `Product` p on oi.`productId` = p.`id`;
//

select * from vw_orderItemViewModel where `orderId` = 1;

drop view if exists vw_productSelectable;
delimiter //
create view vw_productSelectable as
	select p.* 
    from `Product` p
    where p.`quantity` > 0;
//

select * from vw_productSelectable;