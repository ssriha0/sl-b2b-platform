################################################
# Name : solrReport.pl
# Perl script to generate index from solr logs.
# Version : 0.1
################################################

my $searchSolrPath="/workspace/tools/solr-tomcat"; ##point to destination folder which will contains log files in tmp sub-folder

my $workingDate=getDate(1);
#my $inFile = $searchSolrPath . "/apache-tomcat-5.5.25/logs/catalina." . $workingDate . ".log";
 
$handler='dismax';
#$handler='skilltree';
$numArgs = $#ARGV + 1;
 

if ($numArgs == 0) {
  print "Please provide file name\n";
  exit;
}

 

my $inFile = $ARGV[0];
my $tmpFile = "/tmp/solr.tmp";
my $tmpFileB2C = "/tmp/b2c.tmp";
my $outFile = "/tmp/solr_out.csv";

 

### Get Counts ###

sub getVal {

  my $str = $_[0];

  @val=split(/\}/, $str);
  @tmp=split(/ /, $val[1]);
  @hitsa=split(/=/, $tmp[1]);
  $hits= $hitsa[1];

  #print "STR: $str \n";
  #print "val : @val \n";
  #print "Tmp : $tmp[1]";
  #print "\nR. " .  $hits . "\n";
 

  @tmp=split(/&/, $val[0]);
  @arr = split(/=/, $tmp[3]);
  $q=$arr[1];

  $q =~ s/\+/ /g;
  $q =~ s/,/ /g;
  $q = lc($q);
  my $dt = $workingDate  ."T00:00:00Z";
  $ss = $q . "," . $hits . "," . $dt . ",report\n";
  return $ss;
}

 
sub getValB2C {
  my $str = $_[0];
  print "STR: $str \n";
  #return "None";
  @val=split(/\t/, $str);
  @tmp=split(/ /, $val[1]);
  @hitsa=split(/=/, $tmp[1]);
  $hits= '1';

  #print "STR: $str \n";
  #print "val : @val \n";
  #print "Tmp : $val[2] $val[3] $va[4] $val[6]";
  #print "\nR. " .  $hits . "\n";

  $q=$val[4];
  chomp $q;
  $q=trim($q);
  #print "V1:$q\n";

  $q =~ s/\+/ /g;
  $q =~ s/,/ /g;
  $q = lc($q);
  my $dt = $workingDate  ."T00:00:00Z";
  $ss = $q . "," . $hits . "," . $dt . ",b2creport\n";
  #print "VV:$ss\n";
  return $ss;
}

sub getDate {

 my $backCount = $_[0];
 @mytime=localtime (time - 86400*$backCount);
 $d = $mytime[3];
 if (length($d) == 1) {
  $d = "0".$d;
 }

 $ttime=(1900 + $mytime[5]) . "-" . (1 + $mytime[4]) . "-" . $d;
 print " Time:" . $ttime . "\n";
 return $ttime;
}

 

sub main {

  #`cat $inFile |grep "qt=skilltree"|grep "wt=javabin"|cut -d{ -f2 > $tmpFile`;
  `cat $inFile |grep -e "qt=$handler" -e "qt=skilltree"|grep "wt=javabin"|cut -d{ -f2 > $tmpFile`;

  open FILE, $tmpFile or die $!;
  open OUTFILE, ">$outFile" or die $!;
  print OUTFILE "id,keyword,hits,logdate,doctype\n";

  my $i=1;

  while(<FILE>) {
    $val=getVal($_);
    $tt=time . $i;
    $i++;
    print OUTFILE "$tt,$val";
  }

  close FILE;
  if (-r $tmpFileB2C) {
    open FILE, $tmpFileB2C or die $!;
    my $k = 0;
    while(<FILE>) {
      $k++;
      if ($k == 1) {
        next;
      }
      $val=getValB2C($_);
      $tt=time . $i;
      $i++;
      print OUTFILE "$tt,$val";
    }
  }
  close FILE; 
  close OUTFILE;
}


sub trim{
   my $string = shift;
   $string =~ s/^\s+|\s+$//g;
   return $string;
}

main;