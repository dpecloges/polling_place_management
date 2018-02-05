alter table dp.user modify v_username varchar(100) NOT NULL COMMENT 'Όνομα χρήστη';
alter table dp.user modify v_email varchar(100) DEFAULT NULL COMMENT 'Email';

ALTER TABLE dp.contribution
ADD n_candidate_id BIGINT COMMENT 'Id υποψηφίου που εκπροσωπείται';

ALTER TABLE dp.contribution
ADD CONSTRAINT contribution_candidate_fk
FOREIGN KEY (n_candidate_id)
REFERENCES dp.candidate(n_id);


INSERT INTO dp.permission VALUES (520, 'ext.contribution', 'Εξωτερική εφαρμογή εθελοντών');
INSERT INTO dp.role VALUES (17, 'external', 'Διαλειτουργικότητα');
INSERT INTO dp.rolepermission VALUES (17, 520);
INSERT INTO dp.user VALUES (17, 1, 'external', 'bb35f6042a1700a81dc986ca6fda0c96e01e49ee81286ae37e1e46302aed7a9d', null, 'USER', 'Διαλειτουργικότητα', '', null);
INSERT INTO dp.userrole VALUES (17, 17);


DELETE FROM dp.rolepermission WHERE n_role_id = 13 AND n_permission_id = 410;
INSERT INTO dp.rolepermission VALUES (14, 410);
