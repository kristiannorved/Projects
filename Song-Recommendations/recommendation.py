import sklearn
import pandas as pd
import os
import sys

os.chdir(sys.path[0]) # set working directory to file location

# import data from local machine
metadata = pd.read_csv("metadata.csv")
triplet = pd.read_csv("triplet.csv")
