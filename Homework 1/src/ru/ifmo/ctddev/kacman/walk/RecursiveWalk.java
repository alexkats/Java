package ru.ifmo.ctddev.kacman.walk;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class RecursiveWalk {
    private final static int p = 0x01000193;
    private final static int q = 0xff;

    private static int calcHash(String file) {
        try {
            int hash = 0x811c9dc5;

            InputStream is = new FileInputStream(new File(file));
            int c;
            byte[] buf = new byte[2048];

            while ((c = is.read(buf)) != -1) {
                for (int i = 0; i < c; i++) {
                    hash = (hash * p) ^ (buf[i] & q);
                }
            }
            return hash;
        } catch (IOException e) {
            return 0;
        }
    }



    public static void main(String[] args) {
        if (args == null) {
            System.err.println("Null pointer exception");
            return;
        }

        if (args.length != 2) {
            System.err.println("Usage: RecursiveWalk <input file> <output file>");
            return;
        }

        if (args[0] == null || args[1] == null) {
            System.err.println("Wrong arguments!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"))) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"))) {
                String in;

                SimpleFileVisitor <Path> sfv = new SimpleFileVisitor<Path> () {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        String name = file.toString();
                        String out = String.format("%08x %s\n", calcHash(name), name);
                        try {
                            writer.write(out);
                        } catch (IOException e) {
                            System.err.println("IOException in file tree" + e.getMessage());
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) {
                        String name = file.toString();
                        String out = String.format("%08x %s\n", 0, name);
                        try {
                            writer.write(out);
                        } catch (IOException e) {
                            System.err.println("IOException in file tree " + e.getMessage());
                        }
                        return FileVisitResult.CONTINUE;
                    }
                };

                while ((in = reader.readLine()) != null) {
                    Files.walkFileTree(Paths.get(in), sfv);
                }
            } catch (FileNotFoundException e) {
                System.err.println("File or directory " + args[1] + " cannot be created");
            } catch (IOException e) {
                System.err.println(e.toString());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File or directory " + args[0] + " doesn\'t exists");
        } catch (IOException e) {
            System.err.println(e.toString());
        }

    }
}