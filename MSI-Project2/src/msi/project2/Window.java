/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msi.project2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Maciek
 */
public class Window extends JFrame {

    static final int witdh = 800;
    static final int height = 600;

    String[] names = {"k", "lambda=2", "labmda=10"};
    final JTable table = new JTable(getPropabilityTable(), names);
    String[] namesDist = {"t", "lambda=2", "labmda=10"};
    final JTable tableDist = new JTable(getComultativeDistributionTable(), namesDist);
    JButton propButton = new JButton("Pokaz diagram prawdopodobienstwa");
    JButton distButton = new JButton("Pokaz diagram dystrybuanty");

    JFreeChart jfc;

    JPanel listPane = new JPanel();

    public Window() throws HeadlessException {
        setSize(witdh, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        propButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                for (String[] s : getPropabilityTable()) {
                    dcd.addValue((double) Double.valueOf(s[1]), "Lambda = 2", s[0]);
                    dcd.addValue((double) Double.valueOf(s[2]), "Lambda = 10", s[0]);
                }
                jfc = ChartFactory.createBarChart3D("Wykres prawdopodobieństwa", "K", "P(t)", dcd, PlotOrientation.VERTICAL, true, true, false);
                CategoryPlot plot = jfc.getCategoryPlot();
                plot.setRangeGridlinePaint(Color.black);
                ChartFrame cf = new ChartFrame("Prawdopodobienstwo", jfc, true);
                cf.setSize(800, 500);
                cf.setVisible(true);
            }
        });

        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(new JScrollPane(table), BorderLayout.CENTER);
        listPane.add(propButton);

        distButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                for (String[] s : getComultativeDistributionTable()) {
                    dcd.addValue((double) Double.valueOf(s[1]), "Lambda = 2", s[0]);
                    dcd.addValue((double) Double.valueOf(s[2]), "Lambda = 10", s[0]);
                }
                jfc = ChartFactory.createBarChart3D("Wykres prawdopodobieństwa", "K", "F(t)", dcd, PlotOrientation.VERTICAL, true, true, false);
                CategoryPlot plot = jfc.getCategoryPlot();
                plot.setRangeGridlinePaint(Color.black);
                ChartFrame cf = new ChartFrame("Dystrybuanta", jfc, true);
                cf.setSize(800, 500);
                cf.setVisible(true);
            }
        });
        listPane.add(new JScrollPane(tableDist), BorderLayout.CENTER);
        listPane.add(distButton);
        getContentPane().add(listPane);
    }

    private String[][] getPropabilityTable() {
        String[][] table = {
            {"0", "0.1353", "0.0000"},
            {"1", "0.2707", "0.0005"},
            {"2", "0.2707", "0.0023"},
            {"3", "0.1804", "0.0076"},
            {"4", "0.0902", "0.0189"},
            {"5", "0.0361", "0.0378"},
            {"6", "0.0120", "0.0631"},
            {"7", "0.0034", "0.0901"},
            {"8", "0.0009", "0.1126"},
            {"9", "0.0002", "0.1251"},
            {"10", "0.0000", "0.1251"},
            {"11", "0.0000", "0.1137"},
            {"12", "0.0000", "0.0948"},
            {"13", "0.0000", "0.0729"},
            {"14", "0.0000", "0.0521"},
            {"15", "0.0000", "0.0347"}
        };
        return table;
    }

    private String[][] getComultativeDistributionTable() {
        String[][] table = {
            {"0.1", "0.1813", "0.8647"},
            {"0.2", "0.3297", "0.9502"},
            {"0.3", "0.4512", "0.9817"},
            {"0.4", "0.5507", "0.9933"},
            {"0.5", "0.6321", "0.9975"},
            {"0.6", "0.6988", "0.9991"},
            {"0.7", "0.7534", "0.9991"},
            {"0.8", "0.7981", "0.9997"},
            {"0.9", "0.8347", "0.9999"},
            {"1.0", "0.8647", "1.0000"},
            {"1.1", "0.8892", "1.0000"},
            {"1.2", "0.9093", "1.0000"},
            {"1.3", "0.9257", "1.0000"}
        };

        return table;
    }
}
