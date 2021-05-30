package run;

import java.io.IOException;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PtuOutputFormat extends FileOutputFormat<ResultPojo, NullWritable> {
    @Override
    public RecordWriter<ResultPojo, NullWritable>
    getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        return new PtuRecordWriter();
    }

}
