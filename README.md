[![Java CI with Maven](https://github.com/Maritims/opencargo/actions/workflows/maven.yml/badge.svg)](https://github.com/Maritims/opencargo/actions/workflows/maven.yml)

# OpenCargo

OpenCargo is a library which tries to solve two fundamental problems in freight management:

- Determining which services are capable of handling a shipment.
- Calculating the cost of delivery.

## Use cases

### Finding eligible products

To find which freight products are capable of handling a parcel, the parcel's properties are checked against the
defined constraints. Failure to satisfy any of the constraints will result in the freight product being excluded.

#### Defining constraints

The constraint system is XML-based and is defined by an XSD file generated while building the `opencargo-infrastructure` project.

The following example shows how to define the constraints for the Bring product "Pakke til bedrift" as described by [https://www.bring.no/tjenester/pakker-og-gods/bedrifter-nasjonalt/pakke-til-bedrift](https://www.bring.no/tjenester/pakker-og-gods/bedrifter-nasjonalt/pakke-til-bedrift). Remember, failure to satisfy any of the constraints will result in the product being excluded.

```xml
<!-- The id is typically the product number in the carrier's system. -->
<product id="5800">
    <name>Pakke til hentested</name>
    <constraints>
        <!-- The parcel cannot exceed 35 kg in weight. -->
        <max-weight unit="KILOGRAM">35.0</max-weight>
        <!-- The "any" constraint requires that the parcel matches any of its contained constraints, regardless of whether it's one, multiple or all. -->
        <any>
            <!-- The parcel cannot exceed 240 cm in length. -->
            <max-length unit="CENTIMETER">240</max-length>
            <!-- The parcel cannot exceed 360 cm in length plus its girth. -->
            <max-length-plus-girth unit="CENTIMETER">360</max-length-plus-girth>
        </any>
        <!-- The min-dimensions constraint will rotate the parcel in every orientation to verify eligibility, even though we're using the terms width, length and height here -->
        <min-dimensions>
            <width unit="CENTIMETER">15</width>
            <length unit="CENTIMETER">10</length>
            <height unit="CENTIMETER">1</height>
        </min-dimensions>
    </constraints>
    <capabilities>
        <!-- The freight product can handle the following directives defined in the ISO 780 standard. -->
        <handling-directives>
            <directive>FRAGILE</directive>
            <directive>THIS_WAY_UP</directive>
            <directive>STACKING_LIMIT_BY_MASS</directive>
            <directive>STACKING_LIMIT_BY_NUMBER</directive>
            <directive>DO_NOT_STACK</directive>
        </handling-directives>
    </capabilities>
</product>
```