import scala.io.Source
import java.io.File
import java.io.PrintWriter

object TestURLRead extends App {

	override def main(args:Array[String]):Unit ={


		val URL = if(args.length >= 1) args(0) else "https://www.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm"
		val nsePage = Source.fromURL(URL)("UTF-8")
		val details = nsePage.mkString
		val out = new PrintWriter(new File("out.txt"))
		out.write(details)
		out.close

	}
}
