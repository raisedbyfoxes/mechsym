#!/usr/bin/env python

import os
import subprocess
import time
import zipfile
import json
import tomllib

import requests


def build_mrpack():
    pack = load_pack_metadata()

    pack_version_number = pack["version"]

    os.makedirs("out", exist_ok=True)
    with zipfile.ZipFile(
        f"out/MechanicalSympathy-{pack_version_number}.mrpack", "w"
    ) as pack_zip:
        mod_entries_manifest = []
        for slug, mod_origin in pack["mods"].items():
            if type(mod_origin) is str:
                # For mods hosted on Modrinth (mod_origin is version number)
                mod_entries_manifest.append(create_mrpack_mod_entry(slug, mod_origin))
            else:
                # For mods in the local repo that should be built using Gradle
                mod_path = mod_origin["path"]
                log(f"Building `{slug}` (located at `{mod_path}`)")

                gradle = subprocess.Popen(
                    [
                        "java",
                        "-classpath",
                        "gradle/wrapper/gradle-wrapper.jar",
                        "org.gradle.wrapper.GradleWrapperMain",
                        "build",
                    ],
                    cwd=mod_path,
                )
                gradle.wait()

                mod_jardirname = f"{mod_path}/build/libs"
                mod_jarfilename = os.listdir(mod_jardirname)[0]
                pack_zip.write(
                    f"{mod_jardirname}/{mod_jarfilename}",
                    f"overrides/mods/{mod_jarfilename}",
                )

        modrinth_index_manifest = {
            "formatVersion": 1,
            "game": "minecraft",
            "versionId": pack_version_number,
            "name": pack["name"],
            "dependencies": {
                "minecraft": pack["minecraft_version"],
                "fabric-loader": pack["fabric_version"],
            },
            "files": mod_entries_manifest,
        }

        pack_zip.writestr(
            "modrinth.index.json", json.dumps(modrinth_index_manifest, indent=4)
        )


def create_mrpack_mod_entry(slug, version_number):
    log(f"Requesting info for mod `{slug}` {version_number}")
    versions = requests.get(
        f"https://api.modrinth.com/v2/project/{slug}/version"
    ).json()
    for version in versions:
        if version["version_number"] == version_number:
            if "fabric" not in version["loaders"]:
                continue

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
try:
    build_mrpack()
    log(f"Done in {time.time() - start_time:.2f}s")
except KeyboardInterrupt:
    log("Aborted")
