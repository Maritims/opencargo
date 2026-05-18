[![Java CI with Maven](https://github.com/Maritims/opencargo/actions/workflows/maven.yml/badge.svg)](https://github.com/Maritims/opencargo/actions/workflows/maven.yml)

# OpenCargo

OpenCargo is a library which tries to solve two fundamental problems in freight management:

- Determining which services are capable of handling a shipment.
- Calculating the cost of delivery.

## Use cases

### Finding eligible products

To find which freight products are capable of handling a shipment, the shipment's properties are checked against the
defined rules.

#### Defining rules for eligibility

The rule system is XML-based and is defined by an XSD file generated while building the `opencargo-infrastructure` project.

The following example shows how to define the rules for the Bring product "Pakke til bedrift" as described by [https://www.bring.no/tjenester/pakker-og-gods/bedrifter-nasjonalt/pakke-til-bedrift](https://www.bring.no/tjenester/pakker-og-gods/bedrifter-nasjonalt/pakke-til-bedrift).

```xml

<product id="5800">
    <name>Pakke til hentested</name>
    <constraints>
        <max-weight unit="KILOGRAM">35.0</max-weight>
        <any>
            <max-length unit="CENTIMETER">240</max-length>
            <max-length-plus-girth unit="CENTIMETER">360</max-length-plus-girth>
        </any>
        <min-dimensions>
            <width unit="CENTIMETER">15</width>
            <length unit="CENTIMETER">10</length>
            <height unit="CENTIMETER">1</height>
        </min-dimensions>
    </constraints>
</product>
```