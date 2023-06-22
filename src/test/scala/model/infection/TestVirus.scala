package model.infection

import model.infection.{InfectionLogic, InternalInfectionLogic}
import model.world.{BasicRegion, Region}
import org.junit.Assert.assertEquals
import org.junit.{Before, Test}

class TestVirus {
  val testVirusConfiguration: VirusConfiguration = VirusConfiguration("DHT11", 0, 0, 0, 0, 0, 0, 0, false, false)
  val virus: Virus = new BasicVirus(testVirusConfiguration)
  @Test
  def testVirusName: Unit =
    assertEquals(testVirusConfiguration.name, virus.name)

  @Test
  def testVirusColdRegionsInfectivity: Unit =
    assertEquals(testVirusConfiguration.coldRegionsInfectivity, virus.coldRegionsInfectivity)

  @Test
  def testVirusWarmRegionsInfectivity: Unit =
    assertEquals(testVirusConfiguration.warmRegionsInfectivity, virus.warmRegionsInfectivity)

  @Test
  def testVirusLowDensityRegionInfectivity: Unit =
    assertEquals(testVirusConfiguration.lowDensityRegionInfectivity, virus.lowDensityRegionInfectivity)

  @Test
  def testVirusHighDensityRegionsInfectivity: Unit =
    assertEquals(testVirusConfiguration.highDensityRegionsInfectivity, virus.highDensityRegionsInfectivity)

  @Test
  def testVirusRichRegionsInfectivity: Unit =
    assertEquals(testVirusConfiguration.richRegionsInfectivity, virus.richRegionsInfectivity)

  @Test
  def testVirusPoorRegionsInfectivity: Unit =
    assertEquals(testVirusConfiguration.poorRegionsInfectivity, virus.poorRegionsInfectivity)

  @Test
  def testVirusVaccineResistance: Unit =
    assertEquals(testVirusConfiguration.vaccineResistance, virus.vaccineResistance)

  @Test
  def testVirusAirportEnabled: Unit =
    assertEquals(testVirusConfiguration.airportEnabled, virus.airportEnabled)

  @Test
  def testVirusPortEnabled: Unit =
    assertEquals(testVirusConfiguration.portEnabled, virus.portEnabled)

}