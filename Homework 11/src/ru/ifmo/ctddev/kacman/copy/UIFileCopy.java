package ru.ifmo.ctddev.kacman.copy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class UIFileCopy {

    public static boolean flag = false;
    public static long sizeOfDir = 0;
    public static final long CONVERT = 1048576;
    public static long haveCopied = 0;
    public static long start, end, allTime, thisStart, timeLeft, thisTime, thisEnd;

    private static void dfs(File source) {
        if (!source.isDirectory()) {
            sizeOfDir += source.length();
            return;
        }
        File[] children = source.listFiles();
        if (children == null) {
            throw new NullPointerException();
        }
        for (File aChildren1 : children) {
            if (aChildren1.isDirectory()) {
                dfs(aChildren1);
            } else {
                sizeOfDir += aChildren1.length();
            }
        }
    }

    private static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (flag) {
            return;
        }

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                if (!targetLocation.mkdir()) {
                    throw new IOException("Couldn't create directories");
                }
            }

            File[] children = sourceLocation.listFiles();
            if (children == null) {
                throw new NullPointerException();
            }

            for (File aChildren : children) {
                copy(aChildren, new File(targetLocation, aChildren.getName()));
            }
        } else {
            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);
            int count;
            byte[] bytes = new byte[4096];
            double percents;
            int value;
            long prevAllTime = -1;
            while ((count = in.read(bytes)) != -1) {
                thisStart = System.nanoTime();

                if (flag) {
                    break;
                }
                out.write(bytes, 0, count);

                haveCopied += count;
                percents = 1.0 * haveCopied / (sizeOfDir);
                value = (int) (100 * percents);
                prBar.setValue(value);
                end = (long) (System.nanoTime() / 1e7);
                thisEnd = System.nanoTime();
                allTime = end - start;
                timeLeft = (long) (1.0 * ((sizeOfDir - haveCopied) / (1.0 * haveCopied / allTime)));
                allTime /= 100;
                timeLeft /= 100;
                text1.setText("Elapsed time: " + String.valueOf(allTime) + " second" + ((allTime == 1) ? ("") : ("s")));
                text2.setText("Remaining time: " + String.valueOf(timeLeft) + " second" + ((timeLeft == 1) ? ("") : ("s")));
                if (allTime == 0) {
                    text3.setText("Average speed: 0 MB/second");
                } else {
                    text3.setText("Average speed: " + String.valueOf((long) (1.0 * haveCopied / (1.0 * allTime)) / CONVERT) + " MB/second");
                }
                thisTime = thisEnd - thisStart;
                if (prevAllTime != allTime) {
                    if (thisTime == 0) {
                        text4.setText("Current speed: 0 MB/second");
                    } else {
                        text4.setText("Current speed: " + String.valueOf((long) (1.0 * (count) / (1.0 * thisTime / 1e9) / CONVERT) + " MB/second"));
                    }
                }
                prevAllTime = allTime;
            }
            in.close();
            out.flush();
            out.close();
        }
    }

    public static class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            flag = true;
        }
    }

    private static JProgressBar prBar;
    private static JLabel text1, text2, text3, text4;

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            throw new IOException("Arguments should be: <address of file, we want to copy> <where we want to place copy>");
        }

        File file = new File(args[0]);
        JFrame frame = new JFrame("Copying of " + file.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 250));
        frame.setMinimumSize(new Dimension(250, 200));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        Box box = Box.createVerticalBox();
        prBar = new JProgressBar();
        box.add(prBar);
        box.add(Box.createVerticalStrut(10));
        text1 = new JLabel();
        box.add(text1);
        box.add(Box.createVerticalStrut(10));
        text2 = new JLabel();
        box.add(text2);
        box.add(Box.createVerticalStrut(10));
        text3 = new JLabel();
        box.add(text3);
        box.add(Box.createVerticalStrut(10));
        text4 = new JLabel();
        box.add(text4);
        box.add(Box.createVerticalGlue());

        panel.add(box);

        Box box2 = Box.createHorizontalBox();
        box2.add(Box.createHorizontalGlue());
        JButton button = new JButton("Cancel");
        ActionListener actionListener = new TestActionListener();
        button.addActionListener(actionListener);
        box2.add(button);
        panel.add(box2);
        prBar.setStringPainted(true);
        prBar.setMinimum(0);
        prBar.setMaximum(100);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        if (!file.isDirectory()) {
            sizeOfDir = file.length();
        } else {
            dfs(file);
        }
        start = (long) (System.nanoTime() / 1e7);
        copy(file, new File(args[1]));
        frame.dispose();
    }
}