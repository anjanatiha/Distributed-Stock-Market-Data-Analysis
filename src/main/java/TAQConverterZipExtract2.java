/**
 * Created by Anjana on 5/29/2017.
 */
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TAQConverterZipExtract2 {
    private ZipFile zf;
    private PrintStream outputStream;
    private File outputFile;
    private IFieldType[] fieldType;
    private int startOffset;
    private String startTime=null, endTime=null;
    private BufferedReader br;

    TAQConverterZipExtract2(String zipfile, String outputFileName, IFieldType[] fieldType, String startTime, String endTime, int startOffset){
        try {
            setMainObject(zipfile, outputFileName, fieldType, startOffset);
            setTime(startTime, endTime);
            convertFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    TAQConverterZipExtract2(String zipfile, String outputFileName, IFieldType[] fieldType, int startOffset){
        try {
            setMainObject(zipfile, outputFileName, fieldType, startOffset);
            convertFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setMainObject(String zipfile, String outputFileName,IFieldType[] fieldType, int startOffset){
        try {
            this.zf = new ZipFile(zipfile);
            this.outputFile = new File(outputFileName);
            this.outputStream = new PrintStream(outputFile);
            this.fieldType = fieldType;
            this.startOffset=startOffset;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setTime(String startTime, String endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void convertFile() throws IOException {
        try {
            int start=0;
            int inRange = -1;
            StringBuilder strBuilder = new StringBuilder();
            Enumeration entries = zf.entries();
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            while (entries.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                long size = ze.getSize();
                if (size > 0) {
                    System.out.println("Length is " + size);
                    br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
                    String line;
                    int time;
                    br.readLine();
                    line = br.readLine();
                    while(line!= null) {
                        System.out.println(line);
                        StringBuilder tempLine = new StringBuilder();
                        for (int i = 0; i < fieldType.length-1; i++) {
                            String tempStr = fieldType[i].convertFromBinary(line, start);
                            if(startTime!=null & endTime!=null){
                                if(i==0){
                                    time = Integer.parseInt(tempStr);
                                    if((time>=Integer.parseInt(startTime)) && (time<=Integer.parseInt(endTime)))
                                        inRange = 1;
                                    else if(time>Integer.parseInt(endTime)) {
                                        inRange = 0;
                                        break;
                                    }
                                    else if((time<=Integer.parseInt(startTime)) && (time<=Integer.parseInt(endTime)))
                                        inRange = -1;
                                    break;
                                }
                            }
                            tempLine.append(tempStr);
                            start = start + fieldType[i].getLength();

                            if (i < fieldType.length - 1){
                                tempLine.append(',');
                            }
                            if (i == fieldType.length - 2){
                                tempLine.append('.');
                                tempLine.append("\r\n");
                            }
                        }
                        if(startTime!=null & endTime!=null) {
                            if (inRange == 1) {
                                outputStream.print(tempLine);
                                outputStream.flush();
                                tempLine.setLength(0);
                                line = br.readLine();
                                start = 0;
                            }
                            else if (inRange == -1) {
                                continue;
                            }
                            else if (inRange==0)
                                break;
                        }
                        else {
                            outputStream.print(tempLine);
                            outputStream.flush();
                            tempLine.setLength(0);
                            line = br.readLine();
                            start = 0;
                        }
                    }
                }
            }
            closeStream();
        }catch (Exception e){
            System.out.println("Error: "+e);
            closeStream();
        }
    }

    private void closeStream() throws IOException {
        br.close();
        zf.close();
        outputStream.close();
    }
}
