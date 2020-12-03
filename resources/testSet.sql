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

Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Was ist 1+1?', 'Die Lösung ist 2.', 1, 1.5, 0, 'Business Process Model and Notation','QM SS2019', 'QM', 'Level 1', 'Level 2', 'Level 3');
Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Für was steht QM?', 'QM steht für Qualitäsmanagement.', 2, 3.0, 0, 'Business Process Model and Notation','GPM WS 2020', 'GPM', 'Level 1', 'Level 2', 'Level 3');
Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Wie groß ist eine Europalette?', 'Eine Europalette ist 80x120cm groß.', 3, 2.5, 0, 'Beschaffungsmanagement', 'Logistik WS2016', 'Logistik', 'Level 1', 'Level 2', 'Level 3');
Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Wofür steht OOP?', 'OOP steht für objektorientierte Programmierung.', 1, 1.5, 1, 'Beschaffungsmanagement', 'Logistik WS2016', 'Logistik', 'Level 1', 'Level 2', 'Level 3');

Insert Into Pruefung(idPruefung,Bezeichnung,Note,Fragekatalog_fk,Matrikelnr,PersNr) Values (1423, 'Qualitaetsmanagement SS2020', 1.0, 943829, 121212, 123454);
Insert Into Pruefung(idPruefung,Bezeichnung,Note,Fragekatalog_fk,Matrikelnr,PersNr) Values (3425, 'Beschaffung und Logistik WS20/21', 1.7, 239857, 161616, 123453);



Insert Into Fragenloesung (idFragenloesung, Niveau, Frage_fk) Values (333739851, 42 , 11184729);
Insert Into Fragenloesung (idFragenloesung, Niveau, Frage_fk) Values (333739852, 42, 11184730);
Insert Into Fragenloesung (idFragenloesung, Niveau, Frage_fk) Values (333739853, 42, 11184731);

SELECT * FROM Pruefer;

