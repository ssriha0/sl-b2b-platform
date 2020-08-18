import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GetResourceCoverageAreaForExistingResources {
	//private static final Logger logger = Logger.getLogger(GetResourceCoverageAreaForExistingResources.class);

	private static final int MYTHREADS = 50;
	private static int LIMITVALUE;
	private static String db_name = "supplier_prod";
	private static String db_user = "supply_usr";
	private static String db_pass = "supply";
	
	
	public static void main(String[] args) {
		String db_host=args[0];
		String db_port="3372";
		if(args.length>1)
			db_port=args[1];
		Integer offset=0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			StringBuffer buffer=new StringBuffer();
			buffer.append("jdbc:mysql://");
			buffer.append(db_host);
			buffer.append(":");
			buffer.append(db_port);
			buffer.append("/");
			buffer.append(db_name);
			Connection con = DriverManager.getConnection(buffer.toString(), db_user, db_pass);
			System.out.println(db_host);
			if (!con.isClosed()) 
				System.out.println("Successfully connected to MySQL server using TCP/IP...");
			StringBuffer querry=new StringBuffer();
			querry.append("SELECT count(vr.resource_id) FROM vendor_resource vr JOIN location loc ON vr.locn_id = loc.locn_id ");
			querry.append("JOIN lu_service_area_radius lsar ON lsar.id=vr.service_area_radius_id WHERE ");
			querry.append("vr.resource_id NOT IN(SELECT resource_id FROM vendor_resource_coverage) ");
			
			PreparedStatement pstmt = con.prepareStatement(querry.toString());
			ResultSet rs  = pstmt.executeQuery();
			
			while(rs.next()){
				int totalData=rs.getInt(1);
				LIMITVALUE=(int)Math.ceil((double)totalData/(double)MYTHREADS);
				System.out.println(totalData);
				System.out.println(LIMITVALUE);
			}
			rs.close();
			pstmt.close();
			con.close();
			ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
			long totalStartTime=Calendar.getInstance().getTimeInMillis();
			PrintStream fileOut=null;
			fileOut = new PrintStream("./log.txt");
			System.setOut(fileOut);
			for (int i = 1; i <= MYTHREADS; i++) {
				//executor.
				System.out.println("Thread-->"+i);
				ResourceThreadExecution resourceThread = new ResourceThreadExecution(db_host, db_port, offset, i, LIMITVALUE);
				executor.execute(resourceThread);
				offset=offset+LIMITVALUE;
				
			}
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		
			long endTime=Calendar.getInstance().getTimeInMillis();
			System.out.println(System.currentTimeMillis()+"==>Total Time Taken for completing execution " +(endTime-totalStartTime) + " ms.");
			System.out.println("Finished all threads");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (InterruptedException e) {
			
			e.getMessage();
		}
		
		
		
	}

	

}
