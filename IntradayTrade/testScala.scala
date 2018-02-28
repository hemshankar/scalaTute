import java.io.PrintWriter
var parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
var parser = parserFactory.newSAXParser()
var source = new org.xml.sax.InputSource("http://www.moneycontrol.com/stocks/marketinfo/price2bv/nse/index.html")
var adapter = new scala.xml.parsing.NoBindingFactoryAdapter
var index = adapter.loadXML(source, parser)
var out = new PrintWriter("out.txt")
var nseSectorList = (index \\ "a").filter(x => (x \ "@class").toString == "opt_notselected").map(y => y.text + ",http://www.moneycontrol.com" + (y \ "@href").toString).mkString("\n")
//println(nseSectorList)
out.write(nseSectorList)
out.close()
