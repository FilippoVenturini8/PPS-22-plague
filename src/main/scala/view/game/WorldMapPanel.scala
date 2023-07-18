package view.game

import utils.Iterables.getRandomElement
import controller.GameEngine
import model.configuration.Builders.RegionIdentifier
import model.configuration.Loader
import model.configuration.Loader.{ConfigurationsLoader, RegionIdentifierFile, regionIdentifierFilePath}
import view.game.RegionsView.RegionsPanel
import model.configuration.Loader.ConfigurationsLoader.given
import model.dnapoints.DnaPoints.DnaPoint

import java.awt.event.{MouseEvent, MouseListener}
import java.awt.image.{BufferedImage, DataBufferByte}
import java.awt.{Color, Graphics, image}
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JButton, JPanel}

type HexCode = String
type Position = (Int, Int)

class WorldMapPanel(val gameEngine: GameEngine, val regionsPanel: RegionsPanel) extends JPanel with MouseClickListener:
  val mapImage: BufferedImage = ImageIO.read(getClass().getResource("/map.png"))
  val regions: List[RegionIdentifier] = ConfigurationsLoader.load(RegionIdentifierFile(Loader.regionIdentifierFilePath))
  val pixelStep: Int = 10;
  this.setBackground(new Color(255,255,255))
  this.setLayout(null)
  this.addMouseListener(this)


  val colorsMap: Map[HexCode, Iterable[Position]] = (0 until mapImage.getWidth() by pixelStep)
    .flatMap(i => (0 until mapImage.getHeight() by pixelStep).map(j => (i, j)))
    .map(pos => (pos._1, pos._2, mapImage.getHexCode(pos._1, pos._2)))
    .groupMap(pixel => pixel._3)(pixel => (pixel._1, pixel._2))

  override def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    g.drawImage(mapImage, 0, 0, null)

  def showDnaPoint(dnaPoint: DnaPoint): Unit = regions.find(_.regionName == dnaPoint.regionName) match
    case Some(regionIdentifier) => colorsMap(regionIdentifier.identifier).getRandomElement() match
      case Some(pos) =>
        this.add(this.createDnaPointButton(pos))
        this.repaint()
        this.revalidate()
      case _ =>
    case _ =>

  def createDnaPointButton(position: Position): JButton =
    val btn: JButton = new JButton()
    btn.setBounds(position._1-10, position._2-10, 20, 20)
    btn.setText("+")
    btn

  override def mouseClicked(e: MouseEvent): Unit = regions.find(_.identifier == mapImage.getHexCode(e.getX, e.getY)) match
    case Some(regionIdentifier) =>
      gameEngine.getRegion(regionIdentifier.regionName) match
        case Some(region) => regionsPanel.showRegionDetails(region)
        case _ =>
    case _ => regionsPanel.showAllRegionsDetails()

trait MouseClickListener extends MouseListener:
  override def mousePressed(e: MouseEvent): Unit = {}
  override def mouseExited(e: MouseEvent): Unit = {}
  override def mouseReleased(e: MouseEvent): Unit = {}
  override def mouseEntered(e: MouseEvent): Unit = {}
extension(image: BufferedImage)
  def getHexCode(x: Int, y: Int): HexCode = "#" + image.getRGB(x, y).toHexString.substring(2).toUpperCase
