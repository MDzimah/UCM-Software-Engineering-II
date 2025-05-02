# SWE 2 Project: Theatre Management Application

## Project Description
A desktop application for managing theatre operations, including patron records, show scheduling, ticket sales, and staff management. The system is implemented using a strict 3-layer architecture, demonstrating key object-oriented and architectural design principles.

### Core Features
- **Patron Management**: Register, remove, and list theatre patrons.
- **Ticket Invoicing**: Create and manage JSON-based invoices for ticket purchases.
- **Show Scheduling**: Organize productions and their showtimes.
- **Seating and Session Management**: Assign seats and manage specific performances.
- **Company and Staff Management**: Maintain company details and box office staff roles.

## Architecture Overview
The application implements a comprehensive set of subsystems required for theatre management:

- **Cliente**: Patron registration, removal, and listing.
- **Factura**: Ticket invoicing and viewing of past purchases.
- **Obra**: Management of theatre productions (titles, descriptions, and categories).
- **Pase**: Show sessions tied to productions, with timing and seat allocation.
- **CompaniaTeatro**: Theatre company administration.
- **MiembroCompaniaTeatro**: Management of company members and their roles.
- **Taquillero**: Functions for box office staff.

All subsystems are organized using a strict 3-layer architecture:

**Presentation Layer → Business Layer → Integration Layer**  
(*GUI & ActionListeners* → *Services & Validation* → *DAOs & JSON Persistence*)

## UML Artifacts
UML modeling (done using **draw.io**) was completed for the following subsystems:
- **Factura** and **Cliente**: Include Class Diagrams, Sequence Diagrams, and Activity Diagrams across all three layers.
- Class diagrams are also provided for the **Factory** classes in the Presentation, Business, and Integration layers, showing how object creation and dependencies are managed.

All UML diagrams are located in `uml/subsystems/`. The top-level `uml-diagrams/` folder remains empty intentionally.

## Technologies
- **Java 11+**
- **Swing** for GUI development
- **Gson** for JSON persistence
- **draw.io** for UML modeling
- **Eclipse IDE** for development

## Design Patterns
This project applies several classical design patterns to maintain modularity and clear responsibilities:

- **Model-View-Controller (MVC)** – Uses a Passive View variation: views are decoupled from the logic, controllers (ActionListeners) mediate between view and business logic.
- **DAO (Data Access Object)** – Manages persistence logic for each entity, storing data in JSON format.
- **SA (Service Application)** – A dedicated layer that encapsulates business logic and mediates between the presentation and data access layers.
- **Factory Pattern** – Used in all layers to encapsulate object creation, promoting flexibility and decoupling.
- **Singleton** – Applied where centralized configuration or shared access to resources is required.

## Repository Structure

```markdown
├── src/
│   ├── cliente/                # Cliente subsystem
│   ├── factura/                # Factura subsystem
│   ├── obra/                   # Obra subsystem
│   ├── pase/                   # Pase subsystem
│   ├── compTea/                # CompaniaTeatro subsystem
│   ├── miemCompTea/            # MiembroCompaniaTeatro subsystem
│   ├── taquillero/             # Taquillero subsystem
│   └── gui/                    # GUI components (presentation layer)
│
├── resources/                  # JSON data files (e.g., clientes.json, facturas.json)
├── uml/subsystems/             # draw.io UML diagrams by subsystem
├── lib/                        # External libraries (e.g., Gson)
└── README.md
