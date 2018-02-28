import scala.io.Source
import scala.xml._
import java.io.PrintWriter


var parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
var parser = parserFactory.newSAXParser()
var adapter = new scala.xml.parsing.NoBindingFactoryAdapter
var filterWords = Set("Book Value","PBV Ratio")

def readCompalyList(sector: String, url: String) : Unit = {
	var sect = sector.toArray.map(x => if(x == '&') '-' else x).mkString 
	var source = new org.xml.sax.InputSource(url)	
	var sectorPage = adapter.loadXML(source, parser) 
	var out = new PrintWriter(sect + ".txt") 
	//(sectorPage \\ "a").filter(x => (x \ "@class").toString == "bl_12")
	//var compTable = findTagsWithAttribute(sectorPage,"table","class","tbldata14 bdrtpg")
	//var companies = compTable.map(x => findTagsWithAttribute(x,"a","class","bl_12").map(y => (y \ "b").mkString + ",http://www.moneycontrol.com" + (y \ "@href").toString).mkString("\n") )
	var firstPass = findTagsWithAttribute(sectorePage,"a","class","bl_12")
	var secondPass = firstPass.filter(!filterWords.contains(_.text))
	var companiyList = secondPass.map(y => (y \ "b").text + ",http://www.moneycontrol.com" + (y \ "@href").toString).mkString("\n") 
	//println(nseSectorList) |
	out.write(companiyList) 
	out.close() 
}

def findTagsWithAttribute(node:Node, tag: String, name: String, value: String) : NodeSeq = {
	(node \\ tag).filter(x => (x \ s"@$name").toString == value)
}


//Read the sectorlist File
var sectors = Source.fromFile("C:/D Drive/Projects/tradingAutomation/bseSectorsList.txt")
//var sectorList = Source.fromFile("C:/D Drive/Projects/tradingAutomation/nseSectorsList.txt")
var lines = sectors.getLines.toVector.par

//get the Url for each sector
lines.foreach(x => {
	var arr = x.split(",")
	readCompalyList(arr(0), arr(1))
	})
//get the URL content from the link 

//get the list of compaines listed and their details listed