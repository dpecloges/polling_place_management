ALTER TABLE dp.result
ADD dt_nextcalculationdatetime TIMESTAMP NULL DEFAULT NULL COMMENT 'Ημερομηνία/ώρα επόμενης ενημέρωσης' AFTER dt_calculationdatetime;

ALTER TABLE dp.voter
ADD UNIQUE INDEX voter_verification_number_fields (n_electionprocedure_id, v_round, n_electiondepartment_id, n_verificationnumber);


INSERT INTO dp.permission VALUES (420, 'rs.result', 'Προβολή αποτελεσμάτων');
INSERT INTO dp.permission VALUES (600, 'sa', 'Διαχείριση');
INSERT INTO dp.permission VALUES (610, 'sa.email', 'Αποστολή ειδοποιήσεων');
INSERT INTO dp.permission VALUES (620, 'sa.user', 'Χρήστες');

INSERT INTO dp.rolepermission VALUES (11, 420);
INSERT INTO dp.rolepermission VALUES (11, 600);
INSERT INTO dp.rolepermission VALUES (11, 610);
INSERT INTO dp.rolepermission VALUES (11, 620);

INSERT INTO dp.rolepermission VALUES (12, 420);
INSERT INTO dp.rolepermission VALUES (12, 600);
INSERT INTO dp.rolepermission VALUES (12, 610);
INSERT INTO dp.rolepermission VALUES (12, 620);

DELETE FROM dp.rolepermission WHERE n_role_id = 13 and n_permission_id = 321;

DELETE FROM dp.rolepermission WHERE n_role_id = 15 and n_permission_id = 321;
INSERT INTO dp.rolepermission VALUES (15, 600);
INSERT INTO dp.rolepermission VALUES (15, 610);
INSERT INTO dp.rolepermission VALUES (15, 620);

INSERT INTO dp.rolepermission VALUES (16, 420);
INSERT INTO dp.rolepermission VALUES (16, 600);
INSERT INTO dp.rolepermission VALUES (16, 610);
INSERT INTO dp.rolepermission VALUES (16, 620);
