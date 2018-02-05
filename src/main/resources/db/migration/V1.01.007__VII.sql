
ALTER TABLE dp.electiondepartment
ADD dt_submissiondatefirst DATE COMMENT 'Ημερομηνία αποστολής αποτελεσμάτων 1ης αναμέτρησης',
ADD dt_submissiondatesecond DATE COMMENT 'Ημερομηνία αποστολής αποτελεσμάτων 2ης αναμέτρησης';


ALTER TABLE dp.contribution
ADD COLUMN v_identifier VARCHAR(50) NULL DEFAULT NULL AFTER n_volunteer_id,
ADD COLUMN v_status VARCHAR(30) NULL DEFAULT NULL AFTER v_identifier,
ADD COLUMN dt_emailsentdate DATETIME NULL DEFAULT NULL AFTER v_status,
ADD COLUMN dt_registrationdate DATETIME NULL DEFAULT NULL AFTER dt_emailsentdate;


INSERT INTO dp.role VALUES (13, 'committee_leader', 'Πρόεδρος ΕΕ');
INSERT INTO dp.role VALUES (14, 'id_verifier', 'Διαπιστευτής');


INSERT INTO dp.rolepermission VALUES (13, 100);
INSERT INTO dp.rolepermission VALUES (13, 110);
INSERT INTO dp.rolepermission VALUES (13, 300);
INSERT INTO dp.rolepermission VALUES (13, 310);
INSERT INTO dp.rolepermission VALUES (13, 320);
INSERT INTO dp.rolepermission VALUES (13, 400);
INSERT INTO dp.rolepermission VALUES (13, 410);

INSERT INTO dp.rolepermission VALUES (14, 100);
INSERT INTO dp.rolepermission VALUES (14, 110);
INSERT INTO dp.rolepermission VALUES (14, 300);
INSERT INTO dp.rolepermission VALUES (14, 310);
INSERT INTO dp.rolepermission VALUES (14, 320);




create table if not exists dp.mailtemplate (
  n_id bigint primary key auto_increment not null comment 'id εγγραφής',
  v_code varchar(40) not null comment 'Κωδικός προτύπου',
  v_subject varchar(255) not null comment 'Θέμα μηνύματος (template)',
  v_body varchar(1000) not null comment 'Σώμα μηνύματος (template)',
  v_renderer varchar(20) not null comment 'Αναγνωριστικό του template engine που χρησιμοποιείται για το rendering αυτού του προτύπου',
  n_html tinyint(1) not null default 1 comment 'Ένδειξη για τα μηνύματα που παράγονται από αυτό το πρότυπο είναι html ή plain text'
);

insert into dp.mailtemplate (v_code, v_renderer, n_html, v_subject, v_body) values
  ('USER_ACTIVATION', 'simple', true, 'Εκλογές για τον Πρόεδρο του Νέου Φορέα - Ενεργοποίηση Χρήστη',
   '<html><head><title>Εκλογές για τον Πρόεδρο του Νέου Φορέα - Ενεργοποίηση Χρήστη</title></head><p>Προς %USER_FULLNAME%:</p> <p>Σας ενημερώνουμε ότι έχει δημιουργηθεί χρήστης με τα στοιχεία σας. Για να τον ενεργοποιήσετε και να ορίσετε κωδικό πρόσβασης πατήστε <a href="%BASE_URL%/users/activation?uac=%USER_ACTIVATION_CODE%" target="_blank">εδώ</a>. Ευχαριστούμε.</p> <p>Ομάδα Υποστήριξης Συστήματος Εκλογών για τον Πρόεδρο του Νέου Φορέα.</p></html>'),
  ('USER_PASSWORD_RESET', 'simple', true, 'Εκλογές για τον Πρόεδρο του Νέου Φορέα - Αλλαγή κωδικού χρήστη',
   '<html><head><title>Εκλογές για τον Πρόεδρο του Νέου Φορέα - Αλλαγή κωδικού χρήστη</title></head><p>Προς %USER_FULLNAME%:</p> <p>Λαμβάνετε αυτό το μήνυμα μετά από αίτημα αλλαγής κωδικού εισόδου στο σύστημα εκλογών για τον Πρόεδρο του Νέου Φορέα. Για να προχωρήσετε με τη διαδικασία αλλαγής κωδικού, πατήστε <a href="%BASE_URL%/users/pwdreset?uac=%USER_PWD_RESET_CODE%">εδώ</a>. Σε περίπτωση που δεν έχετε αιτηθεί την αλλαγή κωδικού παρακαλούμε αγνοήστε το μήνυμα. Ευχαριστούμε.</p> <p>Ομάδα Υποστήριξης Συστήματος Εκλογών για τον Πρόεδρο του Νέου Φορέα.</p></html>');


CREATE TABLE dp.result (
  n_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  n_electionprocedure_id BIGINT COMMENT 'id εκλογικής διαδικασίας',
  v_round VARCHAR(30) COMMENT 'Γύρος αναμέτρησης',
  v_type VARCHAR(30) COMMENT 'Τύπος αποτελέσματος',
  v_arg_id VARCHAR(30) COMMENT 'id φίλτρου αποτελέσματος',
  n_totalvotes int(11) DEFAULT NULL COMMENT 'Συνολικές ψήφοι',
  n_whitevotes int(11) DEFAULT NULL COMMENT 'Λευκές ψήφοι',
  n_invalidvotes int(11) DEFAULT NULL COMMENT 'Άκυρες ψήφοι',
  n_validvotes int(11) DEFAULT NULL COMMENT 'Έγκυρες ψήφοι',
  n_candidateonevotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 1ου υποψήφιου',
  n_candidatetwovotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 2ου υποψήφιου',
  n_candidatethreevotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 3ου υποψήφιου',
  n_candidatefourvotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 4ου υποψήφιου',
  n_candidatefivevotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 5ου υποψήφιου',
  n_candidatesixvotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 6ου υποψήφιου',
  n_candidatesevenvotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 7ου υποψήφιου',
  n_candidateeightvotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 8ου υποψήφιου',
  n_candidateninevotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 9ου υποψήφιου',
  n_candidatetenvotes int(11) DEFAULT NULL COMMENT 'Ψήφοι 10ου υποψήφιου',
  PRIMARY KEY (n_id)
) ENGINE = InnoDB COMMENT = 'Αποτέλεσμα';


ALTER TABLE dp.result
ADD CONSTRAINT result_electionprocedure_fk
FOREIGN KEY (n_electionprocedure_id)
REFERENCES dp.electionprocedure(n_id);

