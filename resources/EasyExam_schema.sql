DROP TABLE IF EXISTS Pruefung;
DROP TABLE IF EXISTS Fragenloesung;
DROP TABLE IF EXISTS Frage;
DROP TABLE IF EXISTS Fragekatalog;
DROP TABLE IF EXISTS Themengebiet;
DROP TABLE IF EXISTS Modul;
DROP TABLE IF EXISTS Pruefer;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Musterloesung;

CREATE TABLE Student
(Matrikelnr INTEGER PRIMARY KEY,
Nachname VARCHAR(45) NOT NULL,
Vorname VARCHAR(45) NOT NULL,
Semester INTEGER,
Studiengang VARCHAR(45));

CREATE TABLE Pruefer
(PersNr INTEGER PRIMARY KEY,
Nachname VARCHAR(45) NOT NULL,
Vorname VARCHAR(45) NOT NULL);

CREATE TABLE Frage
(idFrage INTEGER IDENTITY(1,1) PRIMARY KEY,
Fragestellung VARCHAR(255) NOT NULL,
Musterloesung VARCHAR(255),
Niveau INTEGER NOT NULL,
Punkte FLOAT NOT NULL,
gestellt TINYINT,
Themengebiet VARCHAR(255),
Fragekatalog VARCHAR(255),
Modul VARCHAR(255));


CREATE TABLE Fragenloesung
(idFragenloesung INTEGER PRIMARY KEY,
Niveau INTEGER NOT NULL,
Frage_fk INTEGER,
CONSTRAINT fk_Frage FOREIGN KEY (Frage_fk) REFERENCES Frage (idFrage) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE Pruefung
(idPruefung INTEGER PRIMARY KEY,
Bezeichnung VARCHAR(45) NOT NULL,
Note FLOAT,
Fragekatalog_fk INTEGER,
Matrikelnr INTEGER,
PersNr INTEGER,
CONSTRAINT fk_PersNr FOREIGN KEY (PersNr) REFERENCES Pruefer (PersNr) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_Matrikelnr FOREIGN KEY (Matrikelnr) REFERENCES Student (Matrikelnr) ON DELETE CASCADE ON UPDATE CASCADE);


