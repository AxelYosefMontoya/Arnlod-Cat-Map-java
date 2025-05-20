package arnoldcattransform;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ArnoldCatTransform {
    
    public static void main(String[] args) {
        try {
            // 1. Seleccionar imagen interactivamente
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona una imagen");
            if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            
            File inputFile = fileChooser.getSelectedFile();
            BufferedImage originalImage = ImageIO.read(inputFile);
            
            // 2. Pedir número de iteraciones
            int iterations = Integer.parseInt(
                JOptionPane.showInputDialog("Número de iteraciones:", "3")
            );
            
            // 3. Procesar imagen
            BufferedImage transformedImage = originalImage;
            for (int i = 0; i < iterations; i++) {
                transformedImage = applyCatMap(transformedImage);
            }
            
            // 4. Guardar resultado
            File outputFile = new File(
                inputFile.getParent(), 
                "arnold_" + inputFile.getName()
            );
            
            ImageIO.write(transformedImage, "png", outputFile);
            
            JOptionPane.showMessageDialog(
                null, 
                "Transformación completada!\nGuardado en:\n" + outputFile.getAbsolutePath(),
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE
            );
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                null, 
                "Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private static BufferedImage applyCatMap(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImg = new BufferedImage(width, height, img.getType());
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int nx = (2 * x + y) % width;
                int ny = (x + y) % height;
                newImg.setRGB(nx, ny, img.getRGB(x, y));
            }
        }
        
        return newImg;
    }
}
