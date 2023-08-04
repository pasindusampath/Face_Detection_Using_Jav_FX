package face.pkg;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Detection {
    private static Detection detection;
    private CascadeClassifier classifier;

    private Detection(){
        // Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Reading the Image from the file and storing it in to a Matrix object
        /*String file = "E:/OpenCV/chap23/3333.JPG";
       src= Imgcodecs.imread(file);*/

        // Instantiating the CascadeClassifier
        String xmlFile = "lbpcascade_frontalface.xml";

        classifier= new CascadeClassifier(xmlFile);
        boolean load = classifier.load("G:\\IJSE\\GDSE 67\\For Innovesta V2\\Face_Id\\src\\main\\java\\face\\service\\lbpcascade_frontalface.xml");
        System.out.println(load);

    }

    public static Detection getInstance(){
        return detection==null ? detection=new Detection() : detection;
    }

    public BufferedImage getImage(BufferedImage img) throws IOException {
        MatOfRect faceDetections = new MatOfRect();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        MatOfByte matOfByte = new MatOfByte(imageBytes);
        Mat matImage = Imgcodecs.imdecode(matOfByte, Imgcodecs.IMREAD_UNCHANGED);
        //Mat src = Imgcodecs.
        classifier.detectMultiScale(matImage, faceDetections);
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(
                    matImage,                                               // where to draw the box
                    new Point(rect.x, rect.y),                            // bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), // top right
                    new Scalar(0, 0, 255),
                    3                                                     // RGB colour
            );

        }
        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));
        MatOfByte matOfBytes = new MatOfByte();
        Imgcodecs.imencode(".png", matImage, matOfBytes);
        byte[] imageBytes1 = matOfBytes.toArray();

        BufferedImage bufferedImage = null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes1);
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;

    }
}
