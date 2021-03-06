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
Vorname VARCHAR(45) NOT NULL,
eMail VARCHAR(255) NOT NULL,
Passwort VARCHAR(45) NOT NULL);

CREATE TABLE Frage
(idFrage INTEGER PRIMARY KEY auto_increment,
Fragestellung text NOT NULL,
Musterloesung text,
Niveau INTEGER NOT NULL,
Punkte FLOAT NOT NULL,
gestellt TINYINT,
Themengebiet VARCHAR(255),
Fragekatalog VARCHAR(255), 
INDEX(Fragekatalog),
Modul VARCHAR(255),
grundLageNiveau VARCHAR(255),
gut VARCHAR(255),
sehrGut VARCHAR(255),
Notizien text(500) null,
Punkte_erreicht FLOAT null);

CREATE TABLE Pruefung
(idPruefung INTEGER PRIMARY KEY auto_increment,
Bezeichnung VARCHAR(45) NOT NULL,
Note FLOAT,
Punkte_gesamt FLOAT null,
Matrikelnr INTEGER,
PersNr INTEGER,
CONSTRAINT fk_Bezeichnung FOREIGN Key (Bezeichnung) REFERENCES Frage (Fragekatalog),
CONSTRAINT fk_PersNr FOREIGN KEY (PersNr) REFERENCES Pruefer (PersNr),
CONSTRAINT fk_Matrikelnr FOREIGN KEY (Matrikelnr) REFERENCES Student (Matrikelnr));

CREATE TABLE Fragenloesung
(idFragenloesung INTEGER PRIMARY KEY, 
Niveau INTEGER NOT NULL,
Frage_fk INTEGER,
CONSTRAINT fk_Frage FOREIGN KEY (Frage_fk) REFERENCES Frage (idFrage) ON DELETE CASCADE ON UPDATE CASCADE);

