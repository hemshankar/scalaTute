import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.Includes._
import scalafx.event._
val app = new JFXApp {
    stage = new JFXApp.PrimaryStage {
        title = "First GUI"
		scene= new Scene(400,600){
			val button = new Button("Remove")
			button.layoutX=100
			button.layoutX=50
			
			val combo = new ComboBox(List("Oracle","Enlightiks","Infa"))
			combo.layoutX = 200
			combo.layoutY = 200
			
			val lstView = new ListView(List("Oracle","Enlightiks","Infa"))
			lstView.layoutX = 200
			lstView.layoutY = 400
			lstView.prefHeight = 150
			
			button.onAction = (e:ActionEvent) => {
				println("Button pressed")
				val selected = lstView.selectionModel.apply.getSelectedItems
				lstView.items = lstView.items.apply.diff(selected)
			} 
			
			combo.onAction = (e:ActionEvent) => {
				lstView.items.apply += combo.selectionModel.apply.getSelectedItem
			} 
						
			content = List(button,combo,lstView)
		}
    }
}

app.main(args)

