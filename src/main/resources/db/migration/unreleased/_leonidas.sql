ALTER TABLE `dp`.`candidate`
ADD COLUMN `n_candidatefirst_id` BIGINT(20) NULL DEFAULT NULL COMMENT 'Σύνδεση με υποψήφιο πρώτου γύρου' AFTER `n_order`;

ALTER TABLE dp.voter
ADD INDEX voter_verification_snapshot_fields (n_electionprocedure_id, v_round, n_voted);
