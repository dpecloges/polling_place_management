
INSERT INTO dp.permission VALUES (321, 'ep.voter.undo', 'Αναίρεση διαπίστευσης');

INSERT INTO dp.rolepermission VALUES (11, 321);
INSERT INTO dp.rolepermission VALUES (12, 321);
INSERT INTO dp.rolepermission VALUES (13, 321);


ALTER TABLE dp.result
ADD dt_calculationdatetime TIMESTAMP NULL DEFAULT NULL COMMENT 'Ημερομηνία/ώρα υπολογισμού' AFTER n_id;

ALTER TABLE dp.electionprocedure
ADD dt_resultlastcalcdatetime TIMESTAMP NULL DEFAULT NULL COMMENT 'Τελευταία ημερομηνία/ώρα υπολογισμού αποτελεσμάτων';


UPDATE dp.adminunit
SET v_type = 'MUNICIPAL_UNIT'
WHERE v_type = 'MUNICIPALITY_UNIT';


ALTER TABLE dp.result
ADD INDEX result_search (dt_calculationdatetime, n_electionprocedure_id, v_round, v_type, v_arg_id);

ALTER TABLE dp.contribution
ADD INDEX contribution_identifier (v_identifier ASC);
