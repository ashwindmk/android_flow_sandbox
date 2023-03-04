# Flow

Kotlin flow android project.

## Flow
- Regular flow using the flow builder.
- Cold stream.
- Cannot serve multiple consumers because every collect function will create a new flow instance.
- A new flow is created for each consumer.

## State Flow
- Hot stream.
- Can serve multiple consumers simultaneously.

## Shared Flow
- Hot stream.
- Can serve multiple consumers simultaneously.
