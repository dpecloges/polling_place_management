
ALTER TABLE dp.voter
ADD v_undoreason VARCHAR(300) COMMENT 'Αιτιολογία αναίρεσης';


ALTER TABLE dp.electiondepartment
ADD n_allowinconsistentsubmission TINYINT DEFAULT 0 COMMENT 'Ένδειξη ότι επιτρέπεται η αποστολή αποτελεσμάτων ανεξάρτητα από τον αριθμό διαπιστεύσεων';


ALTER TABLE dp.electiondepartment
ADD v_attachmenttwofirst MEDIUMBLOB COMMENT 'Επισυναπτόμενο πρακτικό αποδείξεων 1ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
ADD v_attachmenttwosecond MEDIUMBLOB COMMENT 'Επισυναπτόμενο πρακτικό αποδείξεων 2ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
ADD v_attachmenttwonamefirst VARCHAR(255) COMMENT 'Όνομα επισυναπτόμενου πρακτικού αποδείξεων 1ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
ADD v_attachmenttwonamesecond VARCHAR(255) COMMENT 'Όνομα επισυναπτόμενου πρακτικού αποδείξεων 2ης αναμέτρησης';

CREATE TABLE dp.snapshot (
  n_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  dt_calculationdatetime TIMESTAMP NULL COMMENT 'Ημερομηνία/ώρα υπολογισμού',
  n_electionprocedure_id BIGINT COMMENT 'id εκλογικής διαδικασίας',
  dt_nextcalculationdatetime TIMESTAMP NULL COMMENT 'Ημερομηνία/ώρα επόμενης ενημέρωσης',
  v_round VARCHAR(30) COMMENT 'Γύρος αναμέτρησης',
  v_type VARCHAR(30) COMMENT 'Τύπος αποτελέσματος',
  v_arg_id VARCHAR(30) COMMENT 'id φίλτρου αποτελέσματος',
  n_totaldepartmentcount BIGINT COMMENT 'Συνολικός αριθμός εκλογικών τμημάτων',
  n_starteddepartmentcount BIGINT COMMENT 'Αριθμός εκλογικών τμημάτων στα οποία έχει ξεκινήσει η εκλογική διαδικασία',
  n_submitteddepartmentcount BIGINT COMMENT 'Αριθμός εκλογικών τμημάτων που υπέβαλαν αποτελέσματα',
  n_votercount int(11) DEFAULT NULL COMMENT 'Αριθμός διαπιστεύσεων',
  n_undonevotercount int(11) DEFAULT NULL COMMENT 'Αριθμός άρσεων διαπίστευσης',
  PRIMARY KEY (n_id)
) ENGINE = InnoDB COMMENT = 'Στιγμιότυπο Προόδου Εκλογικής Διαδικασίας';


ALTER TABLE dp.snapshot
ADD CONSTRAINT snapshot_electionprocedure_fk
FOREIGN KEY (n_electionprocedure_id)
REFERENCES dp.electionprocedure(n_id);

ALTER TABLE dp.electionprocedure
ADD dt_snapshotlastcalcdatetime TIMESTAMP NULL DEFAULT NULL COMMENT 'Τελευταία ημερομηνία/ώρα υπολογισμού προόδου εκλογικής διαδικασίας';

ALTER TABLE dp.snapshot
ADD INDEX snapshot_search (dt_calculationdatetime, n_electionprocedure_id, v_round, v_type, v_arg_id);

ALTER TABLE dp.snapshot
ADD v_name VARCHAR(150) NULL DEFAULT NULL COMMENT 'Ονομασία' AFTER v_arg_id;


INSERT INTO dp.permission VALUES (330, 'ep.snapshot', 'Εξέλιξη εκλογικής διαδικασίας');

INSERT INTO dp.rolepermission VALUES (11, 330);
INSERT INTO dp.rolepermission VALUES (12, 330);
INSERT INTO dp.rolepermission VALUES (16, 330);



CREATE TABLE dp.scheduled_job_calc (
  n_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  dt_calculationdatetime TIMESTAMP NULL COMMENT 'Ημερομηνία/ώρα υπολογισμού',
  n_electionprocedure_id BIGINT COMMENT 'id εκλογικής διαδικασίας',
  v_jobgroup VARCHAR(100) COMMENT 'Ομάδα εργασιών',
  n_order BIGINT DEFAULT 0 COMMENT 'Σειρά εκτέλεσης',
  PRIMARY KEY (n_id)
) ENGINE = InnoDB COMMENT = 'Στιγμιότυπο Προόδου Εκλογικής Διαδικασίας';

