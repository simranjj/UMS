package UMS.util;


import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javax.imageio.ImageIO;
import javax.print.*;
import java.awt.print.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class BarcodeUtil {

    private static File imageFile = new File("D:\\UMS\\UMS\\src\\UMS\\assets\\barcode.png");

    private static void createQRImage(String dataToBeEncrypted) throws IOException {

        Code128Bean code128 = new Code128Bean();
        code128.setHeight(15f);
        code128.setModuleWidth(0.3);
        code128.setQuietZone(10);
        code128.doQuietZone(true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 400, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        code128.generateBarcode(canvas, dataToBeEncrypted);
        canvas.finish();

        //write to png file
        FileOutputStream fos = new FileOutputStream(imageFile);
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();
    }

    private static class MyPrintable implements Printable {
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            BufferedImage bf = null;
            try {
                bf = ImageIO.read(BarcodeUtil.imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (pageIndex < 1) {
                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();
                double x = pageFormat.getImageableX();
                double y = pageFormat.getImageableY();
                g2d.translate((int) pageFormat.getImageableX() - 14, (int) pageFormat.getImageableY());
                g2d.drawImage(bf, Math.toIntExact(Math.round(x)) - 4, Math.toIntExact(Math.round(y)) - 3
                        , Math.toIntExact(Math.round(width)) + 30, Math.toIntExact(Math.round(height)), null);
                g2d.dispose();
                return PAGE_EXISTS;
            }
            return NO_SUCH_PAGE;
        }
    }

    private static double fromCMToPPI(double cm) {
        return toPPI(cm * 0.393700787);
    }

    private static double toPPI(double inch) {
        return inch * 72d;
    }

    public static void printCode(String dataToCode) throws IOException {

        BarcodeUtil.createQRImage(dataToCode);
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println(service.getName());
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        if (printerJob.printDialog()) {
            PageFormat pageFormat = printerJob.defaultPage();
            Paper paper = pageFormat.getPaper();
            double width = fromCMToPPI(15);
            double height = fromCMToPPI(2.4);
            double horizontalMargin = fromCMToPPI(0.25);
            double verticalMargin = fromCMToPPI(0.1);
            paper.setSize(width, height);
            paper.setImageableArea(
                    horizontalMargin,
                    verticalMargin,
                    width,
                    height);
            pageFormat.setOrientation(PageFormat.PORTRAIT);
            pageFormat.setPaper(paper);
            printerJob.setPrintable(new MyPrintable(), pageFormat);
            try {
                    printerJob.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

}

