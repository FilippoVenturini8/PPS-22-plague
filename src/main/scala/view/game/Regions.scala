package view.game

import controller.GameEngine
import model.world.Region

import javax.swing.{JPanel, JProgressBar}
import java.awt.{Color, Component, Dimension}
import javax.swing.plaf.basic.BasicProgressBarUI
import javax.swing.*
import scala.collection.immutable.SortedMap
import javax.swing.ScrollPaneConstants

object Regions:

  class RegionsPanel(gameEngine: GameEngine) extends JPanel:
    this.showAllRegionsDetails()

    private def setNewPanel(panel: JPanel): Unit =
      this.removeAll()
      this.add(panel)
      this.revalidate()
      this.repaint()


    def showAllRegionsDetails(): Unit = this.setNewPanel(new AllRegionsPanel(gameEngine.getRegions()))

    def showRegionDetails(region: Region): Unit = this.setNewPanel(DumbPanel(region.name))

  class AllRegionsPanel(var regions: List[Region]) extends JPanel :
    var progressBars: Map[Region, JProgressBar] = SortedMap()
    val backgroundColor: Color = new Color(217, 217, 217)
    val barBackgroundColor: Color = new Color(35, 187, 197)
    val barForegroundColor: Color = new Color(215, 19, 19)
    this.setBackground(backgroundColor)
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))

    regions.foreach(r => progressBars = progressBars + (r -> new DecimalProgressBar()))

    progressBars.foreach((r, p) => {
      p.setMaximum(r.population)
      this.configureProgressBarLayout(p)
      this.add(Box.createRigidArea(new Dimension(320, 30)))
      val regionNameLabel: JLabel = new JLabel(r.name, SwingConstants.CENTER)
      regionNameLabel.setBackground(new Color(255, 0, 0))
      this.add(regionNameLabel)
      this.add(p)
    })

    def configureProgressBarLayout(progressBar: JProgressBar): Unit =
      progressBar.setBackground(barBackgroundColor)
      progressBar.setForeground(barForegroundColor)
      progressBar.setStringPainted(true)
      progressBar.setUI(new BasicProgressBarUI() {
        override def getSelectionBackground: Color = Color.white

        override def getSelectionForeground: Color = Color.white
      })

    def updateRegions(updatedRegions: List[Region]): Unit =
      updatedRegions.foreach(region => progressBars(region).setValue(region.infectedAmount))

  class DecimalProgressBar extends JProgressBar :
    override def setValue(n: Int): Unit =
      super.setValue(n)
      this.setString(BigDecimal(100.0 * n / this.getMaximum).setScale(2, BigDecimal.RoundingMode.CEILING).toString + "%")

  case class DumbPanel(str: String) extends JPanel:
    this.add(JLabel(str))

  case class WrapWithScrollBar(component: Component) extends JScrollPane:
    this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED)
    this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
    this.getVerticalScrollBar().setUnitIncrement(10);
    this.setViewportView(component)