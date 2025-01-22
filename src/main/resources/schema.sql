CREATE DATABASE ecommerce_db;

select * from category_tb
where name = 'iphone';

select category_id, product_id, product_name from products_tb
where category_id = ('4235f187-c216-4810-a98b-68b8f5d4243a');

select * from products_tb
where category_id = ('4235f187-c216-4810-a98b-68b8f5d4243a');