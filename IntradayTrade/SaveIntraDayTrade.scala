
import sys.process._
import java.net.URL
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

def fileDownloader(url: String, filename: String) = {
	//downloadURL = downloadURL + s"&symbol=$symbol"
	//println("URL: " + url)
    new URL(url) #> new File(filename) !!
}

//Read the sectorlist File
var companiesList = scala.io.Source.fromFile("C:/D Drive/Projects/tradingAutomation/companies/nse/EQUITY_L.csv")
//var companiesList = scala.io.Source.fromFile("C:/D Drive/Projects/tradingAutomation/companies/bse/CompaniesList.csv")
//var sectorList = Source.fromFile("C:/D Drive/Projects/tradingAutomation/nsecompaniesListList.txt")
var lines = companiesList.getLines.toArray.drop(1)//get rid of the column headers

companiesList.close()

println("Processing following number of lines: " + lines.length)

val now = Calendar.getInstance().getTime()
val dateFormat = new SimpleDateFormat("yyyy.MM.dd")
val date = dateFormat.format(now)
val saveLocation = "C:/D Drive/Projects/tradingAutomation/companies/intradayTrade/" + date + "/"
val dir = new File(saveLocation);

if(dir.exists || dir.mkdir()){
	lines.foreach(line => {
			
			var symbol = line.split(",")(0)	//"SBIN"
			println("downloading for: " + symbol)
			var outFile = saveLocation + symbol + "_" + date + ".json"					
			
			var downloadURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&interval=1min&apikey=KCYTWW26U89FJ2BC&outputsize=full&symbol=" + symbol//SBIN"
			//"https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&interval=1min&apikey=KCYTWW26U89FJ2BC&outputsize=full&symbol=" + symbol
			//println("downloadURL: " + downloadURL)

			fileDownloader(downloadURL,outFile)

			Thread.sleep(5000)
		})
}
