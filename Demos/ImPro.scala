package com.scala.impro

import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

object TestImpro {
  

  def phototest(img: BufferedImage, fromW:Int, width: Int, fromH: Int, height: Int): BufferedImage = {
    // obtain width and height of image
    val w = width;//img.getWidth
    val h = height;//img.getHeight
  
    // create new image of the same size
    val out = new BufferedImage(w-fromW, h-fromH, BufferedImage.TYPE_INT_RGB)
  
    // copy pixels (mirror horizontally)
    for (x <- fromW until w)
      for (y <- fromH until h)
        out.setRGB(x-fromW, y-fromH, img.getRGB(x,y) & 0xffffff)
    
    // draw red diagonal line
    /*for (x <- 0 until (h min w))
      out.setRGB(x, x, 0xff0000)*/
  
    out
  }
    
  def test() {
    // read original image, and obtain width and height
    val photo1 = ImageIO.read(new File("img.jpg"))
    
    val w = photo1.getWidth
    val h = photo1.getHeight
    
    val photo2 = phototest(photo1,0,w/2,0,h/2) 
    ImageIO.write(photo2, "jpg", new File("test1.jpg"))
    
    val photo4 = phototest(photo1,w/2,w,0,h/2) 
    ImageIO.write(photo4, "jpg", new File("test3.jpg"))
        
    val photo3 = phototest(photo1,0,w/2,h/2,h) 
    ImageIO.write(photo3, "jpg", new File("test2.jpg"))
    
    val photo5 = phototest(photo1,w/2,w, h/2, h) 
    ImageIO.write(photo5, "jpg", new File("test4.jpg"))
  }

  def main(args: Array[String]){
    test()
  }
}