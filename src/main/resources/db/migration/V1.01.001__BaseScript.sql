CREATE DATABASE IF NOT EXISTS dp;

-- TABLE electionprocedure
CREATE TABLE IF NOT EXISTS dp.electionprocedure (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  v_name VARCHAR(50) COMMENT 'Σχόλια',
  n_current TINYINT DEFAULT 0 COMMENT 'Ένδειξη ενεργής αναμέτρησης',
  v_round VARCHAR(30) COMMENT 'Γύρος αναμέτρησης',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Εκλογική Διαδικασία';

-- TABLE electioncenter
CREATE TABLE IF NOT EXISTS dp.electioncenter (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  n_electionprocedure_id BIGINT COMMENT 'id εκλογικής διαδικασίας',
  v_code VARCHAR(50) COMMENT 'Κωδικός',
  v_name VARCHAR(50) COMMENT 'Όνομα',
  v_address VARCHAR(50) COMMENT 'Διεύθυνση',
  v_postalcode VARCHAR(30) COMMENT 'Ταχυδρομικός κώδικας',
  v_telephone VARCHAR(50) COMMENT 'Τηλέφωνο',
  n_supervisorfirst_id BIGINT COMMENT 'id επόπτη 1ης αναμέτρησης',
  n_supervisorsecond_id BIGINT COMMENT 'id επόπτη 2ης αναμέτρησης',
  v_comments VARCHAR(500) COMMENT 'Σχόλια',
  n_geographicalunit_id BIGINT COMMENT 'id μεγάλης γεωγραφικής ενότητας',
  n_decentraladmin_id BIGINT COMMENT 'id αποκεντρωμένης διοίκησης',
  n_region_id BIGINT COMMENT 'id περιφέρειας',
  n_regionalunit_id BIGINT COMMENT 'id περιφερειακής ενότητας',
  n_municipality_id BIGINT COMMENT 'id δήμου',
  n_municipalunit_id BIGINT COMMENT 'id δημοτικής ενότητας',
  n_municipalcommunity_id BIGINT COMMENT 'id τοπικής/δημοτικής κοινότητας',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Εκλογικό Κέντρο';

-- TABLE electiondepartment
CREATE TABLE IF NOT EXISTS dp.electiondepartment (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  n_electioncenter_id BIGINT COMMENT 'id εκλογικού κέντρου',
  v_code VARCHAR(30) COMMENT 'Κωδικός',
  v_name VARCHAR(50) COMMENT 'Όνομα',
  v_comments VARCHAR(500) COMMENT 'Σχόλια',
  n_totalvotesfirst INT COMMENT 'Συνολικές ψήφοι 1ης αναμέτρησης',
  n_whitevotesfirst INT COMMENT 'Λευκοί ψήφοι 1ης αναμέτρησης',
  n_invalidvotesfirst INT COMMENT 'Άκυροι ψήφοι 1ης αναμέτρησης',
  n_validvotesfirst INT COMMENT 'Έγκυροι ψήφοι 1ης αναμέτρησης',
  n_candidateonevotesfirst INT COMMENT 'Ψήφοι 1ου υποψήφιου 1ης αναμέτρησης',
  n_candidatetwovotesfirst INT COMMENT 'Ψήφοι 2ου υποψήφιου 1ης αναμέτρησης',
  n_candidatethreevotesfirst INT COMMENT 'Ψήφοι 3ου υποψήφιου 1ης αναμέτρησης',
  n_candidatefourvotesfirst INT COMMENT 'Ψήφοι 4ου υποψήφιου 1ης αναμέτρησης',
  n_candidatefivevotesfirst INT COMMENT 'Ψήφοι 5ου υποψήφιου 1ης αναμέτρησης',
  n_candidatesixvotesfirst INT COMMENT 'Ψήφοι 6ου υποψήφιου 1ης αναμέτρησης',
  n_candidatesevenvotesfirst INT COMMENT 'Ψήφοι 7ου υποψήφιου 1ης αναμέτρησης',
  n_candidateeightvotesfirst INT COMMENT 'Ψήφοι 8ου υποψήφιου 1ης αναμέτρησης',
  n_candidateninevotesfirst INT COMMENT 'Ψήφοι 9ου υποψήφιου 1ης αναμέτρησης',
  n_candidatetenvotesfirst INT COMMENT 'Ψήφοι 10ου υποψήφιου 1ης αναμέτρησης',
  n_totalvotessecond INT COMMENT 'Συνολικές ψήφοι 2ης αναμέτρησης',
  n_whitevotessecond INT COMMENT 'Λευκοί ψήφοι 2ης αναμέτρησης',
  n_invalidvotessecond INT COMMENT 'Άκυροι ψήφοι 2ης αναμέτρησης',
  n_validvotessecond INT COMMENT 'Έγκυροι ψήφοι 2ης αναμέτρησης',
  n_candidateonevotessecond INT COMMENT 'Ψήφοι 1ου υποψήφιου 2ης αναμέτρησης',
  n_candidatetwovotessecond INT COMMENT 'Ψήφοι 2ου υποψήφιου 2ης αναμέτρησης',
  n_candidatethreevotessecond INT COMMENT 'Ψήφοι 3ου υποψήφιου 2ης αναμέτρησης',
  n_candidatefourvotessecond INT COMMENT 'Ψήφοι 4ου υποψήφιου 2ης αναμέτρησης',
  n_candidatefivevotessecond INT COMMENT 'Ψήφοι 5ου υποψήφιου 2ης αναμέτρησης',
  n_candidatesixvotessecond INT COMMENT 'Ψήφοι 6ου υποψήφιου 2ης αναμέτρησης',
  n_candidatesevenvotessecond INT COMMENT 'Ψήφοι 7ου υποψήφιου 2ης αναμέτρησης',
  n_candidateeightvotessecond INT COMMENT 'Ψήφοι 8ου υποψήφιου 2ης αναμέτρησης',
  n_candidateninevotessecond INT COMMENT 'Ψήφοι 9ου υποψήφιου 2ης αναμέτρησης',
  n_candidatetenvotessecond INT COMMENT 'Ψήφοι 10ου υποψήφιου 2ης αναμέτρησης',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Εκλογικό Τμήμα';

-- TABLE candidate
CREATE TABLE IF NOT EXISTS dp.candidate (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  n_electionprocedure_id BIGINT COMMENT 'id εκλογικής διαδικασίας',
  v_lastname VARCHAR(50) COMMENT 'Επίθετο',
  v_firstname VARCHAR(50) COMMENT 'Όνομα',
  v_round VARCHAR(30) COMMENT 'Γύρος αναμέτρησης',
  n_order INT COMMENT 'Σειρά ταξινόμησης',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Υποψήφιος';

-- TABLE contributor
CREATE TABLE IF NOT EXISTS dp.contributor (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  n_electionprocedure_id BIGINT COMMENT 'id εκλογικής διαδικασίας',
  v_type VARCHAR(30) COMMENT 'Τύπος συμβάλλοντα',
  v_lastname VARCHAR(50) COMMENT 'Επίθετο',
  v_firstname VARCHAR(50) COMMENT 'Όνομα',
  v_fatherfirstname VARCHAR(50) COMMENT 'Πατρώνυμο',
  v_telephone VARCHAR(30) COMMENT 'Τηλέφωνο',
  v_cellphone VARCHAR(30) COMMENT 'Κινητό τηλέφωνο',
  v_email VARCHAR(50) COMMENT 'email',
  v_address VARCHAR(50) COMMENT 'Διεύθυνση',
  v_postalcode VARCHAR(30) COMMENT 'Ταχυδρομικός κώδικας',
  v_comments VARCHAR(500) COMMENT 'Σχόλια',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Συμβάλλων';

-- TABLE contribution
CREATE TABLE IF NOT EXISTS dp.contribution (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  n_contributor_id BIGINT COMMENT 'id συμβαλλόντος',
  n_electiondepartment_id BIGINT COMMENT 'id εκλογικού τμήματος',
  v_type VARCHAR(30) COMMENT 'Τύπος συμβολής',
  v_round VARCHAR(30) COMMENT 'Γύρος εκλογικής αναμέτρησης',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Συμβολή';

-- Foreign Key Constraints for Table electioncenter
ALTER TABLE dp.electioncenter
ADD CONSTRAINT electioncenter_electionprocedure_fk
FOREIGN KEY (n_electionprocedure_id)
REFERENCES dp.electionprocedure(n_id);

ALTER TABLE dp.electioncenter
ADD CONSTRAINT electioncenter_supervisorfirst_fk
FOREIGN KEY (n_supervisorfirst_id)
REFERENCES dp.contributor(n_id);

ALTER TABLE dp.electioncenter
ADD CONSTRAINT electioncenter_supervisorsecond_fk
FOREIGN KEY (n_supervisorsecond_id)
REFERENCES dp.contributor(n_id);

-- Foreign Key Constraints for Table electiondepartment
ALTER TABLE dp.electiondepartment
ADD CONSTRAINT electiondepartment_electionprocedure_fk
FOREIGN KEY (n_electioncenter_id)
REFERENCES dp.electioncenter(n_id);

-- Foreign Key Constraints for Table candidate
ALTER TABLE dp.candidate
ADD CONSTRAINT candidate_electionprocedure_fk
FOREIGN KEY (n_electionprocedure_id)
REFERENCES dp.electionprocedure(n_id);

-- Foreign Key Constraints for Table contributor
ALTER TABLE dp.contributor
ADD CONSTRAINT contributor_electionprocedure_fk
FOREIGN KEY (n_electionprocedure_id)
REFERENCES dp.electionprocedure(n_id);

-- Foreign Key Constraints for Table contribution
ALTER TABLE dp.contribution
ADD CONSTRAINT contribution_contributor_fk
FOREIGN KEY (n_contributor_id)
REFERENCES dp.contributor(n_id);

ALTER TABLE dp.contribution
ADD CONSTRAINT contribution_electiondepartment_fk
FOREIGN KEY (n_electiondepartment_id)
REFERENCES dp.electiondepartment(n_id);