Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123456,'Speiser','Sebastian');
Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123455,'Höss','Oliver');
Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123457,'Kramer','Ralf');
Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123454,'Lueckemeyer','Gero');
Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123453,'Dehdari','Payam');

Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (121212, 'Hartwig', 'Lorenz', 6, 'WI');
Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (131313, 'Jakobi', 'Jana', 5, 'WI');
Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (141414, 'Alnaser', 'Bachir', 5, 'WI');
Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (151515, 'Kallenberger', 'Ruth', 5, 'WI');
Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (161616, 'Shkurti', 'Gjergji', 6, 'WI');
Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (171717, 'Durmus', 'Esma', 6, 'WI');

Insert Into Modul(Modulnr, Bezeichnung) Values (222, 'Qualitaetsmanagement');
Insert Into Modul(Modulnr, Bezeichnung) Values (333, 'Logistik');
Insert Into Modul(Modulnr, Bezeichnung) Values (444, 'WI Projekt2');

Insert Into Themengebiet(idThemengebiet, Bezeichnung, Modul_fk) Values (85746, 'Business Process Model and Notation', 222);
Insert Into Themengebiet(idThemengebiet, Bezeichnung, Modul_fk) Values (38762, 'Beschaffungsmanagement', 333);

Insert Into Fragekatalog(idFragekatalog, Modul_fk) Values (943829, 222);
Insert Into Fragekatalog(idFragekatalog, Modul_fk) Values (753824, 222);
Insert Into Fragekatalog(idFragekatalog, Modul_fk) Values (239857, 333);

Insert Into Frage (idFrage, Fragestellung, Niveau, Punkte, gestellt, Themengebiet_fk, Fragekatalog_fk) Values (11184729, 'Was ist 1+1?', 1, 1.5, 0, 85746, 943829);
Insert Into Frage (idFrage, Fragestellung, Niveau, Punkte, gestellt, Themengebiet_fk, Fragekatalog_fk) Values (11184730, 'Für was steht QM?', 2, 3.0, 0, 85746, 943829);
Insert Into Frage (idFrage, Fragestellung, Niveau, Punkte, gestellt, Themengebiet_fk, Fragekatalog_fk) Values (11184731, 'Wie groß ist eine Europalette?', 3, 2.5, 0, 38762, 239857);

Insert Into Musterloesung (idMusterloesung, Loesung, Niveau) Values (222285894, 'Die Lösung ist 2.', 1);
Insert Into Musterloesung (idMusterloesung, Loesung, Niveau) Values (222285897,'QM steht für Qualitäsmanagement.', 2);
Insert Into Musterloesung (idMusterloesung, Loesung, Niveau) Values (222285827,'Eine Europalette ist 80x120cm groß.', 3);



Insert Into Fragenloesung (idFragenloesung, Musterloesung_fk, Frage_fk) Values (333739851, 222285894, 11184729);
Insert Into Fragenloesung (idFragenloesung, Musterloesung_fk, Frage_fk) Values (333739852, 222285897, 11184730);
Insert Into Fragenloesung (idFragenloesung, Musterloesung_fk, Frage_fk) Values (333739853, 222285827, 11184731);



Insert Into Pruefung(idPruefung,Bezeichnung,Note,Fragekatalog_fk,Matrikelnr,PersNr) Values (1423, 'Qualitaetsmanagement SS2020', 1.0, 943829, 121212, 123454);
Insert Into Pruefung(idPruefung,Bezeichnung,Note,Fragekatalog_fk,Matrikelnr,PersNr) Values (3425, 'Beschaffung und Logistik WS20/21', 1.7, 239857, 161616, 123453);

