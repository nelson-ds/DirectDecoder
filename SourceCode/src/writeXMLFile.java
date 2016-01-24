
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nelson.dsouza
 */
public class writeXMLFile {

    List fp_list = new ArrayList();
    List dt_list = new ArrayList();
    List nl_list = new ArrayList();
    List dc_list = new ArrayList();
    List off_list = new ArrayList();
    List len_list = new ArrayList();
    String version = "1";
    String softwareVersion = "1.0.14822.0";
    String decoderName = "Direct_Decoder_test";
    String decoder;
    String recordType;
    int id = 0;
    int length = 0;
    char[] filePathCharUsed;
    File outputfile;
    FileWriter outputtext;
    boolean Continue = false;

    public void writeXML(JTextPane output, JButton generate, Frame frame, String filePath,
            java.util.List fp_list, java.util.List dt_list, java.util.List nl_list, java.util.List dc_list,
            java.util.List off_list, java.util.List len_list, String decoderType) {

        try {

            if (decoderType.startsWith("ASCII")) {
                decoder = "\"ascii\"";
                recordType = "\"CDR\"";
            } else {
                decoder = "\"binary\"";
                recordType = "\"Block\"";
            }
            if (decoderType.endsWith("Fixed")) {
                for (int i = 0; i < len_list.size(); i++) {
                    length = length + Integer.parseInt(len_list.get(i).toString().substring(0, len_list.get(i).toString().length() - 2));
                }
            }

            this.fp_list = fp_list;
            this.dt_list = dt_list;
            this.nl_list = nl_list;
            this.off_list = off_list;
            this.len_list = len_list;

            char[] filePathChar = filePath.toCharArray();
            String filePathUsed;

            if (filePath.endsWith(".xls") || filePath.endsWith(".ods")) {
                filePathCharUsed = new char[filePath.length() - 3];
                for (int i = 0; i < filePath.length() - 3; i++) {
                    filePathCharUsed[i] = filePathChar[i];
                }
            } else {
                filePathCharUsed = new char[filePath.length() - 4];
                for (int i = 0; i < filePath.length() - 4; i++) {
                    filePathCharUsed[i] = filePathChar[i];
                }
            }
            filePathUsed = new String(filePathCharUsed);
            filePathUsed = filePathUsed + "xml";

            String filename = File.separator;
            JFileChooser fc = new JFileChooser(filename);
            int actionDialog = fc.showSaveDialog(null);

            if (actionDialog == JFileChooser.APPROVE_OPTION) {
                outputfile = new File(fc.getSelectedFile() + ".xml");
                if (outputfile.exists()) {
                    actionDialog = JOptionPane.showConfirmDialog(null, "Replace existing file?");
                    while (actionDialog == JOptionPane.NO_OPTION) {
                        actionDialog = fc.showSaveDialog(null);
                        if (actionDialog == JFileChooser.APPROVE_OPTION) {
                            outputfile = new File(fc.getSelectedFile() + ".xml");
                            if (outputfile.exists()) {
                                actionDialog = JOptionPane.showConfirmDialog(null, "Replace existing file?");
                            }
                        }
                    }
                } else {
                    Continue = true;
                }
            }

            if (actionDialog == JFileChooser.APPROVE_OPTION) {
                outputfile.delete();
                outputfile = new File(fc.getSelectedFile() + ".xml");
                Continue = true;
            }

            if (Continue) {
                outputtext = new FileWriter(outputfile, true);
                outputtext.write("<?xml version=\"1.0\"?>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<Diamond application=\"spark\" version=\"" + version + "\" softwareVersion=\"" + softwareVersion + "\">");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<Decoder name=\"" + decoderName + "\" type=" + decoder + ">");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<RecordType root=\"true\" name=\"Root\" type=\"none\">");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<Logic type=\"sequenceOf\">");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<Logic recordType=" + recordType + " type=\"recordReference\"/>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("</Logic>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("</RecordType>");
                outputtext.write(System.getProperty("line.separator"));
                if (decoderType.startsWith("ASCII Delimited")) {
                    outputtext.write("<RecordType parseQuotes=\"false\" delimiter=\"comma\" generate=\"record\" allowEOFTerm=\"false\" terminator=\"crlf\" root=\"false\" name=\"CDR\" type=\"delimitedascii\" parseDoubleQuotes=\"false\">");
                    outputtext.write(System.getProperty("line.separator"));
                    for (int i = 0; i < dc_list.size(); i++) {
                        if (dc_list.get(i).equals("-") || dc_list.get(i).equals("N/A")) {
                            continue;
                        } else {
                            outputtext.write("<Field trimQuotes=\"true\" generate=\"field\" name=\"" + dc_list.get(i) + "\" encoding=\"windows-1252\" trimWhiteSpaceInsideQuotes=\"true\" type=\"string\" trimWhiteSpace=\"true\" id=\"" + id + "\"/>");
                            outputtext.write(System.getProperty("line.separator"));
                            id += id;
                        }
                    }
                } else if (decoderType.startsWith("ASCII Fixed")) {
                    outputtext.write("<RecordType generate=\"record\" root=\"false\" name=\"CDR\" length=\"" + length + "\" type=\"fixedlengthascii\">");
                    outputtext.write(System.getProperty("line.separator"));
                    for (int i = 0; i < dc_list.size(); i++) {
                        if (dc_list.get(i).equals("-") || dc_list.get(i).equals("N/A")) {
                            continue;
                        } else {
                            outputtext.write("<Field trimQuotes=\"true\" generate=\"field\" name=\"" + dc_list.get(i) + "\" length=\"" + len_list.get(i) + "\" encoding=\"windows-1252\" trimWhiteSpaceInsideQuotes=\"true\" offset=\"" + off_list.get(i) + "\" type=\"string\" trimWhiteSpace=\"true\" id=\"" + id + "\"/>");
                            outputtext.write(System.getProperty("line.separator"));
                            id += id;
                        }
                    }
                } else {
                    outputtext.write("<RecordType root=\"false\" name=\"Block\" type=\"block\">");
                    outputtext.write("<Logic type=\"sequenceOf\">");
                    outputtext.write("<Logic recordType=\"CDR\" type=\"recordReference\"/>");
                    outputtext.write("</Logic>");
                    outputtext.write("</RecordType>");
                    outputtext.write("<RecordType generate=\"record\" root=\"false\" name=\"CDR\" length=\"" + length + "\" type=\"fixedlengthbinary\">");
                    outputtext.write(System.getProperty("line.separator"));
                    for (int i = 0; i < dc_list.size(); i++) {
                        if (dc_list.get(i).equals("-") || dc_list.get(i).equals("N/A")) {
                            continue;
                        } else {
                            outputtext.write("<Field postByteSwap=\"false\" nibbleSwap=\"false\" generate=\"field\" name=\"" + dc_list.get(i) + "\" length=\"" + len_list.get(i) + "\" byteReversed=\"false\" offset=\"" + off_list.get(i) + "\" preByteSwap=\"false\" type=\"hex\" id=\"" + id + "\"/>");
                            outputtext.write(System.getProperty("line.separator"));
                            id += id;
                        }
                    }
                }
                outputtext.write("</RecordType>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("</Decoder>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<Mappings>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<Nodes/>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<DynamicFunctions/>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<RecordTypes>");
                outputtext.write(System.getProperty("line.separator"));

                outputtext.write("<RecordType name=\"Fingerprint\">");
                outputtext.write(System.getProperty("line.separator"));
                for (int i = 0; i < fp_list.size(); i++) {
                    if (fp_list.get(i).equals("-") || fp_list.get(i).equals("N/A")) {
                        continue;
                    } else {
                        outputtext.write("<Field name=\"" + fp_list.get(i) + "\" nullable=\"" + nl_list.get(i) + "\" type=\"" + dt_list.get(i) + "\" id=\"" + i + "\"/> ");
                        outputtext.write(System.getProperty("line.separator"));
                    }
                }

                outputtext.write("</RecordType>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("</RecordTypes>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("<Globals/>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("</Mappings>");
                outputtext.write(System.getProperty("line.separator"));
                outputtext.write("</Diamond>");
                outputtext.write(System.getProperty("line.separator"));

                outputtext.close();

                String completionMessage = "<html><body><br><font style=Arial color=green color=green size=5>Saved the "
                        + "xml file at location:-<br> <font style=Arial color=green color=red size=4>"
                        + "<b><font color=purple>" + fc.getSelectedFile() + ".xml</b>"
                        + "<br><br><font color=green> To generate another xml file choose a new mapping sheet";
                System.out.println("Saved file at location :- " + fc.getSelectedFile() + ".xml");
                output.setText(completionMessage);
                generate.setEnabled(false);

                buildFrame bf = new buildFrame();
                bf.decoderFieldYes.setEnabled(false);
                bf.decoderFieldNo.setEnabled(false);
                bf.mappingFieldYes.setEnabled(false);
                bf.mappingFieldNo.setEnabled(false);
                bf.decoderList.setEnabled(false);
                bf.decoderInputList.setEnabled(false);
                bf.decoderFieldYes.setSelected(false);
                bf.decoderFieldNo.setSelected(true);
                bf.mappingFieldYes.setSelected(false);
                bf.mappingFieldNo.setSelected(true);
                bf.decoderList.setSelectedIndex(0);
                bf.decoderInputList.setSelectedIndex(0);
                frame.setTitle("Direct Decoder");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
