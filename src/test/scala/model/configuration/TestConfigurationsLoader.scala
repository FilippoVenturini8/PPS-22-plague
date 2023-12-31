package model.configuration

import model.configuration.builders.RegionIdentifierBuilder.RegionIdentifier
import org.junit.Test
import org.junit.Assert.{assertEquals, assertFalse, assertTrue}
import org.junit.{Before, Test}
import model.configuration.Loader.File.*
import model.configuration.Loader.{ConfigurationsLoader, File, regionFilePath}
import model.configuration.Parsers.Region.RegionParser
import model.configuration.Loader.ConfigurationsLoader.given
import model.world.{AirportRouteManager, PortRouteManager, World}
import model.world.Filters.given
import model.world.RegionParameters.ReachableMode

class TestConfigurationsLoader:

  @Test
  def testNonExistingFileReading(): Unit = {
    assertTrue(File.readLinesFromResources("foo").isEmpty)
  }
  @Test
  def testExistingFileReading(): Unit = {
    assertFalse(File.readLinesFromResources(Loader.regionFilePath).isEmpty)
  }

  @Test
  def testLoadRegions(): Unit = {
    val regionsFile: RegionFile = RegionFile(Loader.regionFilePath)
    assertFalse(ConfigurationsLoader.load(regionsFile).isEmpty)
  }

  @Test
  def testLoadVirus(): Unit = {
    val virusFile: VirusFile = VirusFile(Loader.virusFilePath)
    assertFalse(ConfigurationsLoader.load(virusFile).isEmpty)
  }

  @Test
  def testLoadRoutes(): Unit = {
    val routeFile: RouteFile = RouteFile(Loader.routesFilePath)
    assertFalse(ConfigurationsLoader.load(routeFile).isEmpty)
  }

  @Test
  def testDefaultLoadVirus(): Unit = {
    assertTrue(ConfigurationsLoader.loadVirus().isDefined)
  }

  @Test
  def testAllRegionAreLoaded(): Unit = {
    val numberOfConfigurationLines: Int = File.readLinesFromResources(Loader.regionFilePath).filterNot(_.startsWith("#")).size
    val numberOfRegions: Int = ConfigurationsLoader.loadWorld().getRegions.size
    assertEquals(numberOfConfigurationLines,numberOfRegions)
  }

  @Test
  def testAllBorderRoutesAreLoaded(): Unit = {
    import model.world.Filters.given
    val numberOfConfigurationLines: Int = File.readLinesFromResources(Loader.routesFilePath)
      .filterNot(_.startsWith("#"))
      .count(_.contains(",Border"))
    assertEquals(numberOfConfigurationLines*2, ConfigurationsLoader.loadWorld().getRegions
      .flatMap(r => r.getReachableRegions)
      .count(r => r._2 == ReachableMode.Border))
  }

  @Test
  def testAllPortRoutesAreLoaded(): Unit = {
    import model.world.Filters.given
    val numberOfConfigurationLines: Int = File.readLinesFromResources(Loader.routesFilePath)
      .filterNot(_.startsWith("#"))
      .count(_.contains(",Port"))
    assertEquals(numberOfConfigurationLines * 2, ConfigurationsLoader.loadWorld().getRegions.map(r => PortRouteManager().getAllRoutesOf(r).size).sum)
  }

  @Test
  def testAllAirportRoutesAreLoaded(): Unit = {
    import model.world.Filters.given
    val numberOfConfigurationLines: Int = File.readLinesFromResources(Loader.routesFilePath)
      .filterNot(_.startsWith("#"))
      .count(_.contains(",Airport"))
    assertEquals(numberOfConfigurationLines * 2, ConfigurationsLoader.loadWorld().getRegions.map(r => AirportRouteManager().getAllRoutesOf(r).size).sum)
  }

  @Test
  def testLoadRegionsIdentifiers(): Unit = {
    val numberOfConfigurationLines: Int = File.readLinesFromResources(Loader.regionFilePath)
      .filterNot(_.startsWith("#"))
      .size
    assertEquals(numberOfConfigurationLines, ConfigurationsLoader.load(RegionFile(Loader.regionFilePath)).size)
  }

  @Test
  def testEachRegionHasAnIdentifier(): Unit = {
    assertEquals(ConfigurationsLoader.loadWorld().getRegions.map(r => r.name),
      ConfigurationsLoader.load(RegionFile(Loader.regionFilePath)).map(r => r.name))
  }