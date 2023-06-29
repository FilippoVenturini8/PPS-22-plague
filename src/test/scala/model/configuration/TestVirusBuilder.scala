package model.configuration

import model.configuration.Builders.VirusBuilder
import model.infection.VirusConfiguration
import org.junit.Assert.{assertEquals, assertTrue}
import org.junit.{Before, Test}

class TestVirusBuilder {
  val configuration: VirusConfiguration = VirusConfiguration("DHT11", 0, 1, 2, 3, 4, 5, 6, true, true)
  private var virusBuilder = VirusBuilder()

  @Before
  def before(): Unit = {
    virusBuilder = VirusBuilder()
  }

  @Test
  def testColdRegionsInfectivityIsUnsetByDefault: Unit = {
    assertEquals(None, virusBuilder.coldRegionsInfectivity)
  }

  @Test
  def testSetColdRegionsInfectivity: Unit = {
    virusBuilder = virusBuilder.setColdRegionInfectivity(configuration.coldRegionsInfectivity)
    assertEquals(Some(configuration.coldRegionsInfectivity), virusBuilder.coldRegionsInfectivity)
  }

  @Test
  def testWarmRegionsInfectivityIsUnsetByDefault: Unit = {
    assertEquals(None, virusBuilder.warmRegionsInfectivity)
  }

  @Test
  def testSetWarmRegionsInfectivity: Unit = {
    virusBuilder = virusBuilder.setWarmRegionInfectivity(configuration.warmRegionsInfectivity)
    assertEquals(Some(configuration.warmRegionsInfectivity), virusBuilder.warmRegionsInfectivity)
  }

  @Test
  def testLowDensityRegionsInfectivityIsUnsetByDefault: Unit = {
    assertEquals(None, virusBuilder.lowDensityRegionInfectivity)
  }

  @Test
  def testSetLowDensityRegionsInfectivity: Unit = {
    virusBuilder = virusBuilder.setLowDensityInfectivity(configuration.lowDensityRegionInfectivity)
    assertEquals(Some(configuration.lowDensityRegionInfectivity), virusBuilder.lowDensityRegionInfectivity)
  }

}
