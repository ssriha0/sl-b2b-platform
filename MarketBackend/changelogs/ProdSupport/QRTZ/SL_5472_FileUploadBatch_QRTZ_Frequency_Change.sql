update QRTZ_CRON_TRIGGERS
	set cron_expression = '0 0/5 * * * ?'
	where trigger_name = 'TRG_BUYER_UPLOAD';

