package view.menu

import controller.MenuController

import java.awt.{BasicStroke, BorderLayout, Color, Dimension, Graphics, Graphics2D, GridLayout, Image, Cursor}
import javax.swing.{ImageIcon, JButton, JLabel, JPanel}
import model.powerUp.{PowerUp, PowerUpManager, PowerUpType}
import model.infection.{BasicVirus, ColdRegionsInfectivity, Virus, VirusConfiguration}
import model.dnapoints.DnaPoints.DnaPointsHandler

import java.awt.event.{ActionEvent, ActionListener}
import model.dnapoints.DnaPoints.Logic.EmptyLogic

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.border.EmptyBorder


case class ButtonPowerUp(button: JButton, powerUp: PowerUp)


class PowerUpsGridPanel(powerUpDetailsPanel: PowerUpDetailsPanel, menuController: MenuController) extends JPanel:
  this.setBorder(new EmptyBorder(30,30,30,30))
  powerUpDetailsPanel.refreshPowerUpInformation(menuController.getPowerUp(PowerUpType.ColdResistanceI).get, true)
  val buttonPanel: JPanel = new JPanel()

  val numRows = 4
  val numCols = 4
  this.setLayout(new GridLayout(numRows, numCols, 50, 50))
  var buttons: List[ButtonPowerUp] = List.empty[ButtonPowerUp]


  val btnColdResistanceI: ButtonPowerUp = ButtonPowerUp(new JButton("Cold Resistance I"), menuController.getPowerUp(PowerUpType.ColdResistanceI).get)
  val powerUpColdResistanceI: PowerUp = menuController.getPowerUp(PowerUpType.ColdResistanceI).get
  btnColdResistanceI.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpColdResistanceI, this.menuController.powerUpAvailableForPurchase(powerUpColdResistanceI)))
  btnColdResistanceI.button.setPreferredSize(new Dimension(50, 50))
  btnColdResistanceI.button.setMinimumSize(new Dimension(50, 50))
  this.add(btnColdResistanceI.button)
  buttons = buttons.appended(btnColdResistanceI)

  val btnInfectedDrinkingWater: ButtonPowerUp = ButtonPowerUp(new JButton("Infected Drinking Water"), menuController.getPowerUp(PowerUpType.InfectedDrinkingWater).get)
  val powerUpInfectedDrinkingWater: PowerUp = menuController.getPowerUp(PowerUpType.InfectedDrinkingWater).get
  btnInfectedDrinkingWater.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpInfectedDrinkingWater, this.menuController.powerUpAvailableForPurchase(powerUpInfectedDrinkingWater)))
  this.add(btnInfectedDrinkingWater.button)
  buttons = buttons.appended(btnInfectedDrinkingWater)

  val btnHotResistanceI: ButtonPowerUp = ButtonPowerUp(new JButton("Hot Resistance I"), menuController.getPowerUp(PowerUpType.HotResistanceI).get)
  val powerHotResistanceI: PowerUp = menuController.getPowerUp(PowerUpType.HotResistanceI).get
  btnHotResistanceI.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerHotResistanceI, true))
  this.add(btnHotResistanceI.button)
  buttons = buttons.appended(btnHotResistanceI)

  val btnAlphaMutations: ButtonPowerUp = ButtonPowerUp(new JButton("Alpha Mutations"), menuController.getPowerUp(PowerUpType.AlphaMutations).get)
  val powerAlphaMutations: PowerUp = menuController.getPowerUp(PowerUpType.AlphaMutations).get
  btnAlphaMutations.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerAlphaMutations, this.menuController.powerUpAvailableForPurchase(powerAlphaMutations)))
  this.add(btnAlphaMutations.button)
  buttons = buttons.appended(btnAlphaMutations)


  val btnColdResistanceII: ButtonPowerUp = ButtonPowerUp(new JButton("Cold Resistance II"), menuController.getPowerUp(PowerUpType.ColdResistanceII).get)
  val powerUpColdResistanceII: PowerUp = menuController.getPowerUp(PowerUpType.ColdResistanceII).get
  btnColdResistanceII.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpColdResistanceII, this.menuController.powerUpAvailableForPurchase(powerUpColdResistanceII)))
  this.add(btnColdResistanceII.button)
  buttons = buttons.appended(btnColdResistanceII)

  val btnInfectionThroughAnimals: ButtonPowerUp = ButtonPowerUp(new JButton("Infection Through Animals"), menuController.getPowerUp(PowerUpType.InfectionThroughAnimals).get)
  val powerUpInfectionThroughAnimals: PowerUp = menuController.getPowerUp(PowerUpType.InfectionThroughAnimals).get
  btnInfectionThroughAnimals.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpInfectionThroughAnimals, this.menuController.powerUpAvailableForPurchase(powerUpInfectionThroughAnimals)))
  this.add(btnInfectionThroughAnimals.button)
  buttons = buttons.appended(btnInfectionThroughAnimals)

  val btnHotResistanceII: ButtonPowerUp = ButtonPowerUp(new JButton("Hot Resistance II"), menuController.getPowerUp(PowerUpType.HotResistanceII).get)
  val powerUpHotResistanceII: PowerUp = menuController.getPowerUp(PowerUpType.HotResistanceII).get
  btnHotResistanceII.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpHotResistanceII, this.menuController.powerUpAvailableForPurchase(powerUpHotResistanceII)))
  this.add(btnHotResistanceII.button)
  buttons = buttons.appended(btnHotResistanceII)

  val btnBetaMutations: ButtonPowerUp = ButtonPowerUp(new JButton("Beta Mutations"), menuController.getPowerUp(PowerUpType.BetaMutations).get)
  val powerUpBetaMutations: PowerUp = menuController.getPowerUp(PowerUpType.BetaMutations).get
  btnBetaMutations.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpBetaMutations, this.menuController.powerUpAvailableForPurchase(powerUpBetaMutations)))
  this.add(btnBetaMutations.button)
  buttons = buttons.appended(btnBetaMutations)


  val btnAirportEnablement: ButtonPowerUp = ButtonPowerUp(new JButton("Airport Enablement"), menuController.getPowerUp(PowerUpType.AirportEnablement).get)
  val powerUpAirportEnablement: PowerUp = menuController.getPowerUp(PowerUpType.AirportEnablement).get
  btnAirportEnablement.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpAirportEnablement, this.menuController.powerUpAvailableForPurchase(powerUpAirportEnablement)))
  this.add(btnAirportEnablement.button)
  buttons = buttons.appended(btnAirportEnablement)

  val btnBacterialResistance: ButtonPowerUp = ButtonPowerUp(new JButton("Bacterial Resistance"), menuController.getPowerUp(PowerUpType.BacterialResistance).get)
  val powerUpBacterialResistance: PowerUp = menuController.getPowerUp(PowerUpType.BacterialResistance).get
  btnBacterialResistance.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpBacterialResistance, this.menuController.powerUpAvailableForPurchase(powerUpBacterialResistance)))
  this.add(btnBacterialResistance.button)
  buttons = buttons.appended(btnBacterialResistance)

  val btnOmegaMutations: ButtonPowerUp = ButtonPowerUp(new JButton("Omega Mutations"), menuController.getPowerUp(PowerUpType.OmegaMutations).get)
  val powerUpOmegaMutations: PowerUp = menuController.getPowerUp(PowerUpType.OmegaMutations).get
  btnOmegaMutations.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpOmegaMutations, this.menuController.powerUpAvailableForPurchase(powerUpOmegaMutations)))
  this.add(btnOmegaMutations.button)
  buttons = buttons.appended(btnOmegaMutations)

  val btnGammaMutations: ButtonPowerUp = ButtonPowerUp(new JButton("Gamma Mutations"), menuController.getPowerUp(PowerUpType.GammaMutations).get)
  val powerUpGammaMutations: PowerUp = menuController.getPowerUp(PowerUpType.GammaMutations).get
  btnGammaMutations.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpGammaMutations, this.menuController.powerUpAvailableForPurchase(powerUpGammaMutations)))
  this.add(btnGammaMutations.button)
  buttons = buttons.appended(btnGammaMutations)


  val btnMedicinesResistance: ButtonPowerUp = ButtonPowerUp(new JButton("Medicines Resistance"), menuController.getPowerUp(PowerUpType.MedicinesResistance).get)
  val powerUpMedicinesResistance: PowerUp = menuController.getPowerUp(PowerUpType.MedicinesResistance).get
  btnMedicinesResistance.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpMedicinesResistance, this.menuController.powerUpAvailableForPurchase(powerUpMedicinesResistance)))
  this.add(btnMedicinesResistance.button)
  buttons = buttons.appended(btnMedicinesResistance)

  val btnInfectionThroughRespiratoryTract: ButtonPowerUp = ButtonPowerUp(new JButton("Infection Through Respiratory Tract"), menuController.getPowerUp(PowerUpType.InfectionThroughRespiratoryTract).get)
  val powerUpInfectionThroughRespiratoryTract: PowerUp = menuController.getPowerUp(PowerUpType.InfectionThroughRespiratoryTract).get
  btnInfectionThroughRespiratoryTract.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpInfectionThroughRespiratoryTract, this.menuController.powerUpAvailableForPurchase(powerUpInfectionThroughRespiratoryTract)))
  this.add(btnInfectionThroughRespiratoryTract.button)
  buttons = buttons.appended(btnInfectionThroughRespiratoryTract)

  val btnPortEnablement: ButtonPowerUp = ButtonPowerUp(new JButton("Port Enablement"), menuController.getPowerUp(PowerUpType.PortEnablement).get)
  val powerUpPortEnablement: PowerUp = menuController.getPowerUp(PowerUpType.PortEnablement).get
  btnPortEnablement.button.addActionListener((e: ActionEvent) => powerUpDetailsPanel.refreshPowerUpInformation(powerUpPortEnablement, this.menuController.powerUpAvailableForPurchase(powerUpPortEnablement)))
  this.add(btnPortEnablement.button)
  buttons = buttons.appended(btnPortEnablement)

  buttons.foreach(btn => btn.button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))

  this.setOpaque(false)
  this.refreshPowerUpGridPanel()

  override def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    drawLineBetweenPowerUpsButton(btnColdResistanceI, btnColdResistanceII, g)
    drawLineBetweenPowerUpsButton(btnHotResistanceI, btnHotResistanceII, g)
    drawLineBetweenPowerUpsButton(btnInfectedDrinkingWater, btnInfectionThroughAnimals, g)
    drawLineBetweenPowerUpsButton(btnAlphaMutations, btnBetaMutations, g)
    drawLineBetweenPowerUpsButton(btnColdResistanceII, btnBacterialResistance, g)
    drawLineBetweenPowerUpsButton(btnHotResistanceII, btnBacterialResistance, g)
    drawLineBetweenPowerUpsButton(btnBetaMutations, btnGammaMutations, g)
    drawLineBetweenPowerUpsButton(btnOmegaMutations, btnGammaMutations, g)
    drawLineBetweenPowerUpsButton(btnMedicinesResistance, btnInfectionThroughRespiratoryTract, g)

  def drawLineBetweenPowerUpsButton(firstPowerUpButton: ButtonPowerUp, secondPowerUpButton: ButtonPowerUp, g: Graphics): Unit =
    val g2d = g.asInstanceOf[Graphics2D]
    g2d.setStroke(new BasicStroke(3))
    g2d.drawLine(firstPowerUpButton.button.getLocation().x + firstPowerUpButton.button.getWidth / 2, firstPowerUpButton.button.getLocation().y + firstPowerUpButton.button.getHeight / 2,
      secondPowerUpButton.button.getLocation().x + secondPowerUpButton.button.getWidth / 2, secondPowerUpButton.button.getLocation().y + secondPowerUpButton.button.getHeight / 2)

  def refreshPowerUpGridPanel(): Unit =
    buttons.filter(btnPowerUp => menuController.powerUpAvailableForPurchase(btnPowerUp.powerUp)).foreach(btnPowerUp => {
      btnPowerUp.button.setBackground(new Color(196, 29, 29))
      btnPowerUp.button.setOpaque(true)
      btnPowerUp.button.setBorderPainted(false)
    })
    buttons.filter(btnPowerUp => menuController.powerUpsAlreadyPurchasedPowerUps.contains(btnPowerUp.powerUp)).foreach(btnPowerUp => {
      btnPowerUp.button.setBackground(new Color(42, 29, 196))
      btnPowerUp.button.setOpaque(true)
      btnPowerUp.button.setBorderPainted(false)
      btnPowerUp.button.setForeground(Color.WHITE)
    })
    buttons.filter(btnPowerUp => !menuController.powerUpsAlreadyPurchasedPowerUps.contains(btnPowerUp.powerUp) &&
      !menuController.powerUpAvailableForPurchase(btnPowerUp.powerUp)).foreach(btnPowerUp => {
      btnPowerUp.button.setBackground(new Color(217, 217, 217))
      btnPowerUp.button.setOpaque(true)
      btnPowerUp.button.setBorderPainted(false)
    })
