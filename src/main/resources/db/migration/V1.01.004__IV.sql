CREATE TABLE IF NOT EXISTS dp.elector (
  Id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  Upd_prefix VARCHAR(50) COMMENT 'Κωδικός ΟΤΑ',
  Kod_dhm_enot VARCHAR(50) COMMENT 'Κωδικός Δημοτικής Ενότητας',
  Kod_ekl_diam VARCHAR(50) COMMENT 'Κωδ.Εκλ.Διαμ.',
  Fylo VARCHAR(50) COMMENT 'Φύλο',
  Eponymo VARCHAR(50) COMMENT 'Επώνυμο',
  Eponymo_b VARCHAR(50) COMMENT 'Επώνυμο β',
  Onoma VARCHAR(50) COMMENT 'Όνομα',
  Onoma_b VARCHAR(50) COMMENT 'Όνομα β',
  On_pat VARCHAR(50) COMMENT 'Όνομα πατέρα',
  Epon_pat VARCHAR(50) COMMENT 'Επώνυμο πατέρα',
  On_mht VARCHAR(50) COMMENT 'Όνομα μητέρας',
  On_syz VARCHAR(50) COMMENT 'Όνομα συζύγου',
  Etos_gen BIGINT COMMENT 'Έτος γέννησης',
  Mhn_gen BIGINT COMMENT 'Μήνας γέννησης',
  Mer_gen BIGINT COMMENT 'Μέρα γέννησης',
  Dhmot VARCHAR(50) COMMENT 'Αρ. Δημοτολογίου',
  Odos_tax_dieyt VARCHAR(50) COMMENT 'Οδός',
  Ar_tax_dieyt VARCHAR(50) COMMENT 'Αριθμός',
  Tax_kod VARCHAR(50) COMMENT 'Ταχ. Κώδικας',
  Poly_periox VARCHAR(50) COMMENT 'Πόλη-Περιοχή',
  Eid_ekl_ar VARCHAR(50) COMMENT 'Ειδ. Εκλ. Αριθμός',
  Et_Id BIGINT COMMENT 'Κωδικός αίτησης ετεροδημότη',
  Et_dhmos_diamon VARCHAR(50) COMMENT 'Δήμος Διαμονής Ετεροδημότη',
  Et_odos_tax_dieyt VARCHAR(50) COMMENT 'Οδός Διαμονής Ετεροδημότη',
  Et_ar_tax_dieyt VARCHAR(50) COMMENT 'Αριθμός Διαμονής Ετεροδημότη',
  Et_tax_kod VARCHAR(50) COMMENT 'Τ.Κ. Διαμονής Ετεροδημότη',
  Et_poly_periox VARCHAR(50) COMMENT 'Πόλη – Περιοχή Διαμονής Ετεροδημότη',
  Et_ked_diamon VARCHAR(50) COMMENT 'ΚΕΔ Διαμονής Ετεροδημότη',
  Dipldiafdim_flag BIGINT COMMENT 'Flag διπλού',
  PRIMARY KEY(Id)
) ENGINE = INNODB COMMENT 'Εκλογέας';



CREATE TABLE IF NOT EXISTS dp.voter (
  n_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id εγγραφής',
  n_elector_id BIGINT COMMENT 'id εκλογέα',
  n_electionprocedure_id BIGINT COMMENT 'id εκλογικής διαδικασίας',
  v_round VARCHAR(30) COMMENT 'Γύρος αναμέτρησης',
  n_electiondepartment_id BIGINT COMMENT 'id εκλογικού τμήματος',
  v_eklspecialno VARCHAR(50) COMMENT 'Ειδικός εκλογικός αριθμός',
  v_lastname VARCHAR(50) COMMENT 'Επίθετο',
  v_firstname VARCHAR(50) COMMENT 'Όνομα',
  v_fatherfirstname VARCHAR(50) COMMENT 'Πατρώνυμο',
  v_motherfirstname VARCHAR(50) COMMENT 'Μητρώνυμο',
  dt_birthdate DATE COMMENT 'Ημερομηνία γέννησης',
  n_birthyear BIGINT COMMENT 'Έτος γέννησης',
  v_address VARCHAR(50) COMMENT 'Οδός',
  v_addressno VARCHAR(50) COMMENT 'Αριθμός',
  v_city VARCHAR(50) COMMENT 'Πόλη',
  v_postalcode VARCHAR(30) COMMENT 'Ταχ. κώδικας',
  v_country VARCHAR(50) COMMENT 'Χώρα',
  v_cellphone VARCHAR(30) COMMENT 'Κινητό τηλέφωνο',
  v_email VARCHAR(50) COMMENT 'email',
  n_voted TINYINT DEFAULT 0 COMMENT 'Ένδειξη ψήφου',
  dt_votedatetime DATE COMMENT 'Ημερομηνία/ώρα ψήφου',
  n_member TINYINT DEFAULT 0 COMMENT 'Ένδειξη μέλους',
  n_payment DOUBLE COMMENT 'Ποσό πληρωμής',
  PRIMARY KEY(n_id)
) ENGINE = INNODB COMMENT 'Ψηφίσας';


ALTER TABLE dp.voter
ADD CONSTRAINT voter_electiondepartment_fk
FOREIGN KEY (n_electiondepartment_id)
REFERENCES dp.electiondepartment(n_id);

ALTER TABLE dp.voter
ADD CONSTRAINT voter_electionprocedure_fk
FOREIGN KEY (n_electionprocedure_id)
REFERENCES dp.electionprocedure(n_id);

------------------------------------------------------------------------------------------------------------------------

ALTER TABLE dp.contribution
ADD n_volunteer_id BIGINT COMMENT 'id εθελοντή';

ALTER TABLE dp.contribution
DROP FOREIGN KEY contribution_contributor_fk;

ALTER TABLE dp.contribution
DROP COLUMN n_contributor_id,
DROP INDEX contribution_contributor_fk;



------------------------------------------------------------------------------------------------------------------------

ALTER TABLE dp.voter
CHANGE COLUMN dt_votedatetime dt_votedatetime TIMESTAMP NULL DEFAULT NULL COMMENT 'Ημερομηνία/ώρα ψήφου' ;


ALTER TABLE dp.elector
  ADD COLUMN Per_dhm_enot VARCHAR(50) NULL DEFAULT NULL AFTER Dipldiafdim_flag,
  ADD COLUMN Per_ekl_diam VARCHAR(50) NULL DEFAULT NULL AFTER Per_dhm_enot;


-- Αποτελέσματα

ALTER TABLE dp.electiondepartment
ADD n_submittedfirst TINYINT(1) DEFAULT 0 COMMENT 'Ένδειξη αποστολής αποτελεσμάτων 1ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
ADD n_submittedsecond TINYINT(1) DEFAULT 0 COMMENT 'Ένδειξη αποστολής αποτελεσμάτων 2ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
CHANGE v_attachment v_attachmentfirst MEDIUMBLOB COMMENT 'Επισυναπτόμενο 1ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
ADD v_attachmentsecond MEDIUMBLOB COMMENT 'Επισυναπτόμενο 2ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
ADD v_attachmentnamefirst VARCHAR(255) COMMENT 'Όνομα επισυναπτόμενου 1ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
ADD v_attachmentnamesecond VARCHAR(255) COMMENT 'Όνομα επισυναπτόμενου 2ης αναμέτρησης';

-- Αύξηση μεγέθους πεδίων

ALTER TABLE dp.electionprocedure
MODIFY v_name VARCHAR(150) COMMENT 'Όνομα';

ALTER TABLE dp.electioncenter
MODIFY v_code VARCHAR(150) COMMENT 'Κωδικός',
MODIFY v_name VARCHAR(150) COMMENT 'Όνομα',
MODIFY v_address VARCHAR(150) COMMENT 'Διεύθυνση',
MODIFY v_postalcode VARCHAR(50) COMMENT 'Ταχυδρομικός κώδικας';

ALTER TABLE dp.electiondepartment
MODIFY v_code VARCHAR(150) COMMENT 'Κωδικός',
MODIFY v_name VARCHAR(150) COMMENT 'Όνομα';


ALTER TABLE dp.candidate
MODIFY v_lastname VARCHAR(150) COMMENT 'Επίθετο',
MODIFY v_firstname VARCHAR(150) COMMENT 'Όνομα';

ALTER TABLE dp.contributor
MODIFY v_lastname VARCHAR(150) COMMENT 'Επίθετο',
MODIFY v_firstname VARCHAR(150) COMMENT 'Όνομα',
MODIFY v_fatherfirstname VARCHAR(150) COMMENT 'Πατρώνυμο',
MODIFY v_telephone VARCHAR(50) COMMENT 'Τηλέφωνο',
MODIFY v_cellphone VARCHAR(50) COMMENT 'Κινητό τηλέφωνο',
MODIFY v_email VARCHAR(150) COMMENT 'email',
MODIFY v_address VARCHAR(150) COMMENT 'Διεύθυνση',
MODIFY v_postalcode VARCHAR(100) COMMENT 'Ταχυδρομικός κώδικας';

-- Εκλογικό Κέντρο


alter table dp.electioncenter add n_floornumber int null default 0 comment 'Όροφος';

alter table dp.electioncenter add n_disabledaccess tinyint default 0 comment 'Πρόσβαση στα ΑΜΕΑ';

ALTER TABLE dp.electioncenter
ADD n_ballotboxes INT COMMENT 'Αριθμός κάλπεων',
ADD n_estimated_ballotboxes INT COMMENT 'Αριθμός εκτιμώμενων κάλπεων',
ADD n_voters2007 INT COMMENT 'Ψηφίσαντες του 2007';






create table if not exists dp.country (
  v_isocode char(3) not null comment 'Κωδικός σύμφωνα με το πρότυπο ISO 3166-1 alpha-3',
  v_name varchar(255) not null comment 'Ονομασία χώρας',
  v_namelatin varchar(255) not null comment 'Διεθνής ονομασία χώρας',
  primary key(v_isocode)
) engine = innodb comment 'Χώρα';

insert into dp.country (v_isocode, v_name, v_namelatin) values ('AFG', 'Αφγανιστάν', 'Afghanistan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ALB', 'Αλβανία', 'Albania');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ATA', 'Ανταρκτική', 'Antarctica');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('DZA', 'Αλγερία', 'Algeria');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ASM', 'Αμερικανική Σαμόα', 'American Samoa');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('AGO', 'Ανγκόλα', 'Angola');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ATG', 'Αντίγκουα και Μπαρμπούντα', 'Antigua and Barbuda');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('AZE', 'Αζερμπαϊτζάν', 'Azerbaijan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ARG', 'Αργεντινή', 'Argentina');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('AUS', 'Αυστραλία', 'Australia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('AUT', 'Αυστρία', 'Austria');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BHS', 'Μπαχάμες', 'Bahamas');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BHR', 'Μπαχρέιν', 'Bahrain');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BGD', 'Μπαγκλαντές', 'Bangladesh');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ARM', 'Αρμενία', 'Armenia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BRB', 'Μπαρμπάντος', 'Barbados');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BEL', 'Βέλγιο', 'Belgium');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BMU', 'Βερμούδες', 'Bermuda');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BTN', 'Μπουτάν', 'Bhutan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BOL', 'Βολιβία', 'Bolivia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BIH', 'Βοσνία-Ερζεγοβίνη', 'Bosnia and Herzegovina');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BWA', 'Μποτσουάνα', 'Botswana');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BVT', 'Νησί Μπουβέτ', 'Bouvet Island');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BRA', 'Βραζιλία', 'Brazil');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BLZ', 'Μπελίζ', 'Belize');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('IOT', 'Βρετανικά Εδάφη Ινδικού Ωκεανού', 'British Indian Ocean Territory');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SLB', 'Νήσοι του Σολομώντος', 'Solomon Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('VGB', 'Βρετανικές Παρθένοι Νήσοι', 'Virgin Islands, British');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BRN', 'Μπρουνέι', 'Brunei Darussalam');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BGR', 'Βουλγαρία', 'Bulgaria');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MMR', 'Βιρμανία', 'Myanmar');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BDI', 'Μπουρούντι', 'Burundi');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BLR', 'Λευκορωσία', 'Belarus');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('KHM', 'Καμπότζη', 'Cambodia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CMR', 'Καμερούν', 'Cameroon');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CAN', 'Καναδάς', 'Canada');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CYM', 'Νήσοι Καίυμαν', 'Cayman Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CAF', 'Δημοκρατία Κεντρικής Αφρικής', 'Central African Republic');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LKA', 'Σρι Λάνκα', 'Sri Lanka');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TCD', 'Τσαντ', 'Chad');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CHL', 'Χιλή', 'Chile');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CHN', 'Κίνα', 'China');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TWN', 'Ταϊβάν', 'Taiwan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CXR', 'Νησί των Χριστουγέννων', 'Christmas Island');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CCK', 'Νησιά Κόκος (Keeling)', 'Cocos (Keeling) Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('COL', 'Κολομβία', 'Colombia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('COM', 'Κομόρες', 'Comoros');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MYT', 'Μαγιότ', 'Mayotte');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('COG', 'Κονγκό', 'Congo');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('COD', 'Λαϊκή Δημοκρατία του Κογκό', 'Congo, the Democratic Republic of the');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('COK', 'Νήσοι Κουκ', 'Cook Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CRI', 'Κόστα Ρίκα', 'Costa Rica');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('HRV', 'Κροατία', 'Croatia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CUB', 'Κούβα', 'Cuba');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CYP', 'Κύπρος', 'Cyprus');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CZE', 'Τσεχία', 'Czech Republic');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BEN', 'Μπενίν', 'Benin');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('DNK', 'Δανία', 'Denmark');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('DMA', 'Ντομίνικα', 'Dominica');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('DOM', 'Δομινικανή Δημοκρατία', 'Dominican Republic');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ECU', 'Ισημερινός', 'Ecuador');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SLV', 'Ελ Σαλβαδόρ', 'El Salvador');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GNQ', 'Ισημερινή Γουινέα', 'Equatorial Guinea');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ETH', 'Αιθιοπία', 'Ethiopia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ERI', 'Ερυθραία', 'Eritrea');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('EST', 'Εσθονία', 'Estonia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('FRO', 'Νήσοι Φερόες', 'Faroe Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('FLK', 'Νήσοι Φώκλαντ', 'Falkland Islands (Malvinas)');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SGS', 'Νότια Γεωργία και Νότια Νησιά Σάντουιτς', 'South Georgia and the South Sandwich Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('FJI', 'Φίτζι', 'Fiji');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('FIN', 'Φινλανδία', 'Finland');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ALA', 'Νησιά Άλαντ', 'Aland Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('FRA', 'Γαλλία', 'France');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GUF', 'Γαλλική Γουιάνα', 'French Guiana');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PYF', 'Γαλλική Πολυνησία', 'French Polynesia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ATF', 'Γαλλικά νότια και ανταρκτικά εδάφη', 'French Southern Territories');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('DJI', 'Τζιμπουτί', 'Djibouti');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GAB', 'Γκαμπόν', 'Gabon');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GEO', 'Γεωργία', 'Georgia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GMB', 'Γκάμπια', 'Gambia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('XXX', 'ΧΧΧ', 'XXX');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('DEU', 'Γερμανία', 'Germany');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('000', 'Άγνωστο', 'Uknown');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GHA', 'Γκάνα', 'Ghana');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GIB', 'Γιβραλτάρ', 'Gibraltar');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('KIR', 'Κιριμπάτι', 'Kiribati');
-- insert into dp.country (v_isocode, v_name, v_namelatin) values ('GRC', 'Ελλάδα', 'Greece');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GRL', 'Γροιλανδία', 'Greenland');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GRD', 'Γρενάδα', 'Grenada');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GLP', 'Γουαδελούπη', 'Guadeloupe');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GUM', 'Γκουάμ', 'Guam');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GTM', 'Γουατεμάλα', 'Guatemala');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GIN', 'Γουινέα', 'Guinea');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GUY', 'Γουιάνα', 'Guyana');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('HTI', 'Αϊτή', 'Haiti');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('HMD', 'Νήσος Χέρντ και Νησιά ΜακΝτόναλντ', 'Heard Island and McDonald Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('VAT', 'Βατικανό', 'Holy See (Vatican City State)');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('HND', 'Ονδούρα', 'Honduras');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('HKG', 'Χονγκ Κονγκ', 'Hong Kong');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('HUN', 'Ουγγαρία', 'Hungary');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ISL', 'Ισλανδία', 'Iceland');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('IND', 'Ινδία', 'India');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('IDN', 'Ινδονησία', 'Indonesia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('IRN', 'Ιράν', 'Iran, Islamic Republic of');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('IRQ', 'Ιράκ', 'Iraq');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('IRL', 'Ιρλανδία', 'Ireland');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ISR', 'Ισραήλ', 'Israel');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ITA', 'Ιταλία', 'Italy');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CIV', 'Ακτή Ελεφαντοστού', 'C?te d''Ivoire');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('JAM', 'Τζαμάικα', 'Jamaica');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('JPN', 'Ιαπωνία', 'Japan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('KAZ', 'Καζακστάν', 'Kazakhstan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('JOR', 'Ιορδανία', 'Jordan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('KEN', 'Κένυα', 'Kenya');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PRK', 'Βόρεια Κορέα', 'Korea, Democratic People''s Republic of');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('KOR', 'Νότια Κορέα', 'Korea, Republic of');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('KWT', 'Κουβέιτ', 'Kuwait');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('KGZ', 'Κιργιστάν (Κιργιζία)', 'Kyrgyzstan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LAO', 'Λάος', 'Lao People''s Democratic Republic');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LBN', 'Λίβανος', 'Lebanon');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LSO', 'Λεσότο', 'Lesotho');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LVA', 'Λετονία', 'Latvia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LBR', 'Λιβερία', 'Liberia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LBY', 'Λιβύη', 'Libyan Arab Jamahiriya');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LIE', 'Λιχτενστάιν', 'Liechtenstein');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LTU', 'Λιθουανία', 'Lithuania');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LUX', 'Λουξεμβούργο', 'Luxembourg');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MAC', 'Μακάο', 'Macao');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MDG', 'Μαδαγασκάρη', 'Madagascar');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MWI', 'Μαλάουι', 'Malawi');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MYS', 'Μαλαισία', 'Malaysia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MDV', 'Μαλβίδες', 'Maldives');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MLI', 'Μαλί', 'Mali');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MLT', 'Μάλτα', 'Malta');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MTQ', 'Μαρτινίκα', 'Martinique');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MRT', 'Μαυριτανία', 'Mauritania');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MUS', 'Μαυρίκιος', 'Mauritius');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MEX', 'Μεξικό', 'Mexico');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MCO', 'Μονακό', 'Monaco');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MNG', 'Μογγολία', 'Mongolia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MDA', 'Μολδαβία', 'Moldova, Republic of');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MNE', 'Μαυροβούνιο', 'Montenegro');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MSR', 'Μοντσερράτ', 'Montserrat');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MAR', 'Μαρόκο', 'Morocco');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MOZ', 'Μοζαμβίκη', 'Mozambique');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('OMN', 'Ομάν', 'Oman');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NAM', 'Ναμίμπια', 'Namibia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NRU', 'Ναουρού', 'Nauru');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NPL', 'Νεπάλ', 'Nepal');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NL', 'Ολλανδία', 'Netherlands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ANT', 'Ολλανδικές Αντίλλες', 'Netherlands Antilles');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ABW', 'Αρούμπα', 'Aruba');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NCL', 'Νέα Καληδονία', 'New Caledonia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('VUT', 'Βανουάτου', 'Vanuatu');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NZL', 'Νέα Ζηλανδία', 'New Zealand');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NIC', 'Νικαράγουα', 'Nicaragua');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NER', 'Νίγηρας', 'Niger');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NGA', 'Νιγηρία', 'Nigeria');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NIU', 'Νιούε', 'Niue');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NFK', 'Νησί Νόρφολκ', 'Norfolk Island');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NOR', 'Νορβηγία', 'Norway');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MNP', 'Βόρειες Μαριάνες Νήσοι', 'Northern Mariana Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('UMI', 'Ηνωμένες Πολιτείες - Μικρά απομακρυσμένα νησιά', 'United States Minor Outlying Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('FSM', 'Ομόσπονδα Κράτη της Μικρονησίας', 'Micronesia, Federated States of');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('MHL', 'Νησιά Μάρσαλ', 'Marshall Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PLW', 'Παλάου', 'Palau');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PAK', 'Πακιστάν', 'Pakistan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PAN', 'Παναμάς', 'Panama');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PNG', 'Παπούα - Νέα Γουϊνέα', 'Papua New Guinea');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PRY', 'Παραγουάη', 'Paraguay');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PER', 'Περού', 'Peru');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PHL', 'Φιλιππίνες', 'Philippines');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PCN', 'Νησιά Πίτκερν', 'Pitcairn');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('POL', 'Πολωνία', 'Poland');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PRT', 'Πορτογαλία', 'Portugal');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GNB', 'Γουινέα-Μπισσάου', 'Guinea-Bissau');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TLS', 'Ανατολικό Τιμόρ', 'Timor-Leste');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PRI', 'Πουέρτο Ρίκο', 'Puerto Rico');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('QAT', 'Κατάρ', 'Qatar');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('REU', 'Ρεϋνιόν', 'Reunion');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ROU', 'Ρουμανία', 'Romania');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('RUS', 'Ρωσία', 'Russian Federation');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('RWA', 'Ρουάντα', 'Rwanda');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SHN', 'Σάντα Έλενα', 'Saint Helena');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('KNA', 'Άγιος Χριστόφορος και Νέβις', 'Saint Kitts and Nevis');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('AIA', 'Ανγκουίλα', 'Anguilla');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('LCA', 'Σάντα Λουτσία', 'Saint Lucia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SPM', 'Άγιος Πιέρ και Μικελόν', 'Saint Pierre and Miquelon');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('VCT', 'Άγιος Βικέντιος', 'Saint Vincent and the Grenadines');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('STP', 'Σάο Τομέ και Πρίντσιπε', 'Sao Tome and Principe');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SAU', 'Σαουδική Αραβία', 'Saudi Arabia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SEN', 'Σενεγάλη', 'Senegal');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SRB', 'Σερβία', 'Serbia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SYC', 'Σεϋχέλλες', 'Seychelles');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SLE', 'Σιέρα Λεόνε', 'Sierra Leone');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SGP', 'Σινγκαπούρη', 'Singapore');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SVK', 'Σλοβακία', 'Slovakia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('VNM', 'Βιετνάμ', 'Viet Nam');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SVN', 'Σλοβενία', 'Slovenia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SOM', 'Σομαλία', 'Somalia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ZAF', 'Νότια Αφρική', 'South Africa');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ZWE', 'Ζιμπάμπουε', 'Zimbabwe');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ESP', 'Ισπανία', 'Spain');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ESH', 'Δυτική Σαχάρα', 'Western Sahara');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SDN', 'Σουδάν', 'Sudan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SUR', 'Σουρινάμ', 'Suriname');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SJM', 'Σβάλμπαρντ', 'Svalbard and Jan Mayen');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SWZ', 'Σουαζηλάνδη', 'Swaziland');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SWE', 'Σουηδία', 'Sweden');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CHE', 'Ελβετία', 'Switzerland');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('SYR', 'Συρία', 'Syrian Arab Republic');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TJK', 'Τατζικιστάν', 'Tajikistan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('THA', 'Ταϊλάνδη', 'Thailand');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TGO', 'Τόγκο', 'Togo');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TKL', 'Τοκελάου', 'Tokelau');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TON', 'Τόγκα', 'Tonga');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TTO', 'Τρινιντάντ και Τομπάγκο', 'Trinidad and Tobago');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ARE', 'Ηνωμένα Αραβικά Εμιράτα', 'United Arab Emirates');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TUN', 'Τυνησία', 'Tunisia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TUR', 'Τουρκία', 'Turkey');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TKM', 'Τουρκμενιστάν', 'Turkmenistan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TCA', 'Τουρκς και Κάϊκος Νήσοι', 'Turks and Caicos Islands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TUV', 'Τουβαλού', 'Tuvalu');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('UGA', 'Ουγκάντα', 'Uganda');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('UKR', 'Ουκρανία', 'Ukraine');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('FYR', 'ΠΓΔΜ (FYROM)', 'The former Yugoslav Republic of Macedonia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('EGY', 'Αίγυπτος', 'Egypt');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GBR', 'Ηνωμένο Βασίλειο', 'United Kingdom');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('GGY', 'Γκέρνσεϋ', 'Guernsey');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('JEY', 'Τζέρσεϋ', 'Jersey');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('IMN', 'Νήσος Μαν', 'Isle of Man');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('TZA', 'Τανζανία', 'Tanzania, United Republic of');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('USA', 'Ηνωμένες Πολιτείες Αμερικής', 'United States');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('VIR', 'Παρθένα Νησιά', 'Virgin Islands, U.S.');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('BFA', 'Μπουρκίνα Φάσο', 'Burkina Faso');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('URY', 'Ουρουγουάη', 'Uruguay');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('UZB', 'Ουζμπεκιστάν', 'Uzbekistan');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('VEN', 'Βενεζουέλα', 'Venezuela');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('WLF', 'Ουάλις και Φουτούνα', 'Wallis and Futuna');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('WSM', 'Σαμόα', 'Samoa');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('YEM', 'Υεμένη', 'Yemen');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('ZMB', 'Ζάμπια', 'Zambia');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('CPV', 'Κάμπο Βέρντε', 'REPUBLICA DE CABO VERDE');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('NLD', 'Βασίλειο των Κάτω Χωρών', 'Kingdom of the Netherlands');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('PSE', 'Παλαιστίνη', 'Palestine');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('999', 'Α.Δ. ΑΝΤΙΓΚΕΑΣ', 'from ela');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('001', 'ΔΙΕΘΝΗ ΥΔΑΤΑ', 'from ela');
insert into dp.country (v_isocode, v_name, v_namelatin) values ('002', 'ΕΝ ΠΛΩ', 'from ela');


alter table dp.electioncenter
  add n_foreign tinyint default 0 comment 'Ένδειξη για το αν το ΕΚ βρίσκεται σε ξένη χώρα',
  add v_foreigncountry_isocode char(3) default null comment 'Κωδικός ξένης χώρας σύμφωνα με το πρότυπο ISO 3166-1 alpha-2',
  add constraint electioncenter_foreigncountry_fk foreign key (v_foreigncountry_isocode) references country(v_isocode),
  add v_foreigncity varchar(255)  comment 'Πόλη ξένης χώρας';


alter table dp.electiondepartment add n_accessdifficulty tinyint default 0 comment 'Δυσπρόσιτο';






INSERT INTO dp.adminunit VALUES (22001, 15630, 6, 'MUNICIPALITY_UNIT', '450101', '1ο ΔΙΑΜΕΡΙΣΜΑ');
INSERT INTO dp.adminunit VALUES (22002, 15630, 6, 'MUNICIPALITY_UNIT', '450102', '2ο ΔΙΑΜΕΡΙΣΜΑ');
INSERT INTO dp.adminunit VALUES (22003, 15630, 6, 'MUNICIPALITY_UNIT', '450103', '3ο ΔΙΑΜΕΡΙΣΜΑ');
INSERT INTO dp.adminunit VALUES (22004, 15630, 6, 'MUNICIPALITY_UNIT', '450104', '4ο ΔΙΑΜΕΡΙΣΜΑ');
INSERT INTO dp.adminunit VALUES (22005, 15630, 6, 'MUNICIPALITY_UNIT', '450105', '5ο ΔΙΑΜΕΡΙΣΜΑ');
INSERT INTO dp.adminunit VALUES (22006, 15630, 6, 'MUNICIPALITY_UNIT', '450106', '6ο ΔΙΑΜΕΡΙΣΜΑ');
INSERT INTO dp.adminunit VALUES (22007, 15630, 6, 'MUNICIPALITY_UNIT', '450107', '7ο ΔΙΑΜΕΡΙΣΜΑ');


delete from dp.country where v_isocode in ('000', '001', '002', '999');



DELETE FROM dp.rolepermission WHERE n_permission_id >= 1000;
DELETE FROM dp.permission WHERE n_id >= 1000;
DELETE FROM dp.role WHERE n_id < 10;


INSERT INTO dp.permission VALUES (100, 'cm', 'Γενικά');
INSERT INTO dp.permission VALUES (110, 'cm.general', 'Κοινά στοιχεία');

INSERT INTO dp.permission VALUES (200, 'mg', 'Διαχείριση εκλογικών κέντρων');
INSERT INTO dp.permission VALUES (210, 'mg.electioncenter', 'Εκλογικά κέντρα');
INSERT INTO dp.permission VALUES (220, 'mg.electiondepartment', 'Εκλογικά τμήματα');

INSERT INTO dp.permission VALUES (300, 'ep', 'Εκλογική διαδικασία');
INSERT INTO dp.permission VALUES (310, 'ep.verification', 'Διαπίστευση');
INSERT INTO dp.permission VALUES (320, 'ep.voter', 'Ψηφίσαντες');

INSERT INTO dp.permission VALUES (400, 'rs', 'Αποτελέσματα');
INSERT INTO dp.permission VALUES (410, 'rs.submission', 'Αποστολή αποτελεσμάτων');

INSERT INTO dp.permission VALUES (500, 'ext', 'Διασύνδεση');
INSERT INTO dp.permission VALUES (510, 'ext.volunteer', 'Εθελοντές');

INSERT INTO dp.permission VALUES (900, 'sys', 'Τεχνική διαχείριση');
INSERT INTO dp.permission VALUES (910, 'sys.admin', 'Τεχνικές ενέργειες');


INSERT INTO dp.role VALUES (11, 'sysadmin', 'Τεχνικός Διαχειριστής');
INSERT INTO dp.role VALUES (12, 'admin', 'Διαχειριστής Εφαρμογής');


INSERT INTO dp.rolepermission VALUES (11, 100);
INSERT INTO dp.rolepermission VALUES (11, 110);
INSERT INTO dp.rolepermission VALUES (11, 200);
INSERT INTO dp.rolepermission VALUES (11, 210);
INSERT INTO dp.rolepermission VALUES (11, 220);
INSERT INTO dp.rolepermission VALUES (11, 300);
INSERT INTO dp.rolepermission VALUES (11, 310);
INSERT INTO dp.rolepermission VALUES (11, 320);
INSERT INTO dp.rolepermission VALUES (11, 400);
INSERT INTO dp.rolepermission VALUES (11, 410);
INSERT INTO dp.rolepermission VALUES (11, 500);
INSERT INTO dp.rolepermission VALUES (11, 510);
INSERT INTO dp.rolepermission VALUES (11, 900);
INSERT INTO dp.rolepermission VALUES (11, 910);

INSERT INTO dp.rolepermission VALUES (12, 100);
INSERT INTO dp.rolepermission VALUES (12, 110);
INSERT INTO dp.rolepermission VALUES (12, 200);
INSERT INTO dp.rolepermission VALUES (12, 210);
INSERT INTO dp.rolepermission VALUES (12, 220);
INSERT INTO dp.rolepermission VALUES (12, 300);
INSERT INTO dp.rolepermission VALUES (12, 310);
INSERT INTO dp.rolepermission VALUES (12, 320);
INSERT INTO dp.rolepermission VALUES (12, 400);
INSERT INTO dp.rolepermission VALUES (12, 410);
INSERT INTO dp.rolepermission VALUES (12, 500);
INSERT INTO dp.rolepermission VALUES (12, 510);


INSERT INTO dp.electionprocedure VALUES (1, 'Εκλογές Προέδρου Κεντροαριστεράς 2017', 1, 'FIRST');


INSERT INTO dp.user VALUES (11, 1, 'sysadmin', 'd577adc54e95f42f15de2e7c134669888b7d6fb74df97bd62cb4f5b73c281db4', null, 'ADMIN', 'Τεχνικός', 'Διαχειριστής', null);
INSERT INTO dp.user VALUES (12, 1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', null, 'ADMIN', 'Διαχειριστής', 'Εφαρμογής', null);


INSERT INTO dp.userrole VALUES (11, 11);
INSERT INTO dp.userrole VALUES (12, 12);





