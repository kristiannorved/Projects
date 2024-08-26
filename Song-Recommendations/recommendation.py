import sklearn
import pandas as pd
import os
import sys

os.chdir(sys.path[0]) # set working directory to file location

# import data from local machine
metadata = pd.read_csv('metadata.csv')
triplet = pd.read_csv('triplet.csv', low_memory=False)
triplet.columns = ['user_id', 'song_id', 'listen_count']
df = pd.merge(triplet, metadata.drop_duplicates(['song_id']), on='song_id', how='left')

# data transformation
df['song'] = df[['artist_name', 'release']].agg(' - '.join, axis=1)
df_grouped = df.groupby(['song']).agg({'listen_count': 'count'}).reset_index()
grouped_sum = df_grouped['listen_count'].sum()
df_grouped['percentage'] = df_grouped['listen_count'].div(grouped_sum)*100
df_grouped = df_grouped.sort_values('listen_count', ascending=False)
print(df_grouped.head())
print('Dataset shape: ', df_grouped.shape)