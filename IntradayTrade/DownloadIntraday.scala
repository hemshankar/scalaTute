 
import sys.process._
import java.net.URL
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

object DownloadIntraday extends App { 

	if(args.length < 1){
		println("Usage: DownloadIntraday <industry>")
		System.exit(0)
	}
	

	def fileDownloader(url: String, filename: String) = {		
	    new URL(url) #> new File(filename) !!
	}			


	var companiesList = scala.io.Source.fromFile("CompaniesList.csv")
	var lines = companiesList.getLines.toArray.drop(1)//get rid of the column headers
	companiesList.close() //close the file handle

	println("Processing following number of lines: " + lines.length)

	val now = Calendar.getInstance().getTime()
	val dateFormat = new SimpleDateFormat("yyyy.MM.dd")
	val date = dateFormat.format(now)
	val saveLocation = "downloads/" + date
	val dir = new File(saveLocation);

	println("Save location: " + saveLocation)

	if(dir.exists || dir.mkdir()){
		println("Created directory: " + dir)
		lines.foreach(line => {
			
			//(0)Security Code,(1)Security Id,(2)Security Name, (3)Status, (4)Group, (5)Face Value, (6)ISIN No, (7)Industry, (8)Instrument
			var columns = line.split(",")
			if(columns.length>8){
				var symbol = columns(0)
				var status = columns(3)
				var industry = columns(7)
				if(args.contains(industry) && status == "Active"){	
					println("downloading for: " + symbol)
					var outFile = saveLocation + "/" + symbol + "_" + date + ".json"					

					var downloadURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&interval=1min&apikey=KCYTWW26U89FJ2BC&outputsize=full&symbol=" + symbol
					//println("downloadURL: " + downloadURL)

					fileDownloader(downloadURL,outFile)
					Thread.sleep(2000)
				}
			}
		})
	}


}