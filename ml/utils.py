import pandas as pd
from skyfield.api import load

from skyfield.sgp4lib import EarthSatellite
from tqdm import tqdm

ts = load.timescale()


class modified_EarthSatellite(EarthSatellite):
    def __init__(self, line1, line2, name=None, ts=None):
        super(modified_EarthSatellite, self).__init__(line1, line2, name=None, ts=None)
        self.tle_line1 = line1
        self.tle_line2 = line2


def get_nearest_epoch_for_each_norad(dt):
    print(f"Loading the debris data")
    df = pd.read_csv("data/cosmos_full.csv").append(pd.read_csv("data/iridium_full.csv"))
    df["utc_datetime"] = pd.to_datetime(df["utc_datetime"])
    dt = pd.to_datetime(dt)

    print(f"Creating the satellites")
    satellites = []
    for norad in tqdm(df["norad"].unique().tolist()):
        idx = df[df["norad"] == norad].set_index("utc_datetime")
        idx = idx.iloc[idx.index.drop_duplicates().sort_values().get_loc(dt, method='nearest')]

        satellite = modified_EarthSatellite(idx["line1"], idx["line2"], None, ts=None)
        satellites.append(satellite)
    return satellites


def get_nearest_epoch_for_each_active_norad(dt):
    print(f"Loading the active satellites data")
    df = pd.read_csv("data/active_full.csv")
    df["utc_datetime"] = pd.to_datetime(df["utc_datetime"])
    dt = pd.to_datetime(dt)

    print(f"Creating the satellites")
    satellites = []
    for norad in tqdm(df["norad"].unique().tolist()):
        idx = df[df["norad"] == norad].set_index("utc_datetime")
        idx = idx.iloc[idx.index.drop_duplicates().sort_values().get_loc(dt, method='nearest')]

        satellite = modified_EarthSatellite(idx["line1"], idx["line2"], None, ts=None)
        satellites.append(satellite)
    return satellites
