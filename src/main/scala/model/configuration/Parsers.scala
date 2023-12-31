package model.configuration


import model.configuration.builders.*
import model.configuration.builders.RegionIdentifierBuilder.RegionIdentifier
import model.configuration.builders.RawRouteBuilder.RawRoute
import model.world.Region
import model.world.RegionParameters.*
import model.configuration.Parsers.Cast.*
import model.configuration.Parsers.RawRoute.RouteConfigurationFileFormat
import model.infection.Virus

object Parsers:

  /**
   * Allows to parse a string to an object of type T
   * @tparam T the parser's return type
   */
  trait Parser[T]:
    /**
     * convert the given string in an object of type T if possible
     * @param line the string to be converted
     * @return an option containing the result if the parsing went well, an empty option otherwise
     */
    def parse(line: String): Option[T]

  /**
   * contains some useful cast operations
   */
  object Cast:
    /**
     * true if the input string contains only digits and underscores
     */
    val canBeInt: String => Boolean = _.replace("_","").toIntOption.isDefined
    /**
     * true if the input string is equal to true or false
     */
    val canBeBool: String => Boolean = _.toBooleanOption.isDefined
    /**
     * convert a string containing digits. Underscores are ignored.
     */
    val castInt: String => Int = _.replace("_","").toInt
    /**
     * convert a string to boolean
     */
    val castBool: String => Boolean = _.toBoolean

  object Region:
    private enum RegionConfigurationFileFormat(val castCondition: String => Boolean, val setter: (RegionBuilder, String) => RegionBuilder):
      case Name extends RegionConfigurationFileFormat(_ => true, (b, s) => b.setName(s))
      case Population extends RegionConfigurationFileFormat(canBeInt, (b, s) => b.setPopulation(castInt(s)))
      case Density extends RegionConfigurationFileFormat(canBeInt, (b, s) => b.setPopulationDensity(castInt(s)))
      case Climate extends RegionConfigurationFileFormat(canBeInt, (b, s) => b.setClimate(castInt(s)))
      case Richness extends RegionConfigurationFileFormat(canBeInt, (b, s) => b.setRichness(castInt(s)))
      case BordersControl extends RegionConfigurationFileFormat(canBeInt, (b, s) => b.setBordersControl(castInt(s)))
      case Globalization extends RegionConfigurationFileFormat(canBeInt, (b, s) => b.setGlobalization(castInt(s)))
      case HasAirport extends RegionConfigurationFileFormat(canBeBool, (b, s) => if castBool(s) then b.addAirport else b)
      case HasPort extends RegionConfigurationFileFormat(canBeBool, (b, s) => if castBool(s) then b.addPort else b)

    /**
     * a parser that is capable to convert strings to regions.
     * To be correctly converted, the string must containing the following parameters separated by comma
     * name,population,density,climate,richness,borderControl,Globalization,HasAirport,HasPort
     */
    trait RegionParser extends Parser[Region]

    object RegionParser:
      /**
       * @return a simple RegionParser
       */
      def apply(): RegionParser = new SimpleRegionParser
      private class SimpleRegionParser extends RegionParser:
        def parse(line: String): Option[Region] =
          val params = line.split(",").toList
          RegionConfigurationFileFormat.values
            .foldLeft(RegionBuilder())((builder, field) => field.ordinal < params.size && field.castCondition.apply(params(field.ordinal)) match
              case true => field.setter.apply(builder, params(field.ordinal))
              case false => builder)
            .build()

  object Virus:
    private enum VirusConfigurationFileFormat(val castCondition: String => Boolean, val setter: (VirusBuilder, String) => VirusBuilder):
      case ColdRegionsInfection extends VirusConfigurationFileFormat(canBeInt, (b, s) => b.setColdRegionInfectivity(castInt(s)))
      case HotRegionsInfection extends VirusConfigurationFileFormat(canBeInt, (b, s) => b.setHotRegionInfectivity(castInt(s)))
      case RichRegionsInfection extends VirusConfigurationFileFormat(canBeInt, (b, s) => b.setRichRegionsInfectivity(castInt(s)))
      case PoorRegionsInfection extends VirusConfigurationFileFormat(canBeInt, (b, s) => b.setPoorRegionsInfectivity(castInt(s)))
      case LowDensityRegionsInfection extends VirusConfigurationFileFormat(canBeInt, (b, s) => b.setLowDensityRegionsInfectivity(castInt(s)))
      case HighDensityRegionInfection extends VirusConfigurationFileFormat(canBeInt, (b, s) => b.setHighDensityRegionsInfectivity(castInt(s)))
      case VaccineResistance extends VirusConfigurationFileFormat(canBeInt, (b, s) => b.setVaccineResistance(castInt(s)))
      case AirportEnabled extends VirusConfigurationFileFormat(canBeBool, (b, s) => b.setAirportEnabled(castBool(s)))
      case PortEnabled extends VirusConfigurationFileFormat(canBeBool, (b, s) => b.setPortEnabled(castBool(s)))

    /**
     * a parser that is capable to convert strings to Virus
     * To be correctly converted, the string must containing the following parameters separated by comma
     * ColdRegionsInfection,HotRegionsInfection,RichRegionsInfection,PoorRegionsInfection,LowDensityRegionsInfection,
     * HighDensityRegionInfection,VaccineResistance,AirportEnabled,PortEnabled
     */
    trait VirusParser extends Parser[Virus]

    object VirusParser:
      /**
       * @return a simple VirusParser
       */
      def apply(): VirusParser = new SimpleVirusParser

      private class SimpleVirusParser extends VirusParser :
        def parse(line: String): Option[Virus] =
          val params = line.split(",").toList
          VirusConfigurationFileFormat.values
            .foldLeft(VirusBuilder())((builder, field) => field.ordinal < params.size && field.castCondition.apply(params(field.ordinal)) match
              case true => field.setter.apply(builder, params(field.ordinal))
              case false => builder)
            .build()

  object RawRoute:

    private enum RouteConfigurationFileFormat(val castCondition: String => Boolean, val setter: (RawRouteBuilder, String) => RawRouteBuilder):
      case NameRegion1 extends RouteConfigurationFileFormat(s => true, (b, s) => b.setNameRegion1(s))
      case NameRegion2 extends RouteConfigurationFileFormat(s => true, (b, s) => b.setNameRegion2(s))
      case Mode extends RouteConfigurationFileFormat(s => ReachableMode.values.exists(_.toString == s), (b, s) => b.setReachableMode(ReachableMode.valueOf(s)))

    /**
     * a parser that is capable to convert strings to RawRoutes
     * To be correctly converted, the string must containing the following parameters separated by comma
     * NameRegion1,NameRegion2,Mode
     */
    trait RawRouteParser extends Parser[RawRoute]
    object RawRouteParser:
      /**
       * @return a simple RawRouteParser
       */
      def apply(): RawRouteParser = new SimpleRawRouteConfigurationParser
      private class SimpleRawRouteConfigurationParser extends RawRouteParser:
        def parse(line: String): Option[RawRoute] =
          val params = line.split(",").toList
          RouteConfigurationFileFormat.values
            .foldLeft(RawRouteBuilder())((builder, field) => field.ordinal < params.size && field.castCondition.apply(params(field.ordinal)) match
              case true => field.setter.apply(builder, params(field.ordinal))
              case false => builder)
            .build()

  object RegionIdentifier:
    private enum RegionIdentifierFileFormat(val castCondition: String => Boolean, val setter: (RegionIdentifierBuilder, String) => RegionIdentifierBuilder):
      case Name extends RegionIdentifierFileFormat(s => true, (b, s) => b.setRegionName(s))
      case Identifier extends RegionIdentifierFileFormat(s => true, (b, s) => b.setIdentifier(s))

    /**
     * a parser that is capable to convert strings to RegionIdentifiers
     * To be correctly converted, the string must containing the following parameters separated by comma
     * Name,Identifier
     */
    trait RegionIdentifierParser extends Parser[RegionIdentifier]

    object RegionIdentifierParser:
      /**
       * @return a simple RegionIdentifierParser
       */
      def apply(): RegionIdentifierParser = new SimpleRegionIdentifierParser

      private class SimpleRegionIdentifierParser extends RegionIdentifierParser :
        def parse(line: String): Option[RegionIdentifier] =
          val params = line.split(",").toList
          RegionIdentifierFileFormat.values
            .foldLeft(RegionIdentifierBuilder())((builder, field) => field.ordinal < params.size && field.castCondition.apply(params(field.ordinal)) match
              case true => field.setter.apply(builder, params(field.ordinal))
              case false => builder)
            .build()
    