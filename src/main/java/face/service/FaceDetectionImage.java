package face.service;

import org.opencv.core.*;

import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FaceDetectionImage {
    public static void main(String[] args) throws IOException {
        // Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Reading the Image from the file and storing it in to a Matrix object
        String file = "E:/OpenCV/chap23/3333.JPG";
        Mat src = Imgcodecs.imread(file);

        // Instantiating the CascadeClassifier
        String xmlFile = "lbpcascade_frontalface.xml";

        CascadeClassifier classifier = new CascadeClassifier(xmlFile);
        boolean load = classifier.load("G:\\IJSE\\GDSE 67\\For Innovesta V2\\Face_Id\\src\\main\\java\\face\\service\\lbpcascade_frontalface.xml");
        System.out.println(load);

        // Detecting the face in the snap
        MatOfRect faceDetections = new MatOfRect();

        classifier.detectMultiScale(src, faceDetections);
        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));

        Rect[] rects = faceDetections.toArray();



        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(
                    src,                                               // where to draw the box
                    new Point(rect.x, rect.y),                            // bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), // top right
                    new Scalar(0, 0, 255),
                    3                                                     // RGB colour
            );

        }

        int i = 0;
        /*for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0)); // frame is Mat
            Rect rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
            Mat image_roi = new Mat(src, rectCrop);
            //String f = "E:/OpenCV/chap23/facet0.jpg";
            Mat s = Imgcodecs.imread("E:/OpenCV/chap23/facet0.jpeg");
            MatOfByte mob = new MatOfByte();
            boolean imencode = Imgcodecs.imencode(".jpeg", s, mob);
            byte[] bytes = mob.toArray();
            BufferedImage read = ImageIO.read(new ByteArrayInputStream(bytes));
            MatOfByte m = new MatOfByte();
            Imgcodecs.imencode(".jpeg", image_roi, m);

            byte[] bytes1 = m.toArray();
            BufferedImage read1 = ImageIO.read(new ByteArrayInputStream(bytes1));

            System.out.println(bytes.length +" : "+bytes1.length);
            boolean compare = compare(read, read1);
            System.out.println(compare);

            if(image_roi.empty()){
                System.out.println("Not Support");
                return;
            }

            Imgcodecs.imwrite("E:/OpenCV/chap23/facet" + i + ".jpeg", image_roi);
            i++;
        }*/


        // Writing the image
        Imgcodecs.imwrite("E:/OpenCV/chap23/facedetect_output1.jpg", src);

        System.out.println("Image Processed");
    }

    /*public static void compare(BufferedImage img1, BufferedImage img2) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://face-compare1.p.rapidapi.com/v3/tasks/async/compare/face"))
                .header("content-type", "text/plain")
                .header("X-RapidAPI-Key", "b01129e8fcmsh04f353984676b9fp1b2e56jsn11e9ba0e2ca6")
                .header("X-RapidAPI-Host", "face-compare1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"task_id\": \"74f4c926-250c-43ca-9c53-453e87ceacd1\","+"\"group_id\": \"8e16424a-58fc-4ba4-ab20-5bc8e7c3c41e\","+"\"data\": {"+
                        "\"document1\": \"https://images.news18.com/ibnlive/uploads/2017/11/Shah-Rukh-Khan-at-the-Millennium-Dome-London1.jpg\","+
                        "\"document2\": \"https://www.filmibeat.com/img/popcorn/profile_photos/shahrukh-khan-20190625140849-86.jpg\""+
                        "}}"))
        .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        //https://rapidapi.com/idfy-idfy-default/api/face-compare1/

    }*/
}