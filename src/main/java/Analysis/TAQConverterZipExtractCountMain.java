package Analysis;

import DataFieldType.TAQJune2015Spec;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TAQConverterZipExtractCountMain {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
        String formattedDate = dateFormat.format(new Date()).toString();
        System.out.println(formattedDate);

        int start = 1;
        TAQJune2015Spec taqFieldObject = new TAQJune2015Spec();
        TAQConverterZipExtractCount taqFileObject = new TAQConverterZipExtractCount(args[0], args[1]);
        int recordLength = taqFieldObject.getNBBOFieldsLength();
        long bufferSize = 1024 * recordLength;
        taqFileObject.setAttributes(start, taqFieldObject.getNBBOFields(), bufferSize);
        taqFileObject.convertFile();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        int seconds = (int) ((totalTime / 1000) % 60);
        int minutes = (int) ((totalTime / 1000) / 60);
        System.out.println("\n\nTotal Time: " + minutes + ":" + seconds);

        dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
        formattedDate = dateFormat.format(new Date()).toString();
        System.out.println(formattedDate);
    }
}