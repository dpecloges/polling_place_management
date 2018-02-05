update dp.mailtemplate
set v_body = concat(
    '<html><head><title>Εκλογές για τον Πρόεδρο του Νέου Φορέα - Ενεργοποίηση Χρήστη</title></head><body><p>Προς %USER_FULLNAME%:</p> ',
    '<p>Σας ενημερώνουμε ότι έχει δημιουργηθεί χρήστης με τα στοιχεία σας. Για να τον ενεργοποιήσετε και να ορίσετε κωδικό πρόσβασης πατήστε ',
    '<a href="%BASE_URL%/register/%USER_ACTIVATION_CODE%" target="_blank">εδώ</a>. Ευχαριστούμε.</p> ',
    '<p>Ομάδα Υποστήριξης Συστήματος Εκλογών για τον Πρόεδρο του Νέου Φορέα.</p></body></html>'
)
where v_code='USER_ACTIVATION';

ALTER TABLE dp.electiondepartment
  ADD n_verificationserialfirst BIGINT DEFAULT 0 COMMENT 'Αύξων αριθμός διαπιστεύσεων 1ης αναμέτρησης';

ALTER TABLE dp.electiondepartment
  ADD n_verificationserialsecond BIGINT DEFAULT 0 COMMENT 'Αύξων αριθμός διαπιστεύσεων 2ης αναμέτρησης';

ALTER TABLE dp.voter
  ADD n_verificationnumber BIGINT COMMENT 'Αριθμός διαπίστευσης' AFTER n_electiondepartment_id;


ALTER TABLE dp.voter
  ADD v_idtype VARCHAR(50) COMMENT 'Είδος εντύπου ταυτοποίησης' AFTER v_email;

ALTER TABLE dp.voter
  ADD v_idnumber VARCHAR(50) COMMENT 'Αριθμός εντύπου ταυτοποίησης' AFTER v_idtype;


ALTER TABLE dp.result
  ADD n_submitteddepartmentcount BIGINT COMMENT 'Αριθμός εκλογικών τμημάτων που υπέβαλαν';

ALTER TABLE dp.result
  ADD n_totaldepartmentcount BIGINT COMMENT 'Συνολικός αριθμός εκλογικών τμημάτων';



