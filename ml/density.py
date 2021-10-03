import numpy as np
from scipy import stats


def get_density(x, y, z):
    xyz = np.vstack([x, y, z])
    return stats.gaussian_kde(xyz)(xyz)