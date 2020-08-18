SET @N = 1;
SELECT CONCAT('ZIP', @N := @N +1) AS id, zip, lat, lng, doctype from  (select distinct zip, gis_latitude AS lat, gis_longitude AS lng, "zipcode" AS doctype FROM location where gis_latitude is not null) AS tmp
