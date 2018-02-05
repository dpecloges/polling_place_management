ALTER TABLE dp.snapshot
ADD INDEX snapshot_search_pack (dt_calculationdatetime, n_electionprocedure_id, v_round, v_type);