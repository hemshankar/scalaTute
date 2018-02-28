import java.io.PrintWriter
var parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
var parser = parserFactory.newSAXParser()
var source = new org.xml.sax.InputSource("http://www.moneycontrol.com/india/stockpricequote/banks-public-sector/statebankindia/SBI")
var adapter = new scala.xml.parsing.NoBindingFactoryAdapter


for( i <- 1 until 1000) {
	
	Thread.sleep(200)
	var sbiPage = adapter.loadXML(source, parser)

	//<span class="PA2" id="Nse_Prc_tick"><strong>277.25</strong></span></div>
	var sbi = (sbiPage \\ "span").filter(x => (x \ "@id").toString == "Nse_Prc_tick").text
	println(sbi)
}




/*var out = new PrintWriter("out.txt")-
out.write(nseSectorList)
out.close()*/
