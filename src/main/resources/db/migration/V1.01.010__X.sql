create table dp.qrtz_job_details
  (
    sched_name varchar(120) not null,
    job_name  varchar(200) not null,
    job_group varchar(200) not null,
    description varchar(250) null,
    job_class_name   varchar(250) not null,
    is_durable varchar(1) not null,
    is_nonconcurrent varchar(1) not null,
    is_update_data varchar(1) not null,
    requests_recovery varchar(1) not null,
    job_data blob null,
    primary key (sched_name,job_name,job_group)
);

create table dp.qrtz_triggers
  (
    sched_name varchar(120) not null,
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    job_name  varchar(200) not null,
    job_group varchar(200) not null,
    description varchar(250) null,
    next_fire_time bigint(13) null,
    prev_fire_time bigint(13) null,
    priority integer null,
    trigger_state varchar(16) not null,
    trigger_type varchar(8) not null,
    start_time bigint(13) not null,
    end_time bigint(13) null,
    calendar_name varchar(200) null,
    misfire_instr smallint(2) null,
    job_data blob null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,job_name,job_group)
        references dp.qrtz_job_details(sched_name,job_name,job_group)
);

create table dp.qrtz_simple_triggers
  (
    sched_name varchar(120) not null,
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    repeat_count bigint(7) not null,
    repeat_interval bigint(12) not null,
    times_triggered bigint(10) not null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
        references dp.qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table dp.qrtz_cron_triggers
  (
    sched_name varchar(120) not null,
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    cron_expression varchar(200) not null,
    time_zone_id varchar(80),
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
        references dp.qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table dp.qrtz_simprop_triggers
  (
    sched_name varchar(120) not null,
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    str_prop_1 varchar(512) null,
    str_prop_2 varchar(512) null,
    str_prop_3 varchar(512) null,
    int_prop_1 int null,
    int_prop_2 int null,
    long_prop_1 bigint null,
    long_prop_2 bigint null,
    dec_prop_1 numeric(13,4) null,
    dec_prop_2 numeric(13,4) null,
    bool_prop_1 varchar(1) null,
    bool_prop_2 varchar(1) null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
    references dp.qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table dp.qrtz_blob_triggers
  (
    sched_name varchar(120) not null,
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    blob_data blob null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
        references dp.qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table dp.qrtz_calendars
  (
    sched_name varchar(120) not null,
    calendar_name  varchar(200) not null,
    calendar blob not null,
    primary key (sched_name,calendar_name)
);

create table dp.qrtz_paused_trigger_grps
  (
    sched_name varchar(120) not null,
    trigger_group  varchar(200) not null,
    primary key (sched_name,trigger_group)
);

create table dp.qrtz_fired_triggers
  (
    sched_name varchar(120) not null,
    entry_id varchar(95) not null,
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    instance_name varchar(200) not null,
    fired_time bigint(13) not null,
    sched_time bigint(13) not null,
    priority integer not null,
    state varchar(16) not null,
    job_name varchar(200) null,
    job_group varchar(200) null,
    is_nonconcurrent varchar(1) null,
    requests_recovery varchar(1) null,
    primary key (sched_name,entry_id)
);

create table dp.qrtz_scheduler_state
  (
    sched_name varchar(120) not null,
    instance_name varchar(200) not null,
    last_checkin_time bigint(13) not null,
    checkin_interval bigint(13) not null,
    primary key (sched_name,instance_name)
);

create table dp.qrtz_locks
  (
    sched_name varchar(120) not null,
    lock_name  varchar(40) not null,
    primary key (sched_name,lock_name)
);



create table if not exists dp.mailtemplate (
  n_id bigint primary key auto_increment not null comment 'id εγγραφής',
  v_code varchar(40) not null comment 'Κωδικός προτύπου',
  v_subject varchar(255) not null comment 'Θέμα μηνύματος (template)',
  v_body varchar(1000) not null comment 'Σώμα μηνύματος (template)',
  v_renderer varchar(20) not null comment 'Αναγνωριστικό του template engine που χρησιμοποιείται για το rendering αυτού του προτύπου',
  n_html tinyint(1) not null default 1 comment 'Ένδειξη για τα μηνύματα που παράγονται από αυτό το πρότυπο είναι html ή plain text'
);


create table dp.scheduled_job
(
  n_id bigint primary key auto_increment not null comment 'id εγγραφής',
  v_jobname varchar(100) not null comment 'Αναγνωριστικό όνομα εργασίας',
  v_jobgroup varchar(100) not null comment 'Αναγνωριστικό ομάδας εργασιών',
  v_description varchar(250) null comment 'Περιγραφή',
  v_scheduledby varchar(100) not null comment 'Χρήστης προγραμματισμού εργασίας',
  dt_scheduledate timestamp null comment 'Ημερομηνία καταχώρησης εργασίας',
  dt_firedate timestamp null comment 'Ημερομηνία προγραμματισμού εργασίας',
  v_status varchar(50) null comment 'Κατάσταση',
  dt_startdate timestamp null comment 'Ημερομηνία έναρξης',
  dt_enddate timestamp null comment 'Ημερομηνία λήξης',
  v_errorid varchar(100) null comment 'Αναγνωριστικό σφάλματος',
  v_errormessage varchar(250) null comment 'Περιγραφή σφάλματος'
) ENGINE = INNODB comment 'Χρονοπρογραμματισμένες εργασίες';


ALTER TABLE dp.scheduled_job ADD INDEX sched_jobname (v_jobname ASC);
ALTER TABLE dp.scheduled_job ADD INDEX sched_scheduledby (v_scheduledby ASC);
ALTER TABLE dp.scheduled_job ADD INDEX sched_status (v_status ASC);

ALTER TABLE dp.scheduled_job ADD v_fromjobname varchar(100) null comment 'Αναγνωριστικό Όνομα Σχετικής Εργασίας';

