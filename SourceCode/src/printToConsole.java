/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nelson.dsouza
 */
import java.util.*;

public class printToConsole {

    List fp_list = new ArrayList();
    List dt_list = new ArrayList();
    List nl_list = new ArrayList();
    List dc_list = new ArrayList();
    int size;

    public void printConsole(java.util.List fp_list, java.util.List dt_list, java.util.List nl_list, java.util.List dc_list, java.util.List off_list, java.util.List len_list) {
        try {
            this.fp_list = fp_list;
            this.dt_list = dt_list;
            this.nl_list = nl_list;
            this.dc_list = dc_list;
            if (fp_list.size() > dc_list.size()) {
                size = fp_list.size();
            } else {
                size = dc_list.size();
            }


            for (int i = 0; i < size; i++) {

                if (i > fp_list.size() - 1) {
                    System.out.print("-" + "\t \t \t");
                } else {
                    if (fp_list.get(i).equals("-")) {
                        System.out.print("-" + "\t \t \t");
                    } else {
                        System.out.print(fp_list.get(i) + "\t \t \t");
                    }
                }

                if (i > dc_list.size() - 1) {
                    System.out.print("-" + "\t \t \t");
                } else {
                    if (dc_list.get(i).equals("-")) {
                        System.out.print("-" + "\t \t \t");
                    } else {
                        System.out.print(dc_list.get(i) + "\t \t \t");
                    }
                }



                System.out.print(dt_list.get(i) + "\t \t \t");
                System.out.print(nl_list.get(i));

                System.out.println();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
