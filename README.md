[![Java CI with Maven](https://github.com/Maritims/opencargo/actions/workflows/maven.yml/badge.svg)](https://github.com/Maritims/opencargo/actions/workflows/maven.yml)

# OpenCargo

OpenCargo is a library which tries to solve two fundamental problems in freight management:

- Determining which services are capable of handling a shipment.
- Calculating the cost of cargo delivery based on flexible pricing rules and price modifiers.

## Core features

### Product selection

Before the cost can be calculated, a shipment must be validated against a service's physical and geographic constraints.
OpenCargo filters services based on:

- Dimensions: Minimum and maximum width, length and height.
- Weight: Absolute weight limits per service.
- Geography: Country codes and/or postal codes.
- Girth: Calculations for meeting carrier standards.
- Monetary value: The value of the cargo.

### Pricing policies

Pricing is not hardcoded, it is defined via pricing policies. A policy is a contract containing pricing rules and price
modifiers. The contract is applicable if the pricing query satisfies each of its pricing rules. A pricing query
consists of cargo, a delivery address and a list of products.

- Priority-based matching: The engine sorts policies by priority and then matches the pricing query against the
  policies.
- Detailed rejections: Through the `ApplicabilityReports` model the engine can report on the reasons why a pricing
  query was not applicable.

### Price modifiers

Once a policy has been matched, the engine can apply price modifiers to the pricing query based on "price increasing
circumstances".

- Fixed surcharges: A fixed increase in delivery cost when the cargo contains dangerous goods.
- Dynamic surcharges: A variable increase in delivery cost (e.g. due to increased fuel cost or peak season adjustments).
- Geographic surcharges: Automated increases for delivery to remote areas (e.g. Svalbard).

### Detailed price breakdown

Instead of returning just the calculated total price, OpenCargo returns an instance of the `PriceBreakdown` model. This
model enables you to understand precisely how the system arrived at the result.

### Currently not supported: Optimal packing

OpenCargo is currently **NOT** able to determine the most optimal way to pack your cargo.

## Roadmap

1. Document how to configure selection rules with XML.
2. Document how to configure price modifiers with XML.
3. Document how to configure pricing policies with XML.