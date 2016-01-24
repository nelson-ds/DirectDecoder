
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nelson.dsouza
 */
public class buildFrame {

    static String filePath = null;
    static JButton generate;
    static JRadioButton decoderFieldYes;
    static JRadioButton decoderFieldNo;
    static JRadioButton mappingFieldYes;
    static JRadioButton mappingFieldNo;
    boolean createMappingField;
    boolean createDecoderField;
    String quoteIntro = "<html><body><br><font style=Helvetica color=maroon size=6>Quote of the day<br><br>"
            + "<font style=Arial color=purple size=5>";
    String quote;
    String mappingText;
    String decoderText;
    String mandatoryFieldsText0;
    String mandatoryFieldsText1;
    String mandatoryFieldsText2;
    String mandatoryFieldsText3;
    String decoderType;
    String decoderInput;
    static JComboBox decoderList;
    static JComboBox decoderInputList;

    public void frame() {

        final String intro = "<html><body><br><font style=Helvetica color=green size=5>Instructions for using Direct Decoder Tool"
                + "<br> <br><font style=Arial color=purple size=4> 1. Select the mapping sheet"
                + "<br> 2. Customize options"
                + "<br> 3. Click on Save & Generate"
                + "</html>";
        final String instructions = "<html><body><br><font style=Helvetica color=green size=5>Instructions for using Direct Decoder Tool"
                + "<br> <br><font style=Arial color=purple size=4> 1. Select the mapping sheet"
                + "<br> 2. Customize options"
                + "<br> 3. Click on Save & Generate"
                + "<br><br><b>Mandatory Reqirements are</b><br>"
                + "# All column headers should be in the same row<br>"
                + "# Column names should match exactly to the ones prompted by this tool<br>"
                + "# For Binary Mapping sheet, all decoder fields will be of data type Hex by default."
                + " You will need to modify it later, as per your requirements<br>";
        final String about = "<br><font style=Helvetica color=green size=5>Product"
                + "<br><font color=purple>Direct Decoder Version 1.0 beta"
                + "<br><font size=4><i>Released on 1 June 2012</i>"
                + "<br><br><font size=5 color=green>Developed by"
                + "<br><font color=purple>Nelson Dsouza<font color=black> @2012"
                + "<br><font color=purple size=4>Email - <i>nelson.dsouza@subex.com</i><br>"
                + "Skype - <i>nelson.dsouza.here</i>"
                + "<br><br><font size=5 color=green>Acknowledgement"
                + "<br><font color=purple size=4> Preetham B, Lakshmi A <i>(For guidance and support)</i>";

        final String selectFile = "<html><body><br><font style=Arial color=green size=5>Browsing for mapping sheet..";

        final JLabel space = new JLabel("                                                                                "
                + "                                                                                "
                + "                                                                                "
                + "                                                                                ");

        final JFrame frame = new JFrame();
        frame.setTitle("Direct Decoder");

        decoderText = "<br><br><font style=Helvetica color=purple size=5>You have chosen NOT to include decoder field";
        mappingText = "<br><font style=Helvetica color=purple size=5>You have chosen NOT to include mapping field";
        mandatoryFieldsText1 = " ";
        mandatoryFieldsText3 = "<font color=purple>"
                + "<br>=> Offset"
                + "<br>=> Length";

        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("Menu");
        Menu help = new Menu("Help");
        MenuItem randomQuote = new MenuItem("Quote of the day");
        MenuItem exitMenu = new MenuItem("Exit");
        MenuItem instructionsMenu = new MenuItem("Instructions");
        MenuItem aboutMenu = new MenuItem("About");
        file.add(randomQuote);
        file.add(exitMenu);
        help.add(instructionsMenu);
        help.add(aboutMenu);
        menuBar.add(file);
        menuBar.add(help);

        Border border1 = BorderFactory.createLineBorder(Color.WHITE);
        JLabel path = new JLabel("  Path                                                    ");
        final JLabel decoderField = new JLabel("  Create Decoder Field ?                ");
        JLabel createOutputMapping = new JLabel(("  Create Mapping Field ?                "));

        final JTextPane output = new JTextPane();
        output.setBorder(BorderFactory.createCompoundBorder(border1, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        output.setContentType("text/html");
        output.setText(intro);
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);

        JButton chooseFile = new JButton("Select File");
        generate = new JButton("Save & Generate XML");
        generate.setEnabled(false);
        JButton exit = new JButton("Exit");

        decoderFieldYes = new JRadioButton("Yes", false);
        decoderFieldNo = new JRadioButton("No", true);
        mappingFieldYes = new JRadioButton("Yes", false);
        mappingFieldNo = new JRadioButton("No", true);

        decoderFieldYes.setEnabled(false);
        decoderFieldNo.setEnabled(false);
        mappingFieldYes.setEnabled(false);
        mappingFieldNo.setEnabled(false);

        final ButtonGroup bgroupDecoder = new ButtonGroup();
        final ButtonGroup bgroupMapping = new ButtonGroup();


        bgroupDecoder.add(decoderFieldYes);
        bgroupDecoder.add(decoderFieldNo);
        bgroupMapping.add(mappingFieldYes);
        bgroupMapping.add(mappingFieldNo);



        String[] decoderArray = {"ASCII Delimited", "ASCII Fixed", "Binary Fixed"};
        decoderList = new JComboBox(decoderArray);
        decoderList.setPreferredSize(new Dimension(117, 20));

        String[] decoderInputArray = {"Same as Mapping", "Distinct values", "Y/N Values"};
        decoderInputList = new JComboBox(decoderInputArray);
        decoderInputList.setPreferredSize(new Dimension(130, 20));

        decoderList.setEnabled(false);
        decoderInputList.setEnabled(false);
        decoderList.setFocusable(true);
        decoderInputList.setFocusable(true);

//Adding to frame
        JPanel north1 = new JPanel();
        north1.setLayout(new BoxLayout(north1, BoxLayout.LINE_AXIS));
        north1.add(path);
        north1.add(chooseFile);

        JPanel north2 = new JPanel();
        north2.setLayout(new BoxLayout(north2, BoxLayout.LINE_AXIS));
        north2.add(createOutputMapping);
        north2.add(mappingFieldYes);
        north2.add(mappingFieldNo);

        JPanel north3 = new JPanel();
        north3.setLayout(new BoxLayout(north3, BoxLayout.LINE_AXIS));
        north3.add(decoderField);
        north3.add(decoderFieldYes);
        north3.add(decoderFieldNo);
        JPanel north31 = new JPanel();
        north31.setLayout(new FlowLayout());
        north31.add(decoderList);
        north31.add(decoderInputList);
        north31.add(space);
        north3.add(north31);

        JPanel pNorth = new JPanel(new GridLayout(3, 1, 10, 10));
        pNorth.add(north1);
        pNorth.add(north2);
        pNorth.add(north3);

        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BorderLayout());
        pCenter.add(scroll, BorderLayout.CENTER);

        JPanel pSouth = new JPanel(new FlowLayout());
        pSouth.add(generate);
        pSouth.add(exit);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(pNorth, BorderLayout.NORTH);
        frame.getContentPane().add(pCenter, BorderLayout.CENTER);
        frame.getContentPane().add(pSouth, BorderLayout.SOUTH);

        frame.setMenuBar(menuBar);
        frame.setSize(540, 525);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = frame.getSize().width;
        int h = frame.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        frame.setLocation(x, y);

        frame.setVisible(true);

        ActionListener actionListenerRandomQuote = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {

                System.out.println("Fetching random quote ");
                quoteOfTheDay qod = new quoteOfTheDay();
                quote = qod.quote();
                output.setText(quoteIntro + quote);
            }
        };

        ActionListener actionListenerMenuExit = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Exiting..");
                output.setText(selectFile);
                System.exit(0);
            }
        };

        ActionListener actionListenermenuInstructions = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Loading Instructions..");
                output.setText(instructions);
            }
        };

        ActionListener actionListenerMenuAbout = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("About Developer..");
                output.setText(about);
            }
        };

        ActionListener actionListenerChooseFile = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                frame.setTitle("Direct Decoder");
                System.out.println("Browsing for mapping sheet..");
                output.setText(selectFile);
            }
        };

        ActionListener actionListenerGenerate = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Clicked on save and generate..");
            }
        };

        ActionListener actionListenerExit = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Exiting..");
            }
        };


        ActionListener actionListenerMappingYes = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {

                if (decoderFieldNo.isSelected()) {
                    decoderText = "<br><br><font style=Helvetica color=purple size=5>You have chosen NOT to include decoder field";
                    mandatoryFieldsText0 = "<br>";
                    mandatoryFieldsText2 = " ";
                } else {
                    mandatoryFieldsText0 = "<font color=purple>";
                    if (getDecoderInput().equals("Same as Mapping")) {
                        mandatoryFieldsText2 = " ";
                    } else {
                        mandatoryFieldsText2 = "<i><font size=4 style=Helvetica color=purple><br>=> Decoder Field</i>";
                    }
                }
                mappingText = "<br><font style=Helvetica color=Green size=5>You have chosen to include mapping field.";

                mandatoryFieldsText1 = mandatoryFieldsText0
                        + "<br><br><br><i><font size=4 style=Helvetica color=green>Mandatory columns in your mapping sheet are -"
                        + "<font color=purple>"
                        + "<br>=> Mapping Field"
                        + mandatoryFieldsText2
                        + "<br>=> Data Type"
                        + "<br>=> Nullable</i>";

                if (getDecoderType().endsWith("Fixed") && decoderFieldYes.isSelected()) {
                    mandatoryFieldsText1 = mandatoryFieldsText1 + mandatoryFieldsText3;
                }

                output.setText(mappingText + decoderText + mandatoryFieldsText1);
            }
        };

        ActionListener actionListenerMappingNo = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                if (decoderFieldNo.isSelected()) {
                    decoderText = "<br><br><font style=Helvetica color=purple size=5>You have chosen NOT to include decoder field";
                    mandatoryFieldsText0 = "<br><br>";
                    mandatoryFieldsText1 = " ";
                    mandatoryFieldsText2 = " ";
                } else {
                    mandatoryFieldsText0 = "<font color=purple>";
                    if (getDecoderInput().equals("Same as Mapping")) {
                        mandatoryFieldsText2 = "<br>=> Mapping Field";
                    } else {
                        mandatoryFieldsText2 = "<br>=> Decoder Field";
                    }
                    mandatoryFieldsText1 = mandatoryFieldsText0
                            + "<br><br><br><i><font size=4 style=Helvetica color=green>Mandatory columns in your mapping sheet are -"
                            + "<font color=purple>"
                            + mandatoryFieldsText2
                            + "<br>=> Data Type"
                            + "<br>=> Nullable</i>";
                }
                mappingText = "<br><font style=Helvetica color=purple size=5>You have chosen NOT to include mapping field";

                if (getDecoderType().endsWith("Fixed") && decoderFieldYes.isSelected()) {
                    mandatoryFieldsText1 = mandatoryFieldsText1 + mandatoryFieldsText3;
                }
                output.setText(mappingText + decoderText + mandatoryFieldsText1);
            }
        };


        ActionListener actionListenerDecoderYes = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {

                mandatoryFieldsText0 = "<font color=purple>";

                if (mappingFieldNo.isSelected()) {
                    if (getDecoderInput().equals("Same as Mapping")) {

                        mandatoryFieldsText2 = "<i><font size=4 style=Helvetica color=purple><br>=> Mapping Field</i>";
                    } else {
                        mandatoryFieldsText2 = "<i><font size=4 style=Helvetica color=purple><br>=> Decoder Field</i>";
                    }
                    mappingText = "<br><font style=Helvetica color=purple size=5>You have chosen NOT to include mapping field";
                } else {
                    if (getDecoderInput().equals("Same as Mapping")) {

                        mandatoryFieldsText2 = "<i><font size=4 style=Helvetica color=purple><br>=> Mapping Field</i>";
                    } else {
                        mandatoryFieldsText2 = "<i><font size=4 style=Helvetica color=purple><br>=> Mapping Field <br>=> Decoder Field</i>";
                    }
                }

                decoderText = "<br><br><font style=Helvetica color=Green size=5>You have chosen to include decoder field."
                        + " Current selections are-"
                        + "<br><font color =green style=Arial size=4> 1. Decoder type - <i><font color=purple>" + getDecoderType() + "<font color=green><br></i>"
                        + "2. Input value - <i><font color=purple>" + getDecoderInput() + "</font>";

                mandatoryFieldsText1 = mandatoryFieldsText0
                        + "<br><br><br><i><font size=4 style=Helvetica color=green>Mandatory columns in your mapping sheet are -"
                        + "<font color=purple>"
                        + mandatoryFieldsText2
                        + "<br>=> Data Type<"
                        + "<br>=> Nullable</i>";

                if (getDecoderType().endsWith("Fixed") && decoderFieldYes.isSelected()) {
                    mandatoryFieldsText1 = mandatoryFieldsText1 + mandatoryFieldsText3;
                }
                output.setText(mappingText + decoderText + mandatoryFieldsText1);
                decoderList.setEnabled(true);
                decoderInputList.setEnabled(true);
            }
        };

        ActionListener actionListenerDecoderNo = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                mandatoryFieldsText0 = "<br>";
                if (mappingFieldNo.isSelected()) {
                    mappingText = "<br><font style=Helvetica color=purple size=5>You have chosen NOT to include mapping field";
                    mandatoryFieldsText1 = " ";
                    mandatoryFieldsText2 = " ";
                } else {
                    mandatoryFieldsText2 = " ";
                    mandatoryFieldsText1 = mandatoryFieldsText0
                            + "<br><br><br><i><font size=4 style=Helvetica color=green>Mandatory columns in your mapping sheet are -"
                            + "<font color=purple>"
                            + "<br>=> Mapping Field"
                            + mandatoryFieldsText2
                            + "<br>=> Data Type"
                            + "<br>=> Nullable</i>";
                }

                decoderText = "<br><br><font style=Helvetica color=purple size=5>You have chosen NOT to include decoder field";
                output.setText(mappingText + decoderText + mandatoryFieldsText1);
                decoderList.setEnabled(false);
                decoderInputList.setEnabled(false);

            }
        };


        ActionListener actionListenerDecoderList = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {

                if (decoderList.isEnabled()) {

                    decoderText = "<br><br><font style=Helvetica color=Green size=5>You have chosen to include decoder field."
                            + " Current selections are-"
                            + "<br><font color =green style=Arial size=4> 1. Decoder type - <i><font color=purple>" + getDecoderType() + "<font color=green><br></i>"
                            + "2. Input value - <i><font color=purple>" + getDecoderInput() + "</font>";

                    if (getDecoderType().endsWith("Fixed")) {
                        if (!mandatoryFieldsText1.endsWith("Length")) {
                            mandatoryFieldsText1 = mandatoryFieldsText1 + mandatoryFieldsText3;

                        }
                    } else {
                        if (mandatoryFieldsText1.endsWith("Length")) {
                            mandatoryFieldsText1 = mandatoryFieldsText1.substring(0, mandatoryFieldsText1.length() - 25);
                        }
                    }

                    output.setText(mappingText + decoderText + mandatoryFieldsText1);
                }

            }
        };

        ActionListener actionListenerDecoderInputList = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                if (decoderInputList.isEnabled()) {

                    mandatoryFieldsText0 = "<font color=purple>";
                    decoderText = "<br><br><font style=Helvetica color=Green size=5>You have chosen to include decoder field."
                            + " Current selections are-"
                            + "<br><font color =green style=Arial size=4> 1. Decoder type - <i><font color=purple>" + getDecoderType() + "<font color=green><br></i>"
                            + "2. Input value - <i><font color=purple>" + getDecoderInput() + "</font>";

                    if (getDecoderInput().equals("Same as Mapping")) {
                        mandatoryFieldsText2 = " ";
                        mandatoryFieldsText1 = mandatoryFieldsText0
                                + "<br><br><br><i><font size=4 style=Helvetica color=green>Mandatory columns in your mapping sheet are -"
                                + "<font color=purple>"
                                + "<br>=> Mapping Field"
                                + mandatoryFieldsText2
                                + "<br>=> Data Type"
                                + "<br>=> Nullable</i>";
                    } else {
                        if (mappingFieldYes.isSelected()) {
                            mandatoryFieldsText2 = " ";
                            mandatoryFieldsText1 = mandatoryFieldsText0
                                    + "<br><br><br><i><font size=4 style=Helvetica color=green>Mandatory columns in your mapping sheet are -"
                                    + "<font color=purple>"
                                    + "<br>=> Mapping Field"
                                    + "<br>=> Decoder Field"
                                    + "<br>=> Data Type"
                                    + "<br>=> Nullable</i>"
                                    + mandatoryFieldsText2;
                        } else {
                            mandatoryFieldsText2 = " ";
                            mandatoryFieldsText1 = mandatoryFieldsText0
                                    + "<br><br><br><i><font size=4 style=Helvetica color=green>Mandatory columns in your mapping sheet are -"
                                    + "<font color=purple>"
                                    + mandatoryFieldsText2
                                    + "<br>=> Decoder Field"
                                    + "<br>=> Data Type<"
                                    + "<br>=> Nullable</i>";
                        }
                    }

                    if (getDecoderType().endsWith("Fixed")) {
                        mandatoryFieldsText1 = mandatoryFieldsText1 + mandatoryFieldsText3;
                    }
                    output.setText(mappingText + decoderText + mandatoryFieldsText1);
                }

            }
        };


        MouseListener mouseListenerChooseFile = new MouseAdapter() {

            public void mousePressed(MouseEvent mouseEvent) {
                int modifiers = mouseEvent.getModifiers();
                if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                }
                if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) {
                }
                if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                }
            }

            public void mouseReleased(MouseEvent mouseEvent) {
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    String filename = File.separator;
                    filePath = null;
                    generate.setEnabled(false);
                    decoderFieldYes.setEnabled(false);
                    decoderFieldNo.setEnabled(false);
                    mappingFieldYes.setEnabled(false);
                    mappingFieldNo.setEnabled(false);
                    decoderList.setEnabled(false);
                    decoderInputList.setEnabled(false);
                    decoderFieldYes.setSelected(false);
                    decoderFieldNo.setSelected(true);
                    mappingFieldYes.setSelected(false);
                    mappingFieldNo.setSelected(true);
                    decoderList.setSelectedIndex(0);
                    decoderInputList.setSelectedIndex(0);
                    JFileChooser fc = new JFileChooser(new File(filename));
                    fc.setDialogType(JFileChooser.SAVE_DIALOG);

                    fc.addActionListener(new AbstractAction() {

                        public void actionPerformed(ActionEvent evt) {
                            JFileChooser fc = (JFileChooser) evt.getSource();
                            String fileName = null;

                            if (JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) {
                                // Open or Save was clicked                                
                                File selFile = fc.getSelectedFile();
                                System.out.println("open clicked");
                                fileName = selFile.getName();
                                String clickedOpen = "<html><body><br><font style=Arial color=purple size=5>Selected "
                                        + "the mapping sheet :- <font color=green>\"" + fileName + "\"<font color=purple>.<br><br>Configure the options "
                                        + "and Click on 'Save& Generate XML' button to generate diamond xml."
                                        + "<br><br><br><br><br><br><font size=4 color=purple><i>Note - Do not give an extension to the filename while saving.</i>"
                                        + " The file will be automatically saved as .xml file";

                                String notExcel = "<html><body><br><font style=Arial color=purple size=5>Please select a"
                                        + " mapping sheet that is  either <b>.xsl</b> , <b>.xslt</b> or <b>.ods</b> format";
                                String filePathBrowsed = selFile.getPath();
                                filePath = filePathBrowsed;

                                if (filePathBrowsed.endsWith(".xls") || filePathBrowsed.endsWith(".xlst") || filePathBrowsed.endsWith(".ods")) {
                                    frame.setTitle("Direct Decoder - " + fileName);
                                    output.setText(clickedOpen);
                                    decoderFieldYes.setEnabled(true);
                                    decoderFieldNo.setEnabled(true);
                                    mappingFieldYes.setEnabled(true);
                                    mappingFieldNo.setEnabled(true);
                                    generate.setEnabled(true);
                                } else {
                                    frame.setTitle("Direct Decoder");
                                    output.setText(notExcel);
                                    decoderFieldYes.setEnabled(false);
                                    decoderFieldNo.setEnabled(false);
                                    mappingFieldYes.setEnabled(false);
                                    mappingFieldNo.setEnabled(false);
                                    generate.setEnabled(false);
                                    decoderList.setEnabled(false);
                                    decoderInputList.setEnabled(false);
                                }
                            } else if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {
                                System.out.println("cancel clicked");
                                output.setText(intro);

                            }
                        }
                    });

                    fc.showOpenDialog(frame);

                    if (filePath == null) {
                        output.setText(intro);
                    }

                }
                if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {
                }
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                }
                System.out.println();
            }
        };


        MouseListener mouseListenerGenerate = new MouseAdapter() {

            public void mousePressed(MouseEvent mouseEvent) {
                int modifiers = mouseEvent.getModifiers();
                if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                }
                if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) {
                }
                if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                }
            }

            public void mouseReleased(MouseEvent mouseEvent) {

                if (generate.isEnabled()) {

                    if (SwingUtilities.isLeftMouseButton(mouseEvent)) {

                        if (bgroupDecoder.getSelection().equals(decoderFieldYes.getModel())) {

                            createDecoderField = true;
                        } else {
                            createDecoderField = false;
                        }

                        if (bgroupMapping.getSelection().equals(mappingFieldYes.getModel())) {

                            createMappingField = true;
                        } else {
                            createMappingField = false;
                        }
                        // columnYN = checkColumnNames.getState();
                        output.setText(mappingText + decoderText + mandatoryFieldsText1);
                        if (createDecoderField || createMappingField) {
                            readExcelFile readExcelFile = new readExcelFile();
                            readExcelFile.readExcel(getDecoderType(), getDecoderInput(), createMappingField, createDecoderField, output, generate, frame, filePath);

                            System.out.println("Creating mapping field " + createMappingField);
                            System.out.println("Creating decoder field " + createDecoderField);
                            System.out.println("Decoder Type is " + getDecoderType());
                            System.out.println("Decodeder Input is " + getDecoderInput());

                        } else {
                            JOptionPane.showMessageDialog(frame, "Cannot Generate xml containing no mapping or decoder field \n"
                                    + "                             Choose atleast one !!");
                        }
                    }
                }
                if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {
                }
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                }
                System.out.println();
            }
        };



        MouseListener mouseListenerExit = new MouseAdapter() {

            public void mousePressed(MouseEvent mouseEvent) {
                int modifiers = mouseEvent.getModifiers();
                if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                }
                if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) {
                }
                if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                }
            }

            public void mouseReleased(MouseEvent mouseEvent) {
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    System.exit(0);
                }
                if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {
                }
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                }
                System.out.println();
            }
        };

        randomQuote.addActionListener(actionListenerRandomQuote);
        exitMenu.addActionListener(actionListenerMenuExit);
        instructionsMenu.addActionListener(actionListenermenuInstructions);
        aboutMenu.addActionListener(actionListenerMenuAbout);
        chooseFile.addActionListener(actionListenerChooseFile);
        generate.addActionListener(actionListenerGenerate);
        mappingFieldYes.addActionListener(actionListenerMappingYes);
        mappingFieldNo.addActionListener(actionListenerMappingNo);
        decoderFieldYes.addActionListener(actionListenerDecoderYes);
        decoderFieldNo.addActionListener(actionListenerDecoderNo);

        decoderList.addActionListener(actionListenerDecoderList);
        decoderInputList.addActionListener(actionListenerDecoderInputList);

        generate.addMouseListener(mouseListenerGenerate);
        chooseFile.addMouseListener(mouseListenerChooseFile);
        exit.addActionListener(actionListenerExit);
        exit.addMouseListener(mouseListenerExit);

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public String getDecoderType() {
        return decoderType = (String) decoderList.getSelectedItem();
    }

    public String getDecoderInput() {
        return decoderInput = (String) decoderInputList.getSelectedItem();
    }
}
