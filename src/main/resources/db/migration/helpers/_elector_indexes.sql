
alter table elector add key elector_eid_ekl_ar_idx (Eid_ekl_ar);
alter table elector add key elector_search_idx (Eponymo, Onoma, On_pat, etos_gen);
alter table elector add key elector_onoma_idx (Onoma);
alter table elector add key elector_eponymo_idx (Eponymo);
alter table elector add key elector_on_pat_idx (on_pat);
alter table elector add key elector_on_mht_idx (on_mht);
alter table elector add key elector_birthymd_idx (etos_gen, mhn_gen, mer_gen);
alter table elector add key elector_birthyear_idx (etos_gen);
