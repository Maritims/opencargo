[![Java CI with Maven](https://github.com/Maritims/opencargo/actions/workflows/maven.yml/badge.svg)](https://github.com/Maritims/opencargo/actions/workflows/maven.yml)

# OpenCargo

This project attempts to determine the applicability of freight products based on rules configured within the system,
and a productQuery from a consumer.

## What does it do?

These are the things OpenCargo CAN do for you.

- OpenCargo helps you determine the most suitable freight product for a consumer based on the rules you configure.

## What does it not do?

These are the things OpenCargo CANNOT do for you.

- OpenCargo cannot figure out how to pack your cargo to determine the most optimal means of cargo delivery.
- OpenCargo cannot figure out the cost of cargo delivery.

## Technical details

These are some technical details which you as a user of this library might find interesting.

### Serialization and deserialization

The various domain entities within this system may be expressed as XML in their respective files within the
`src/main/resources` area. These files are deserialized using JAXB.

### Domain entities

There are various domain entities within this system.

- `Consignor`: A consignor is an entity which provides one or more freight services. These freight services are
  represented as products. A consignor does not concern itself with the products it provides, that is the responsibility
  of the product registry.
- `Product`: A product represents a freight service provided by a consignor. The product doesn ot concern itself with
  its applicability based on a consumer's productQuery, that is the concern of the rules registry.
- `Address`: An address represents a geographical location to which cargo should be delivered.
- `Query`: A productQuery is submitted by a consumer with the consumer's cargo to be delivered and the consumer's address to
  which the cargo should be delivered.
- `Rule`: A rule describes the constraints to evaluate in to determine a product's applicability based on a consumer's productQuery.

### Rules

There are many different types of rules. A rule can consider geography, dimensions, monetary value or anything you may
find relevant. However, some of what you may be able to imagine might not be implemented yet.

#### Types of rules

- `Geography`: Uses country codes and/or postal codes to determine whether a productQuery's delivery address is relevant.
- `Width`, `Length` and `Height`: Either of these rules can be applied to require a minimum and/or maximum value for the
  corresponding cargo dimension. These rules must not be confused as being one combined rule. They are separate rules:
  width, length, height.
- `LengthGirth`: Determine whether the cargo exceeds either a maximum length or a maximum combined length and girth.
- `MinDimensions`: Determine whether the cargo meets the required minimum dimensions. This rule supports rotation so
  that the orientation
  of the cargo is irrelevant to the system.
- `MonetaryValue`: Require a minimum and/or maximum monetary value based on the cargo content.
- `Weight`: Require a minimum and/or maximum cargo weight.