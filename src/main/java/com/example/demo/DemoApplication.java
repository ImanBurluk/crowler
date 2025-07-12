package com.example.demo;

import org.springframework.boot.SpringApplication;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class DemoApplication {

    public static void main(String[] args)throws Exception {
        SpringApplication.run(DemoApplication.class, args);

        String folderPath = "C:\\Users\\Jaros\\project\\java\\test\\crowler\\demo\\img";


        String outputPath = "C:\\Users\\Jaros\\project\\java\\test\\crowler\\output.pdf";

        File folder = new File(folderPath);
        File[] imageFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));



//        System.out.println("Папка: " + folder.getAbsolutePath());
//        System.out.println("Содержимое:");
//
//        for (File f : folder.listFiles()) {
//            System.out.println(f.getName());
//        }


                if (imageFiles == null || imageFiles.length == 0) {
                    System.out.println("Нет PNG-файлов в папке.");
                    return;
                }

                // Сортировка по имени (чтобы page_1.png шёл первым и т.д.)
                Arrays.sort(imageFiles, (a, b) -> {
                    String aNum = a.getName().replaceAll("\\D+", "");
                    String bNum = b.getName().replaceAll("\\D+", "");
                    return Integer.compare(Integer.parseInt(aNum), Integer.parseInt(bNum));
                });

                PDDocument document = new PDDocument();

                for (File imageFile : imageFiles) {
                    PDImageXObject pdImage = PDImageXObject.createFromFile(imageFile.getAbsolutePath(), document);
                    PDPage page = new PDPage(new PDRectangle(pdImage.getWidth(), pdImage.getHeight()));
                    document.addPage(page);

                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    contentStream.drawImage(pdImage, 0, 0);
                    contentStream.close();
                }

                document.save(outputPath);
                document.close();

                System.out.println("PDF создан: " + outputPath);


    }
}
