insert into `QRTZ_JOB_DETAILS` (`JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `JOB_CLASS_NAME`, `IS_DURABLE`, `IS_VOLATILE`, `IS_STATEFUL`, `REQUESTS_RECOVERY`, `JOB_DATA`) 
values('PLUS_ONE_FEED','GRP_PLUSONE','Calls PlusOne FEED','com.newco.marketplace.util.scheduler.BackgroundCheckProcessScheduler','Y','N','N','N',NULL);

insert into `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`, `JOB_NAME`, `JOB_GROUP`, `IS_VOLATILE`, `DESCRIPTION`, `NEXT_FIRE_TIME`, `PREV_FIRE_TIME`, `PRIORITY`, `TRIGGER_STATE`, `TRIGGER_TYPE`, `START_TIME`, `END_TIME`, `CALENDAR_NAME`, `MISFIRE_INSTR`, `JOB_DATA`) 
values('TRG_PLUS_ONE_FEED','TRG_PLUSONE','PLUS_ONE_FEED','GRP_PLUSONE','0','Plus One Feed Trigger','-1','-1','0','PAUSED','CRON','0','0',NULL,'2',NULL);

insert into `QRTZ_CRON_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`, `CRON_EXPRESSION`, `TIME_ZONE_ID`) 
values('TRG_PLUS_ONE_FEED','TRG_PLUSONE','0 0 2 * * ?','America/Chicago');