package model.dnapoints

import model.world.World
import model.world.{BasicRegion, Region, World}
import org.junit.{Before, Test}
import org.junit.Assert.{assertEquals, assertFalse, assertTrue}
import model.world.RegionParameters.RegionConfiguration
import model.dnapoints.DnaPoints.Logic.{BasicLogic, EmptyLogic, EveryXSecondsLogic, OnNewInfectedRegions, OnNewInfectedRegionsLogic, SpawnPointLogic}
import model.dnapoints.DnaPoints.{DnaPoint, DnaPointsHandler}

class TestLogic {

  private var russia: Region = BasicRegion(RegionConfiguration("Russia", 0, 0, 0, 0, 0, 0))
  private var usa: Region = BasicRegion(RegionConfiguration("USA", 0, 0, 0, 0, 0, 0))
  private var europe: Region = BasicRegion(RegionConfiguration("Europe", 0, 0, 0, 0, 0, 0))
  private var china: Region = BasicRegion(RegionConfiguration("China", 0, 0, 0, 0, 0, 0))
  private var brazil: Region = BasicRegion(RegionConfiguration("Brazil", 0, 0, 0, 0, 0, 0))
  private var world: World = new World(List(russia, usa, europe, china, brazil))
  private val refreshTime: Int = 1;

  @Before
  def before(): Unit =
    russia = BasicRegion(RegionConfiguration("Russia", 0, 0, 0, 0, 0, 0))
    usa = BasicRegion(RegionConfiguration("USA", 0, 0, 0, 0, 0, 0))
    europe = BasicRegion(RegionConfiguration("Europe", 0, 0, 0, 0, 0, 0))
    china = BasicRegion(RegionConfiguration("China", 0, 0, 0, 0, 0, 0))
    brazil = BasicRegion(RegionConfiguration("Brazil", 0, 0, 0, 0, 0, 0))
    world = new World(List(russia, usa, europe, china, brazil))

  @Test
  def testOnNewInfectedRegionsWithNoInfectedCountries(): Unit =
    val logic: SpawnPointLogic = OnNewInfectedRegionsLogic(world)
    assertEquals(Set(), logic.evaluate())

  @Test
  def testOnNewInfectedRegionsWithOneInfectedCountry(): Unit =
    val logic: SpawnPointLogic = OnNewInfectedRegionsLogic(world)
    russia.infectedAmount = 1
    assertEquals(Set(russia), logic.evaluate())

  @Test
  def testOnNewInfectedRegionsWithMultipleInfectedCountries(): Unit =
    val logic: SpawnPointLogic = OnNewInfectedRegionsLogic(world)
    russia.infectedAmount = 1
    usa.infectedAmount = 100
    assertEquals(Set(russia, usa), logic.evaluate())

  @Test
  def testOnNewInfectedRegionsCountriesAreDetectedOnlyOnce(): Unit =
    val logic: SpawnPointLogic = OnNewInfectedRegionsLogic(world)
    russia.infectedAmount = 1
    logic.evaluate()
    russia.infectedAmount = 100
    assertEquals(Set(), logic.evaluate())

  @Test
  def testEveryXSecondsWithNoInfectedCountries(): Unit =
    val logic: SpawnPointLogic = EveryXSecondsLogic(world, refreshTime)
    Thread.sleep(refreshTime*1000)
    assertEquals(Set(), logic.evaluate())

  @Test
  def testEveryXSecondsWithOneInfectedCountry(): Unit =
    russia.infectedAmount = 1
    val logic: SpawnPointLogic = EveryXSecondsLogic(world, refreshTime)
    Thread.sleep(refreshTime*1000)
    assertEquals(Set(russia), logic.evaluate())

  @Test
  def testEveryXSecondsWithMultipleInfectedCountries(): Unit =
    russia.infectedAmount = 1
    usa.infectedAmount = 1
    val logic: SpawnPointLogic = EveryXSecondsLogic(world, refreshTime)
    Thread.sleep(refreshTime*1000)
    assertFalse(logic.evaluate().intersect(Set(russia, usa)).isEmpty)

  @Test
  def testEveryXSecondsMultipleTimes(): Unit =
    russia.infectedAmount = 1
    usa.infectedAmount = 1
    val logic: SpawnPointLogic = EveryXSecondsLogic(world, refreshTime)
    for _ <- 0 to 5 do
      Thread.sleep(refreshTime*1000)
      assertFalse(logic.evaluate().intersect(Set(russia, usa)).isEmpty)


  @Test
  def testBasicLogicWithNoInfectedCountries(): Unit =
    val logic: SpawnPointLogic = BasicLogic(world, refreshTime)
    Thread.sleep(refreshTime*1000)
    assertEquals(Set(), logic.evaluate())

  @Test
  def testBasicLogicWithOneInfectedCountry(): Unit =
    russia.infectedAmount = 1
    val logic: SpawnPointLogic = BasicLogic(world, refreshTime)
    assertEquals(Set(russia), logic.evaluate())
    Thread.sleep(refreshTime*1000)
    assertEquals(Set(russia), logic.evaluate())


  @Test
  def testBasicLogicWithMultipleInfectedCountry(): Unit =
    russia.infectedAmount = 1
    val logic: SpawnPointLogic = BasicLogic(world, refreshTime)
    logic.evaluate()
    usa.infectedAmount = 1
    assertEquals(Set(usa), logic.evaluate())
    Thread.sleep(refreshTime*1000)
    assertFalse(logic.evaluate().intersect(Set(russia, usa)).isEmpty)
}
