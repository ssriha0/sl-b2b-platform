CREATE TABLE `lu_minimum_rating` (      
                     `rating` decimal(10,2) NOT NULL,      
                     `descr` varchar(50) default NULL,     
                     `type` varchar(50) default NULL,      
                     PRIMARY KEY  (`rating`)               
                   ) ENGINE=InnoDB DEFAULT CHARSET=latin1  


CREATE TABLE `lu_resource_distance` (   
                        `distance` int(10) NOT NULL,          
                        `descr` varchar(50) default NULL,     
                        PRIMARY KEY  (`distance`)             
                      ) ENGINE=InnoDB DEFAULT CHARSET=latin1  
                      
                      
CREATE TABLE `lu_resource_top_select` (  
                          `id` int(10) NOT NULL,                 
                          `descr` varchar(50) default NULL,      
                          PRIMARY KEY  (`id`)                    
                        ) ENGINE=InnoDB DEFAULT CHARSET=latin1   


insert into lu_resource_top_select (id, descr) values (05, '5');
insert into lu_resource_top_select (id, descr) values (10, '10');
insert into lu_resource_top_select (id, descr) values (15, '15');
insert into lu_resource_top_select (id, descr) values (20, '20');
insert into lu_resource_top_select (id, descr) values (25, '25');
insert into lu_resource_top_select (id, descr) values (30, '30');                      


insert into lu_resource_distance (distance, descr) values (05, 'Less than 5 miles');
insert into lu_resource_distance (distance, descr) values (10, 'Less than 10 miles');
insert into lu_resource_distance (distance, descr) values (15, 'Less than 15 miles');
insert into lu_resource_distance (distance, descr) values (20, 'Less than 20 miles');
insert into lu_resource_distance (distance, descr) values (25, 'Less than 25 miles');
insert into lu_resource_distance (distance, descr) values (30, 'Less than 30 miles');
insert into lu_resource_distance (distance, descr) values (35, 'Less than 35 miles');
insert into lu_resource_distance (distance, descr) values (40, 'Less than 40 miles');
insert into lu_resource_distance (distance, descr) values (50, 'Less than 50 miles');
insert into lu_resource_distance (distance, descr) values (75, 'Less than 75 miles');
insert into lu_resource_distance (distance, descr) values (100, 'Less than 100 miles');
insert into lu_resource_distance (distance, descr) values (125, 'Less than 125 miles');
insert into lu_resource_distance (distance, descr) values (150, 'Less than 150 miles');
insert into lu_resource_distance (distance, descr) values (175, 'Less than 175 miles');
insert into lu_resource_distance (distance, descr) values (200, 'Less than 200 miles');



insert into lu_minimum_rating (rating, descr, type) values (1.0, '1 Star', null);
insert into lu_minimum_rating (rating, descr, type) values (1.5, '1.5 Star', null);
insert into lu_minimum_rating (rating, descr, type) values (2.0, '2 Star', null);
insert into lu_minimum_rating (rating, descr, type) values (2.5, '2.5 Star', null);
insert into lu_minimum_rating (rating, descr, type) values (3.0, '3 Star', null);
insert into lu_minimum_rating (rating, descr, type) values (3.5, '3.5 Star', null);
insert into lu_minimum_rating (rating, descr, type) values (4.0, '4 Star', null);
insert into lu_minimum_rating (rating, descr, type) values (4.5, '4.5 Star', null);