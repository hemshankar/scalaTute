// Extract the Symbol name



//Read the sectorlist File
var sectors = scala.io.Source.fromFile("C:/D Drive/Projects/tradingAutomation/nseSectorsList.txt")
//var sectorList = Source.fromFile("C:/D Drive/Projects/tradingAutomation/nseSectorsList.txt")
var lines = sectors.getLines.toArray

//get the Url for each sector
var sector = ""
lines.foreach(x => {
	sector = x.split(",").toArray.map(x => if(x == '&') '-' else x).mkString
	var comps = getCompanies(sector) //List[Strings]
	comps.froeach( comp => {
		var compArr = comp.split(",")
		var compName = compArr(0)
		var compURL = compArr(0)
		})
	println(s"\n\nListed $totalCompanies Companies")
	})



def getCompanies(sector: String, url: String) : Unit = {
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