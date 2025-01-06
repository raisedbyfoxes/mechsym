# Mechanical Sympathy

Fabric 1.20.1 pack that aims to one day be to Create as _GT New Horizons_ is to Gregtech

## Building from source

**Dependencies**:

-   Java 17-22 (in PATH)
-   Python 3
    -   `requests` package

Once you've installed all the above, all you (should) need to do is to run `./build.py`, the final
`.mrpack` file will be in the `out` directory

## Repo map

-   **core** - Source tree for the custom core mod
-   **packfiles** - Configuration and other files that will be copied directly to the modpack's
    `.minecraft` folder on installation
-   _pack.toml_ - Blueprint for the modpack. Contains a list of every mod used and their versions
-   _build.py_ - Script that assembles the Modrinth pack file using _pack.toml_. Also invokes Gradle
    so be warned
