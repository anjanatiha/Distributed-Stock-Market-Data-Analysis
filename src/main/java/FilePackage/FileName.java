package FilePackage;

import java.io.File;

import static Misc.Print.print;

public class FileName {
    public static String getInputFileName(String inputFileName) {
        File inputFile = new File(inputFileName);
        String inputFileNameOnly = inputFile.getName();
        return inputFileNameOnly;
    }
    public static String getFileTypeRemved(String inputFileName) {
        String inputFileNameOnly = inputFileName.substring(0,inputFileName.length()-4);
        return inputFileNameOnly;
    }
    public static String getOutputFileName(String inputFileName) {
        String outputFileName;
        if (getInputFileType(inputFileName).equals("zip"))
            outputFileName = getFileTypeRemved(inputFileName)+"_Unzipped";
        else
            outputFileName = inputFileName + "_Converted";
        return outputFileName;

    }
    public static String getUnzippedFileName(String inputFileName) {
        String unzippedFileName="";
        if (getInputFileType(inputFileName).equals("zip"))
            unzippedFileName = getFileTypeRemved(inputFileName)+"_Unzipped";
        else {
            print("Already Unzipped");
        }
        return unzippedFileName;

    }
    public static String getConvertedFileName(String inputFileName) {
        String outputFileName="";
        if (getInputFileType(inputFileName).equals("zip"))
            outputFileName = getFileTypeRemved(inputFileName)+ "_Converted";
        return outputFileName;

    }
    public static String getFolder(String inputFileName, String subStr) {
        File file = new File(inputFileName);
        File parentDir = file.getParentFile(); // to get the parent dir
        String parentDirName = file.getParent();
        return parentDirName;
    }
    public static String getOutputFileName(String inputFileName, String subStr) {
        String outputFileName;
        if (inputFileName.substring(inputFileName.length() - 3, inputFileName.length()).equals("zip")) {

            outputFileName = inputFileName.substring(0, inputFileName.length() - 4) + "_unzipped";
        } else {
            outputFileName = inputFileName + "_"+subStr;
        }
        return outputFileName;
    }

    public static String getInputFileType(String inputFileName) {
        print(inputFileName);
        if (inputFileName.substring(inputFileName.length() - 3, inputFileName.length()).equals("zip")) {
            return "zip";
        } else
            return "txt";
    }
}