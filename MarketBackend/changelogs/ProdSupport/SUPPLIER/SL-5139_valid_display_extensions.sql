ALTER TABLE lu_document_formats_allowed
ADD display_extension varchar(10);

update lu_document_formats_allowed set display_extension='.doc' where format='application/msword';
update lu_document_formats_allowed set display_extension='.pdf' where format='application/pdf';
update lu_document_formats_allowed set display_extension='.zip' where format='application/zip';
update lu_document_formats_allowed set display_extension='.gif' where format='image/gif';
update lu_document_formats_allowed set display_extension='.jpeg' where format='image/jpeg';
update lu_document_formats_allowed set display_extension='.jpg' where format='image/jpg';
update lu_document_formats_allowed set display_extension='.pjpeg' where format='image/pjpeg';
update lu_document_formats_allowed set display_extension='.txt' where format='text/plain';
update lu_document_formats_allowed set display_extension='.xml' where format='text/xml';
