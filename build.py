#!/usr/bin/env python

import os
import time
import requests
import zipfile
import json
import tomllib

def build_mrpack():
    pack = load_pack_metadata()

    pack_version = pack["version"]

    os.makedirs("out", exist_ok=True)
    with zipfile.ZipFile(f"out/MechanicalSympathy-{pack_version}.mrpack", "w") as pack_zip:
        pack_zip.writestr("modrinth.index.json", json.dumps({
            "formatVersion": 1,
            "game": "minecraft",
            "versionId": pack_version,
            "name": pack["name"],
            "dependencies": {
                "minecraft": pack["minecraft_version"],
                "fabric-loader": pack["fabric_version"],
            },
            "files": [create_mrpack_mod_entry(slug, version_number) for slug, version_number in pack["mods"].items()],
        }))

def create_mrpack_mod_entry(slug, version_number):
    log(f"Requesting info for mod `{slug}` {version_number}")
    versions = requests.get(f"https://api.modrinth.com/v2/project/{slug}/version").json()
    for version in versions:
        if version["version_number"] == version_number:
            if "fabric" not in version["loaders"]: continue

            mod_file = version["files"][0]

            return {
                "path": f"mods/{slug}-{version_number}.jar",
                "hashes": mod_file["hashes"],
                "downloads": [mod_file["url"]],
            }

def load_pack_metadata():
    with open("pack.toml", "rb") as file:
        return tomllib.load(file)

def log(msg):
    print(f"\x1B[90m| \x1B[37m{msg}\x1B[0m")

start_time = time.time()
build_mrpack()
log(f"Completed in {time.time() - start_time:.2f}s")
