import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class PdfPictureAlbumCreator {
    private static final List<String> EXTENSIONS = Arrays.asList(".jpg", ".gif", ".png");


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("no image path");
            System.exit(0);
        }

        String path = args[0];
        File dir = new File(path);

        String[] files = dir.list();

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Image_" + System.currentTimeMillis() + ".pdf"));
            document.open();

            boolean top = true;

            for (String file : files) {


                String extension = file.substring(file.length() - 4).toLowerCase();

                if (EXTENSIONS.contains(extension)) {

                    String imageFile = path + '/' + file;

                    BufferedImage img = ImageIO.read(new File(imageFile));

                    int height = img.getHeight();
                    int width = img.getWidth();

                    Image image = Image.getInstance(imageFile);
                    image.scaleToFit(500, 500);
                    if (top) {
                        image.setAbsolutePosition(50f, 450f);
                    } else {
                        image.setAbsolutePosition(50f, 50f);

                    }

                    if (height > width) {
                        image.setRotationDegrees(270);
                    }

                    document.add(image);

                    if (top) {
                        top = false;
                    } else {
                        top = true;
                        document.newPage();
                    }
                }
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
