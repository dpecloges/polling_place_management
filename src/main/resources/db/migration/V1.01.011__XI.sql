UPDATE dp.role SET v_name = 'Χειριστής' WHERE v_code = 'id_verifier';

ALTER TABLE dp.user
ADD UNIQUE INDEX user_username (v_username);


INSERT INTO dp.role VALUES (15, 'helpdesk', 'Help Desk');
INSERT INTO dp.role VALUES (16, 'central_committee', 'Κεντρική Εφορευτική Επιτροπή');

INSERT INTO dp.rolepermission VALUES (15, 100);
INSERT INTO dp.rolepermission VALUES (15, 110);
INSERT INTO dp.rolepermission VALUES (15, 200);
INSERT INTO dp.rolepermission VALUES (15, 210);
INSERT INTO dp.rolepermission VALUES (15, 220);
INSERT INTO dp.rolepermission VALUES (15, 300);
INSERT INTO dp.rolepermission VALUES (15, 310);
INSERT INTO dp.rolepermission VALUES (15, 320);
INSERT INTO dp.rolepermission VALUES (15, 321);
INSERT INTO dp.rolepermission VALUES (15, 400);
INSERT INTO dp.rolepermission VALUES (15, 410);
INSERT INTO dp.rolepermission VALUES (15, 500);
INSERT INTO dp.rolepermission VALUES (15, 510);

INSERT INTO dp.rolepermission VALUES (16, 100);
INSERT INTO dp.rolepermission VALUES (16, 110);
INSERT INTO dp.rolepermission VALUES (16, 200);
INSERT INTO dp.rolepermission VALUES (16, 210);
INSERT INTO dp.rolepermission VALUES (16, 220);
INSERT INTO dp.rolepermission VALUES (16, 300);
INSERT INTO dp.rolepermission VALUES (16, 310);
INSERT INTO dp.rolepermission VALUES (16, 320);
INSERT INTO dp.rolepermission VALUES (16, 321);
INSERT INTO dp.rolepermission VALUES (16, 400);
INSERT INTO dp.rolepermission VALUES (16, 410);
INSERT INTO dp.rolepermission VALUES (16, 500);
INSERT INTO dp.rolepermission VALUES (16, 510);


