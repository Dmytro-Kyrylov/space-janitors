import math
import time

import flask
import numpy as np
import pandas as pd
from flask import request, jsonify
from skyfield.api import wgs84, load
from tqdm import tqdm

from density import get_density
from utils import get_nearest_epoch_for_each_norad, get_nearest_epoch_for_each_active_norad

app = flask.Flask(__name__)
app.config["DEBUG"] = True

ts = load.timescale()

PREDICT_FOR_N_SEC = 60 * 10
PREDICT_PER_SECS = 1
COLOR_NUMBERS = 7
EARTH_RADIUS = 6371

pd.options.mode.chained_assignment = None


def get_endangered_satellites(df, active, t):
    danger_density = df[df["color"].astype("float") >= 4].copy()
    danger_density["z"] += EARTH_RADIUS
    low_danger_active = []
    high_danger_active = []
    for sat in active:
        geocentric = sat.at(t)
        subpoint = wgs84.subpoint(geocentric)
        x = subpoint.longitude.degrees
        y = subpoint.latitude.degrees
        z = subpoint.elevation.km + EARTH_RADIUS
        closest_zone_km = min(np.sqrt(danger_density["z"] ** 2 + z ** 2 - 2 * danger_density["z"] * z * (
                    np.sin(danger_density["y"]) * np.sin(y) + np.cos(danger_density["x"]) * np.cos(x))))
        if closest_zone_km <= 100:
            high_danger_active.append(sat.model.satnum)
        elif closest_zone_km <= 300:
            low_danger_active.append(sat.model.satnum)

    return low_danger_active, high_danger_active


@app.route("/api/v1/density", methods=["GET"])
def api_density():
    begin = time.time()

    print(f"Begin density calculations\nPREDICT_FOR_N_SEC={PREDICT_FOR_N_SEC}\nPREDICT_PER_SECS={PREDICT_PER_SECS}")
    if "date" in request.args:
        date = pd.to_datetime(request.args["date"])
        satellites = get_nearest_epoch_for_each_norad(date)
        active = get_nearest_epoch_for_each_active_norad(date)

        results = {}
        for _secs in tqdm(range(0, PREDICT_FOR_N_SEC, PREDICT_PER_SECS)):
            _time = date + pd.to_timedelta(_secs, unit='s')
            print(f"Forming data for the {_time} second")
            t = ts.utc(
                year=_time.year,
                month=_time.month,
                day=_time.day,
                hour=_time.hour,
                minute=_time.minute,
                second=_time.second
            )
            x, y, z = [], [], []
            norads = []
            for sat in satellites:
                norads.append(sat.model.satnum)
                geocentric = sat.at(t)
                subpoint = wgs84.subpoint(geocentric)
                x.append(subpoint.longitude.degrees)
                y.append(subpoint.latitude.degrees)
                z.append(subpoint.elevation.km)

            df = pd.DataFrame(
                {
                    "norad": norads,
                    "x": x,
                    "y": y,
                    "z": z
                }
            ).dropna()
            df["density"] = get_density(df["x"].values, df["y"].values, df["z"].values)
            step = (max(df["density"]) - min(df["density"])) / COLOR_NUMBERS
            bins = [min(df["density"]) + step * i for i in range(0, COLOR_NUMBERS)]
            bins.append(np.inf)
            df["color"] = pd.cut(df['density'], bins, labels=[str(i) for i in range(0, COLOR_NUMBERS)])

            low_danger_active, high_danger_active = get_endangered_satellites(df, active, t)

            time_df = df[["norad", "color"]].dropna()

            results[str(pd.to_datetime(_time).strftime('%Y-%m-%dT%H:%M:%S'))] = {
                "low_danger_active": low_danger_active,
                "high_danger_active": high_danger_active,
                "density": time_df.to_dict("records")
            }

        end = time.time()
        print(f"Total runtime of the program is {end - begin}")
        return jsonify(results)
    else:
        return "Error: No id field provided. Please specify an date."


if __name__ == '__main__':
    app.run()
