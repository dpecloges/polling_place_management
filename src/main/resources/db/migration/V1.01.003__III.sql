
ALTER TABLE dp.electiondepartment ADD n_serialno TINYINT COMMENT 'Αύξων αριθμός ανά εκλογικό κέντρο';

ALTER TABLE dp.electiondepartment ADD v_attachment MEDIUMBLOB;

-- TABLE user
CREATE TABLE IF NOT EXISTS dp.user (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  n_electionprocedure_id BIGINT NOT NULL COMMENT 'id εκλογικής διαδικασίας',
  v_username VARCHAR(30) NOT NULL COMMENT 'Όνομα χρήστη',
  v_email VARCHAR(50) NULL COMMENT 'Email',
  v_type VARCHAR(20) NULL COMMENT 'Τύπος',
  v_lastname VARCHAR(50) NULL COMMENT 'Επώνυμο',
  v_firstname VARCHAR(50) NULL COMMENT 'Όνομα',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Χρήστης';

ALTER TABLE dp.user
ADD COLUMN v_password VARCHAR(300) NULL DEFAULT NULL COMMENT 'Κωδικός πρόσβασης' AFTER v_username;


-- TABLE role
CREATE TABLE IF NOT EXISTS dp.role (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  v_code VARCHAR(30) NOT NULL COMMENT 'Κωδικός',
  v_name VARCHAR(100) NOT NULL COMMENT 'Ονομασία',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Ρόλος';

-- TABLE permission
CREATE TABLE IF NOT EXISTS dp.permission (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  v_code VARCHAR(50) NOT NULL COMMENT 'Κωδικός',
  v_name VARCHAR(100) NOT NULL COMMENT 'Ονομασία',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Δικαίωμα';

-- TABLE userrole
CREATE TABLE IF NOT EXISTS dp.userrole (
  n_user_id BIGINT NOT NULL COMMENT 'id χρήστη',
  n_role_id BIGINT NOT NULL COMMENT 'id ρόλου',
  PRIMARY KEY(n_user_id, n_role_id)
) ENGINE = INNODB COMMENT 'Ρόλος χρήστη';

-- TABLE rolepermission
CREATE TABLE IF NOT EXISTS dp.rolepermission (
  n_role_id BIGINT NOT NULL COMMENT 'id ρόλου',
  n_permission_id BIGINT NOT NULL COMMENT 'id δικαιώματος',
  PRIMARY KEY(n_role_id, n_permission_id)
) ENGINE = INNODB COMMENT 'Δικαίωμα ρόλου';

-- Foreign Key Constraints for Tables userrole and rolepermission
ALTER TABLE dp.userrole
ADD CONSTRAINT userrole_user_fk
FOREIGN KEY (n_user_id)
REFERENCES dp.user(n_id);

ALTER TABLE dp.userrole
ADD CONSTRAINT userrole_role_fk
FOREIGN KEY (n_role_id)
REFERENCES dp.role(n_id);

ALTER TABLE dp.rolepermission
ADD CONSTRAINT rolepermission_role_fk
FOREIGN KEY (n_role_id)
REFERENCES dp.role(n_id);

ALTER TABLE dp.rolepermission
ADD CONSTRAINT rolepermission_permission_fk
FOREIGN KEY (n_permission_id)
REFERENCES dp.permission(n_id);


INSERT INTO dp.permission VALUES (1000, 'mg', 'Υποσύστημα διαχείρισης');

INSERT INTO dp.permission VALUES (1010, 'mg.electioncenter', 'Εκλογικό κέντρο');
INSERT INTO dp.permission VALUES (1011, 'mg.electioncenter.list', 'Ευρετήριο εκλογικών κέντρων');
INSERT INTO dp.permission VALUES (1012, 'mg.electioncenter.view', 'Εμφάνιση εκλογικών κέντρων');
INSERT INTO dp.permission VALUES (1013, 'mg.electioncenter.create', 'Δημιουργία εκλογικών κέντρων');
INSERT INTO dp.permission VALUES (1014, 'mg.electioncenter.update', 'Ενημέρωση εκλογικών κέντρων');
INSERT INTO dp.permission VALUES (1015, 'mg.electioncenter.delete', 'Διαγραφή εκλογικών κέντρων');
INSERT INTO dp.permission VALUES (1016, 'mg.electioncenter.choose', 'Επιλογή εκλογικών κέντρων');
INSERT INTO dp.permission VALUES (1017, 'mg.electioncenter.print', 'Εκτύπωση εκλογικών κέντρων');

INSERT INTO dp.permission VALUES (1020, 'mg.electiondepartment', 'Εκλογικό τμήμα');
INSERT INTO dp.permission VALUES (1021, 'mg.electiondepartment.list', 'Ευρετήριο εκλογικών τμημάτων');
INSERT INTO dp.permission VALUES (1022, 'mg.electiondepartment.view', 'Εμφάνιση εκλογικών τμημάτων');
INSERT INTO dp.permission VALUES (1023, 'mg.electiondepartment.create', 'Δημιουργία εκλογικών τμημάτων');
INSERT INTO dp.permission VALUES (1024, 'mg.electiondepartment.update', 'Ενημέρωση εκλογικών τμημάτων');
INSERT INTO dp.permission VALUES (1025, 'mg.electiondepartment.delete', 'Διαγραφή εκλογικών τμημάτων');
INSERT INTO dp.permission VALUES (1026, 'mg.electiondepartment.choose', 'Επιλογή εκλογικών τμημάτων');
INSERT INTO dp.permission VALUES (1027, 'mg.electiondepartment.print', 'Εκτύπωση εκλογικών τμημάτων');

INSERT INTO dp.permission VALUES (2000, 'rs', 'Υποσύστημα αποτελεσμάτων');

INSERT INTO dp.role VALUES (1, 'admin', 'Διαχειριστής');
INSERT INTO dp.role VALUES (2, 'mg_role', 'Καταχωρητής εκλογικών κέντρων');

INSERT INTO dp.rolepermission VALUES (1, 1000);
INSERT INTO dp.rolepermission VALUES (1, 1010);
INSERT INTO dp.rolepermission VALUES (1, 1011);
INSERT INTO dp.rolepermission VALUES (1, 1012);
INSERT INTO dp.rolepermission VALUES (1, 1013);
INSERT INTO dp.rolepermission VALUES (1, 1014);
INSERT INTO dp.rolepermission VALUES (1, 1015);
INSERT INTO dp.rolepermission VALUES (1, 1016);
INSERT INTO dp.rolepermission VALUES (1, 1017);
INSERT INTO dp.rolepermission VALUES (1, 1020);
INSERT INTO dp.rolepermission VALUES (1, 1021);
INSERT INTO dp.rolepermission VALUES (1, 1022);
INSERT INTO dp.rolepermission VALUES (1, 1023);
INSERT INTO dp.rolepermission VALUES (1, 1024);
INSERT INTO dp.rolepermission VALUES (1, 1025);
INSERT INTO dp.rolepermission VALUES (1, 1026);
INSERT INTO dp.rolepermission VALUES (1, 1027);
INSERT INTO dp.rolepermission VALUES (1, 2000);

INSERT INTO dp.rolepermission VALUES (2, 1000);
INSERT INTO dp.rolepermission VALUES (2, 1010);
INSERT INTO dp.rolepermission VALUES (2, 1011);
INSERT INTO dp.rolepermission VALUES (2, 1012);
INSERT INTO dp.rolepermission VALUES (2, 1013);
INSERT INTO dp.rolepermission VALUES (2, 1014);
INSERT INTO dp.rolepermission VALUES (2, 1015);
INSERT INTO dp.rolepermission VALUES (2, 1016);
INSERT INTO dp.rolepermission VALUES (2, 1017);
INSERT INTO dp.rolepermission VALUES (2, 1020);
INSERT INTO dp.rolepermission VALUES (2, 1021);
INSERT INTO dp.rolepermission VALUES (2, 1022);
INSERT INTO dp.rolepermission VALUES (2, 1023);
INSERT INTO dp.rolepermission VALUES (2, 1024);
INSERT INTO dp.rolepermission VALUES (2, 1025);
INSERT INTO dp.rolepermission VALUES (2, 1026);
INSERT INTO dp.rolepermission VALUES (2, 1027);

-- Add field n_electiondepartment_id to table user
ALTER TABLE dp.user ADD n_electiondepartment_id BIGINT NULL COMMENT 'id εκλογικού τμήματος';

ALTER TABLE dp.user
ADD CONSTRAINT user_electiondepartment_fk
FOREIGN KEY (n_electiondepartment_id)
REFERENCES dp.electiondepartment(n_id);
