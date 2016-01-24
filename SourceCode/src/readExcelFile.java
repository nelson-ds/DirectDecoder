/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nelson.dsouza
 */
import java.awt.Frame;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readExcelFile {

    List fp_list = new ArrayList();
    List dt_list = new ArrayList();
    List nl_list = new ArrayList();
    List dc_list = new ArrayList();
    List off_list = new ArrayList();
    List len_list = new ArrayList();
    String decoder_value;
    String fingerprint_value;
    String fp_value;
    String fp_copy_value;
    String dt_value;
    String nl_value;
    String dc_value;
    String off_value;
    String len_value;
    boolean fp_col_found = false;
    boolean dt_col_found = false;
    boolean nl_col_found = false;
    boolean dc_col_found = false;
    boolean off_col_found = false;
    boolean len_col_found = false;
    boolean all_found = false;
    boolean Continue = true;
    boolean createDecoderField = false;
    boolean createMappingField = false;
    boolean parseNext = true;
    int fp_col_num;
    int dt_col_num;
    int nl_col_num;
    int dc_col_num;
    int off_col_num;
    int len_col_num;
    int row_num1 = 0;
    int row_num2 = 0;

    public void readExcel(String decoderType, String decoderInput, boolean createMappingField, boolean createDecoderField, JTextPane output, JButton generate, Frame frame, String filePath) {

        try {

            String[] unwantedCharacters = {";", "&", "^", "%", "$", "#", "@", "!", ":", "-"};

            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workBook;
            //POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
            if (filePath.endsWith("xlsx")) {
                workBook = new XSSFWorkbook(fileInputStream);
            } else {
                workBook = new HSSFWorkbook(fileInputStream);
            }
            Sheet Sheet = workBook.getSheetAt(0);

            Iterator rowIterator1 = Sheet.rowIterator();

            while (rowIterator1.hasNext() && all_found == false) {

                if (dt_col_found == false
                        || nl_col_found == false
                        || (createMappingField == true && fp_col_found == false)
                        || (createDecoderField == true && dc_col_found == false)) {

                    Row Row = (Row) rowIterator1.next();
                    Iterator cellIterator = Row.cellIterator();

                    while (cellIterator.hasNext()) {

                        Cell Cell = (Cell) cellIterator.next();
                        if ((createMappingField == true || decoderInput.equals("Same as Mapping")) && fp_col_found == false) {
                            if (Cell.toString().contains("Mapping Field")) {
                                fp_col_found = true;
                                fp_col_num = Cell.getColumnIndex();
                            }
                        }
                        if (Cell.toString().contains("Data Type")) {
                            dt_col_found = true;
                            dt_col_num = Cell.getColumnIndex();
                        }
                        if (Cell.toString().contains("Nullable")) {
                            nl_col_found = true;
                            nl_col_num = Cell.getColumnIndex();
                        }
                        if (createDecoderField == true && dc_col_found == false) {
                            if (Cell.toString().contains("Decoder Field")) {
                                dc_col_found = true;
                                dc_col_num = Cell.getColumnIndex();
                            }
                        }

                        if (createDecoderField && decoderType.endsWith("Fixed")) {
                            if (Cell.toString().contains("Offset")) {
                                off_col_found = true;
                                off_col_num = Cell.getColumnIndex();
                            }
                        }

                        if (createDecoderField && decoderType.endsWith("Fixed")) {
                            if (Cell.toString().contains("Length")) {
                                len_col_found = true;
                                len_col_num = Cell.getColumnIndex();
                            }
                        }


                        if (dt_col_found == true
                                && nl_col_found == true
                                && ((createMappingField == true && fp_col_found == true) || createMappingField == false)
                                && (((createDecoderField == true) && dc_col_found == true) || (decoderInput.equals("Same as Mapping") && fp_col_found == true) || createDecoderField == false)
                                && ((createDecoderField == true && decoderType.endsWith("Fixed") && off_col_found == true && len_col_found == true)
                                || (createDecoderField == true && !decoderType.endsWith("Fixed"))
                                || createDecoderField == false)) {
                            all_found = true;
                        }
                        //System.out.println("dt " + dt_col_found + "\nnl " + nl_col_found + "\nfp " + fp_col_found + "\noff " + off_col_found + "\nlen " + len_col_found +"\n all found" + all_found);
                    }
                }
                row_num1++;
            }

            row_num1 += 1;

            if (dt_col_found == false) {
                Continue = false;
                JOptionPane.showMessageDialog(frame, "Mapping sheet does not have a column named \"Data Type\" \n"
                        + "Choose a mapping sheet having this column !!");
            }

            if (nl_col_found == false) {
                Continue = false;
                JOptionPane.showMessageDialog(frame, "Mapping sheet does not have a column named \"Nullable\" \n"
                        + "Choose a mapping sheet having this column !!");
            }

            if (createDecoderField == true && !decoderInput.equals("Same as Mapping") && dc_col_found == false) {
                Continue = false;
                JOptionPane.showMessageDialog(frame, "Mapping sheet does not have a column named \"Decoder Field\" \n"
                        + "Choose a mapping sheet having this column !!");
            }

            if ((createDecoderField == true && decoderInput.equals("Same as Mapping") && fp_col_found == false)
                    || (createMappingField == true && fp_col_found == false)) {
                Continue = false;
                JOptionPane.showMessageDialog(frame, "Mapping sheet does not have a column named \"Mapping Field\" \n"
                        + "Choose a mapping sheet having this column !!");
            }

            if (createDecoderField == true && decoderType.endsWith("Fixed") && off_col_found == false) {
                Continue = false;
                JOptionPane.showMessageDialog(frame, "Mapping sheet does not have a column named \"Offset\" \n"
                        + "Choose a mapping sheet having this column !!");
            }

            if (createDecoderField == true && decoderType.endsWith("Fixed") && len_col_found == false) {
                Continue = false;
                JOptionPane.showMessageDialog(frame, "Mapping sheet does not have a column named \"Length\" \n"
                        + "Choose a mapping sheet having this column !!");
            }



            if (Continue) {
                Iterator rowIterator2 = Sheet.rowIterator();
                while (rowIterator2.hasNext() && Continue) {
                    row_num2 += 1;
                    if (row_num2 < row_num1) {
                        Row Row = (Row) rowIterator2.next();
                        continue;
                    } else {
                        Row Row = (Row) rowIterator2.next();


                        Cell dt_cell = (Cell) Row.getCell(dt_col_num, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                        if (dt_cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                            dt_value = "string";

                        } else {
                            dt_value = dt_cell.toString();
                            dt_value = dt_value.toLowerCase();
                        }
                        if (dt_value.equals("long")) {
                            dt_value = "integer";
                        }
                        if (!dt_value.equals("boolean") && !dt_value.equals("decimal") && !dt_value.equals("datetime")
                                && !dt_value.equals("integer") && !dt_value.equals("string")) {
                            dt_value = "string";
                        }
                        dt_list.add(dt_value);



                        Cell nl_cell = (Cell) Row.getCell(nl_col_num, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                        if (nl_cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                            nl_value = "false";
                            nl_list.add("false");
                        } else {
                            nl_value = nl_cell.toString();
                            if (nl_value.equalsIgnoreCase("y") || nl_value.equalsIgnoreCase("yes") || nl_value.equalsIgnoreCase("true")) {
                                nl_list.add("true");
                            } else {
                                nl_list.add("false");
                            }
                        }




                        if (createMappingField == false) {
                            fp_list.add("N/A");
                        } else {
                            Cell fp_cell = (Cell) Row.getCell(fp_col_num, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                            if (fp_cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                                fp_value = "-";
                            } else {
                                fp_value = fp_cell.toString();
                            }
                            fp_value = fp_value.replaceAll(" ", "_");
                            if (fp_value.endsWith("_")) {
                                fp_value = fp_value.substring(0, fp_value.length() - 1);
                            }
                            fp_list.add(fp_value);
                        }


                        if (createDecoderField == false) {
                            dc_list.add("N/A");
                        } else {
                            if (decoderInput.equalsIgnoreCase("Y/N Values")) {
                                Cell dc_cell = (Cell) Row.getCell(dc_col_num, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                                if (dc_cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                                    dc_value = "N";

                                } else {
                                    dc_value = dc_cell.toString();
                                }
                                if (dc_value.equalsIgnoreCase("Y")) {
                                    dc_list.add(fp_value);
                                } else {
                                    dc_list.add("-");
                                }
                            }

                            if (decoderInput.equalsIgnoreCase("Distinct Values")) {
                                Cell dc_cell = (Cell) Row.getCell(dc_col_num, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                                if (dc_cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                                    dc_value = "-";
                                    parseNext = false;
                                } else {
                                    parseNext = true;
                                    dc_value = dc_cell.toString();
                                    for (int i = 0; i < unwantedCharacters.length && parseNext; i++) {
                                        dc_value = dc_value.replaceAll(unwantedCharacters[i], "");
                                    }
                                    dc_value = dc_value.replaceAll(" ", "_");
                                    if (dc_value.endsWith("_")) {
                                        dc_value = dc_value.substring(0, dc_value.length() - 1);
                                    }
                                    dc_list.add(dc_value);
                                }
                            }


                            if (decoderInput.equalsIgnoreCase("Same as Mapping")) {
                                Cell fp_copy_cell = (Cell) Row.getCell(fp_col_num, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                                if (fp_copy_cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                                    fp_copy_value = "-";
                                    parseNext = false;

                                } else {
                                    parseNext = true;
                                    fp_copy_value = fp_copy_cell.toString();
                                }
                                for (int i = 0; i < unwantedCharacters.length && parseNext; i++) {
                                    fp_copy_value = fp_copy_value.replaceAll(unwantedCharacters[i], "");
                                }
                                fp_copy_value = fp_copy_value.replaceAll(" ", "_");
                                if (fp_copy_value.endsWith("_")) {
                                    fp_copy_value = fp_copy_value.substring(0, fp_copy_value.length() - 1);
                                }
                                dc_list.add(fp_copy_value);
                            }


                            if (decoderType.endsWith("Fixed")) {

                                Cell off_cell = (Cell) Row.getCell(off_col_num, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                                if (off_cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                                    Continue = false;
                                    JOptionPane.showMessageDialog(frame, "In the Mapping sheet, \"Offset\" field  cannot have a blank value !! \n");

                                } else {
                                    off_value = off_cell.toString();
                                }
                                off_list.add(off_value);


                                Cell len_cell = (Cell) Row.getCell(len_col_num, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                                if (len_cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                                    Continue = false;
                                    JOptionPane.showMessageDialog(frame, "In the Mapping sheet, \"Length\" field  cannot have a blank value !! \n");

                                } else {
                                    len_value = len_cell.toString();
                                }
                                len_list.add(len_value);

                            }
                        }
                    }
                }
                if (Continue) {
                    //printToConsole ptc = new printToConsole();
                    //ptc.printConsole(fp_list, dt_list, nl_list, dc_list, off_list, len_list);
                    writeXMLFile wxf = new writeXMLFile();
                    wxf.writeXML(output, generate, frame, filePath, fp_list, dt_list, nl_list, dc_list, off_list, len_list, decoderType);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
