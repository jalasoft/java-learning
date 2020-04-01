INSERT INTO address(id, street, number, town, pobox, country) VALUES
('00000000-0000-0000-0000-00000000001', 'Antonina Dvoraka', 1111, 'Kolin', '28002', 'Czech Republic'),
('00000000-0000-0000-0000-00000000002', 'Delnicka', 804, 'Kolin', '28002', 'Czech Republic'),
('00000000-0000-0000-0000-00000000003', 'Polni', 384, 'Tri Dvory', '28000', 'Czech Republic');

INSERT INTO person (id, name, surname, email, address) VALUES
('00000000-0000-0000-0000-00000000007', 'Honza', 'Lastovicka', 'lasja01@ca.com', '00000000-0000-0000-0000-00000000001'),
('00000000-0000-0000-0000-00000000008', 'Jiri', 'Sereda', 'serji02@cngroup.dk', '00000000-0000-0000-0000-00000000003'),
('00000000-0000-0000-0000-00000000009', 'Lukas', 'Svec', 'luky@avast.com', '00000000-0000-0000-0000-00000000003');


INSERT INTO credentials(id, username, password, person_id) VALUES
('00000000-0000-0000-0000-00000000004', 'lasja01', 'hehe33', '00000000-0000-0000-0000-00000000007'),
('00000000-0000-0000-0000-0000000000a', 'lastovicka', 'hihi66', '00000000-0000-0000-0000-00000000007'),
('00000000-0000-0000-0000-00000000005', 'serji02', 'cngroup', '00000000-0000-0000-0000-00000000008'),
('00000000-0000-0000-0000-00000000006', 'lukas.svec', 'sracka', '00000000-0000-0000-0000-00000000009');



