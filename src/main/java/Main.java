import com.heroku.agent.MemoryAgent;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;

import java.util.Timer;

/**
 * A simple class that demonstrates/tests the usage of the OpenCV library in
 * Java. It prints a 3x3 identity matrix and then converts a given image in gray
 * scale.
 *
 */
public class Main
{

  public static void main(String[] args)
  {
    Timer timer = new Timer("Heroku MemoryAgent Timer",/*daemon*/true);
    timer.scheduleAtFixedRate(new MemoryAgent.Reporter(true, true, false), 2000, 5000);

    // load the OpenCV native library
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    while (true) {
      // prepare to convert a RGB image in gray scale
      String location = "resources/Poli.jpg";

      // get the jpeg image from the internal resource folder
      Mat image = Imgcodecs.imread(location);
      // convert the image in gray scale
      Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
      // write the new image on disk
      Imgcodecs.imwrite("resources/Poli-gray.jpg", image);

      // Uncomment this line to fix the leak
      //image.release();
    }
  }
}

