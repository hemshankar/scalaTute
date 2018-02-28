import scala.io.Source
import scala.xml._
import java.io.PrintWriter


var parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
var parser = parserFactory.newSAXParser()
var adapter = new scala.xml.parsing.NoBindingFactoryAdapter
var filterWords = Set("Book Value","PBV Ratio")
var count = 0
var totalCompanies = 0
def findTagsWithAttribute(node:Node, tag: String, name: String, value: String) : NodeSeq = {
	(node \\ tag).filter(x => (x \ s"@$name").toString == value)
}

def readCompanyList(sector: String, url: String) : Unit = {
	count += 1
	print(s"$count=====================Fetching: $sector")
	var sect = sector.toArray.map(x => if(x == '&') '-' else x).mkString 
	var source = new org.xml.sax.InputSource(url)	
	var sectorPage = adapter.loadXML(source, parser) 
	

	var firstPass = findTagsWithAttribute(sectorPage,"a","class","bl_12")
	var secondPass = firstPass.filter(x => !filterWords.contains(x.text))
	var noOfComp = secondPass.length
	totalCompanies = totalCompanies + noOfComp
	println(s"   Total: ${noOfComp} companies")
	var companiyList = secondPass.map(y => (y \ "b").text + ",http://www.moneycontrol.com" + (y \ "@href").toString).mkString("\n") 
	//println(nseSectorList) |
	var out = new PrintWriter("C:/D Drive/Projects/tradingAutomation/companies/nse/" + sect + s"__$noOfComp.txt") 
	out.write(companiyList) 
	out.close() 
}



//Read the sectorlist File
var sectors = scala.io.Source.fromFile("C:/D Drive/Projects/tradingAutomation/nseSectorsList.txt")
//var sectorList = Source.fromFile("C:/D Drive/Projects/tradingAutomation/nseSectorsList.txt")
var lines = sectors.getLines.toArray

//get the Url for each sector
lines.foreach(x => {
	var arr = x.split(",")
	readCompanyList(arr(0), arr(1))
	println(s"\n\nListed $totalCompanies Companies")
	})
//get the URL content from the link 

//get the list of compaines listed and their details listed