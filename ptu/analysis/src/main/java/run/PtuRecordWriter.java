package run;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * RecordWriter对象
 * @author zw
 *
 */
public class PtuRecordWriter extends RecordWriter<ResultPojo, NullWritable> {


    private Connection conn ;
    private String sql = "";
    public PtuRecordWriter(){
        conn = DBUtils.getConn();
        sql = "insert into calllog(telnum,year,month,day,telcount,telcallingcount,"
                + "telcalledcount,telsum,telcallingsum,telcalledsum) values (?,?,?,?,?,?,?,?,?,?)";
    }
    @Override
    public void write(ResultPojo key, NullWritable value) throws IOException, InterruptedException {
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, key.getTelnum());
            stmt.setInt(2, key.getYear());
            stmt.setInt(3, key.getMonth());
            stmt.setInt(4, key.getDay());
            stmt.setInt(5, key.getTelcount());
            stmt.setInt(6, key.getTelcallingcount());
            stmt.setInt(7, key.getTelcalledcount());
            stmt.setInt(8, key.getTelsum());
            stmt.setInt(9, key.getTelcallingsum());
            stmt.setInt(10, key.getTelcalledsum());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
