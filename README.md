# SWE 2 Project: Theatre Management Application

## Project Description
A desktop application for managing theatre operations, including patron records, show scheduling, ticket sales, and staff management. The system is implemented using a strict 3-layer architecture, demonstrating key object-oriented and architectural design principles.

### Core Features
- **Client Management**: Registering, deleting, and listing theatre patrons.
- **Invoice and Payment Management**: Creating and storing invoices for ticket purchases using JSON.
- **Show Management**: Handling details of theatrical productions.
- **Session and Seating Management**: Managing showtimes (pases) and seat allocations.
- **Theatre Company Management**: Administering information about theatre companies.
- **Company Member Management**: Managing individual members of a theatre company.
- **Box Office Operations**: Supporting the workflow and tasks of ticket sellers.
- **Graphical User Interface**: Swing-based GUI for interaction with all system functionalities.
- **Interactive Table for Data Management**: A dynamic table view displays and edits key system data like client records and invoices. It allows easy viewing and editing in a user-friendly interface with support for inline updates, making it ideal for both administrative and customer service tasks. This feature is highly customizable, supporting both read-only and editable modes, depending on user role or context.

## Architecture Overview
The application implements a comprehensive set of subsystems required for theatre management:

- **Cliente**: Patron registration, removal, and listing.
- **Factura**: Ticket invoicing and viewing of past purchases.
- **Obra**: Management of theatre productions (titles, descriptions, and categories).
- **Pase**: Show sessions tied to productions, with timing and seat allocation.
- **CompaniaTeatro**: Theatre company administration.
- **MiembroCompaniaTeatro**: Management of company members and their roles.
- **Taquillero**: Functions for box office staff.

## Design Patterns
This project applies several classical design patterns to maintain modularity and clear responsibilities:

- **Model-View-Controller (MVC)** – Uses a Passive View variation: views are decoupled from the logic, controllers (ActionListeners) mediate between view and business logic.
- **DAO (Data Access Object)** – Manages persistence logic for each entity, storing data in JSON format.
- **SA (Service Application)** – A dedicated layer that encapsulates business logic and mediates between the presentation and data access layers.
- **Factory Pattern** – Used in all layers to encapsulate object creation, promoting flexibility and decoupling.
- **Singleton** – Applied where centralized configuration or shared access to resources is required.

All subsystems are organized using a strict 3-layer architecture:

**Presentation Layer → Business Layer → Integration Layer**  
(*GUI & ActionListeners* → *Services & Validation* → *DAOs & JSON Persistence*)

## UML Artifacts
UML modeling (done using **draw.io**) was completed for the following subsystems:
- **Factura** and **Cliente**: Class Diagrams, Sequence Diagrams, and Activity Diagrams across all three layers.
- Class diagrams are also provided for the **Factory** classes in the Presentation, Business, and Integration layers, showing how object creation and dependencies are managed.

All UML diagrams are located in the `Diagramas_UML/` folder, inside the `IS2_GestionTeatro/` directory.

## Technologies
- **Java 11+**
- **Swing** for GUI development
- **Gson** for JSON persistence
- **draw.io** for UML modeling
- **Eclipse IDE** for development
  
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
├── Diagramas_UML/              # draw.io UML diagrams by subsystem
├── lib/                        # External libraries (e.g., Gson)
└── README.md
