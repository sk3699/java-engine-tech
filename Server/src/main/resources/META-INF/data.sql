INSERT INTO BasicWeightTable (weightValue, chance) VALUES (0.00, 0.40), (1.00, 0.30), (2.00, 0.20), (3.00, 0.10);

INSERT INTO Reels (pos1, pos2, pos3) VALUES ('JACK', 'KING', 'JACK'), ('JACK', 'QUEEN', 'JACK'), ('KING', 'JACK', 'KING'), ('KING', 'JACK', 'KING'), ('ACE', 'JACK', 'ACE');
INSERT INTO Reels (pos1, pos2, pos3) VALUES ('QUEEN', 'ACE', 'QUEEN'), ('QUEEN', 'KING', 'QUEEN'), ('KING', 'QUEEN', 'KING'), ('ACE', 'JACK', 'ACE');
INSERT INTO Reels (pos1, pos2, pos3) VALUES ('JACK', 'JACK', 'JACK'), ('ACE', 'JACK', 'ACE'), ('JACK', 'ACE', 'JACK'), ('QUEEN', 'QUEEN', 'QUEEN');
INSERT INTO Reels (pos1, pos2, pos3) VALUES ('QUEEN', 'QUEEN', 'QUEEN'), ('JACK', 'QUEEN', 'JACK'), ('QUEEN', 'KING', 'QUEEN'), ('KING', 'KING', 'KING'), ('JACK', 'ACE', 'JACK');


INSERT INTO WinPatternTable (pattern, valuee, expectedChance, occurrences) VALUES ('ACE', 15.00, 0.4630, 3), ('KING', 14.00, 1.0974, 3), ('QUEEN', 13.00, 2.1433, 3), ('JACK', 12.00, 3.7037, 3), ('ACE', 11.00, 6.9444, 2);


